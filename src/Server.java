import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Server
{

	public static int port = 2406;
	public static String ip = "";

	public static ServerSocket server;

	public static Vector<Socket> sockets_l = new Vector<Socket>();
	public static Vector<Integer> client_states_l = new Vector<Integer>();
	public static Vector<DataPackage> data_l = new Vector<DataPackage>();
	public static Vector<MonsterPackage> monster_l = new Vector<MonsterPackage>();

	private static Runnable accept = new Runnable()
	{
		@Override
		public void run()
		{

			new Thread(monstersData).start();

			while (true)
			{
				if (sockets_l.size() < 4)
					try
					{
						Socket socket = server.accept();

						ObjectInputStream ois = new ObjectInputStream(
								socket.getInputStream());

						String username = (String) ois.readObject();

						boolean accepted = true;
						for (int i = 0; i < data_l.size(); i++)
						{
							if (data_l.get(i).username.toLowerCase().equals(
									username.toLowerCase()))
							{
								accepted = false;
								break;
							}
						}

						ObjectOutputStream oos = new ObjectOutputStream(
								socket.getOutputStream());

						if (accepted)
						{
							oos.writeObject("Welcome To the Server");
							clients_model_l.addElement(username + " - "
									+ socket.getInetAddress().getHostAddress()
									+ " - "
									+ socket.getInetAddress().getHostName());
							client_states_l.add(0);

							data_l.add(new DataPackage());
							sockets_l.add(socket);
							new Thread(send).start();
							new Thread(receive).start();
						} else
							oos.writeObject("Your name is taken");

						Thread.sleep(100);

					} catch (Exception ex)
					{
					}

				else
					try
					{
						Thread.sleep(500);
					} catch (InterruptedException e)
					{
					}
			}

		}

	};
	static Vector<Vector<Monster>> monsters = new Vector<Vector<Monster>>();
	static float[] lifeMultiplier = { 1, 1, 1, 1 };
	static int[] loadedMaps = { -1, -1, -1, -1 };
	
	private static Runnable monstersData = new Runnable()
	{

		Vector<Vector<Wall>> walls = new Vector<Vector<Wall>>();
		Vector<Vector<Platform>> platforms = new Vector<Vector<Platform>>();
		Vector<Integer> usedMaps;

		@Override
		public void run()
		{
			long timePassed, cumTime = System.currentTimeMillis();
			for (int i = 0; i < 4; i++)
			{
				if (monsters.size() <= i)
					monsters.add(new Vector<Monster>());
				if (walls.size() <= i)
					walls.add(new Vector<Wall>());
				if (platforms.size() <= i)
					platforms.add(new Vector<Platform>());
			}
			
			

			while (true)
			{
				try
				{
					timePassed = System.currentTimeMillis() - cumTime;
					cumTime += timePassed;
					if (timePassed > 20)
						timePassed = 20;

					for (int i = 0; i < lifeMultiplier.length; i++)
						lifeMultiplier[i] = 1;

					if (data_l.size() > 0)
					{
						usedMaps = new Vector<Integer>();
						for (int i = 0; i < data_l.size(); i++)
						{
							if ((!usedMaps.contains(data_l.get(i).map)) && data_l.get(i).map != -1)
								usedMaps.add(data_l.get(i).map);
							else if (data_l.get(i).map != -1){
								int map = -1;
								for(int j = 0; j < loadedMaps.length; j++){
									if(loadedMaps[j] == data_l.get(i).map) map = j;
								}
								if(map!=-1)
								lifeMultiplier[map] += 0.9;
							}
						}
						loadMaps(usedMaps);

						for (int i = 0; i < 4; i++)
						{
							if (monsters.get(i).size() > 0)
							{
								for (int j = 0; j < monsters.get(i).size(); j++)
								{
									monsters.get(i).set(
											j,
											mapPhysics(monsters.get(i).get(j),
													i, timePassed));
								}
							}
						}

						Vector<MonsterPackage> temp = new Vector<MonsterPackage>();
						for (int i = 0; i < monsters.size(); i++)
						{
							if (monsters.get(i).size() != 0)
								for (int j = 0; j < monsters.get(i).size(); j++)
								{
									Monster monster = monsters.get(i).get(j);
									monster.setLifeMultiplier(lifeMultiplier[i]);
									MonsterPackage mp = new MonsterPackage();
									mp.x = monster.getX();
									mp.y = monster.getY();
									mp.map = loadedMaps[i];
									mp.canMove = monster.canMove();
									mp.life = monster.getLife();
									mp.maxLife = monster.getMaxLife();
									mp.number = j;
									mp.initialized = monster.initialized;
									mp.type = monster.type;
									mp.isFacingLeft = monster.isFacingLeft();
									mp.eliteType = monster.eliteT;
									mp.alive = monster.isAlive();
									temp.add(mp);
								}
						}

						monster_l = temp;

						if (10 - timePassed > 0)
							Thread.sleep(10 - timePassed);

					} else
						Thread.sleep(100);

				} catch (Exception ex)
				{
					ex.printStackTrace();
					System.exit(0);
				}
			}
		}

		private Monster mapPhysics(Monster m, int i, long timePassed)
		{

			if (m.isAlive())
			{
				m.fall(timePassed);

				for (Wall wall : walls.get(i))
				{
					if (wall.getTop().intersects(m.getBase())
							&& m.getYVelocity() > 0)
					{
						m.setY(wall.getTopY() - m.getHeight());
						m.setYVelocity(0);
					}
				}

				for (Platform platform : platforms.get(i))
				{
					if (platform.getTop().intersects(m.getBase())
							&& m.getYVelocity() > 0)
					{

						m.setY(platform.getTopY() - m.getHeight());
						m.setYVelocity(0);
					}
				}

				boolean turn, facingwall = false;
				if (m.getYVelocity() == 0)
				{
					turn = true;
				} else
				{
					turn = false;
				}

				for (Wall wall : walls.get(i))
					if (wall != null)
						if (wall.getTop().intersects(m.getNextFloor()))
							turn = false;
				for (Platform platform : platforms.get(i))
					if (platform != null)
						if (platform.getTop().intersects(m.getNextFloor()))
							turn = false;

				for (Wall wall : walls.get(i))
					if (wall != null)
						if (wall.getSide().intersects(m.getSide()))
						{
							turn = true;
							facingwall = true;
						}

				if (turn)
				{
					if (m.canMove())
					{
						if (m.isAggro != null && isFacingChar(m) && !facingwall
								&& m.getYVelocity() == 0)
						{
							m.jump();
						} else
						{
							m.setXVelocity(-m.getXVelocity());
							m.setFacingLeft(!m.isFacingLeft());
						}

					} else
						m.setXVelocity(0);
				}
				if (m.canMove()
						&& (m.getXVelocity() != m.getSpeed() && m
								.getXVelocity() != -m.getSpeed()))
				{
					if (m.isAggro != null)
					{
						if (m.isAggro.getX() > m.getX())
						{
							m.setXVelocity(m.getSpeed());
							m.setFacingLeft(false);
						} else
						{
							m.setXVelocity(-m.getSpeed());
							m.setFacingLeft(true);
						}
					} else
					{
						m.setXVelocity(m.getSpeed());
						m.setFacingLeft(false);
					}
				}
			}
			m.update(timePassed);
			return m;

		}

		public boolean isFacingChar(Monster m)
		{
			if (m.isAggro.getY() > m.getY())
			{
				if ((m.getXVelocity() > 0 && m.isAggro.getX() > m.getX())
						|| (m.getXVelocity() < 0 && m.isAggro.getX() < m.getX()))
					return true;
			}
			return false;
		}

		private void loadMaps(Vector<Integer> usedMaps)
		{

			for (int i = 0; i < loadedMaps.length; i++)
			{
				if (!usedMaps.contains((Object) loadedMaps[i])
						&& loadedMaps[i] != -1)
				{
					loadedMaps[i] = -1;
					walls.set(i, null);
					platforms.set(i, null);
					monsters.set(i, new Vector<Monster>());
				}
			}
			for (Integer map : usedMaps)
			{
				boolean loaded = false;
				for (Integer loadedMap : loadedMaps)
				{
					if (loadedMap == map)
					{
						loaded = true;
					}
				}
				if (!loaded)
				{
					int emptyMapSpot = -1;
					for (int i = 0; i < loadedMaps.length; i++)
					{
						if (loadedMaps[i] == -1)
						{
							emptyMapSpot = i;
							break;
						}
					}
					if (emptyMapSpot != -1)
					{
						Map tempmap = new Map(map);
						loadedMaps[emptyMapSpot] = map;
						monsters.set(emptyMapSpot, new Vector<Monster>(tempmap.getMonsters()));
						walls.set(emptyMapSpot, new Vector<Wall>(tempmap.getWalls()));
						platforms.set(emptyMapSpot, new Vector<Platform>(tempmap.getPlatforms()));
					}
				}
			}
		}

	};

	private static Runnable send = new Runnable()
	{
		@Override
		public void run()
		{
			int i = -1;
			ObjectOutputStream oos;
			Socket socket = sockets_l.get(sockets_l.size() - 1);
			while (sockets_l.contains(socket))
			{
				i = sockets_l.indexOf(socket);
				try
				{
					oos = new ObjectOutputStream(sockets_l.get(i)
							.getOutputStream());
					int client_state = client_states_l.get(i);
					oos.writeObject(client_state);

					oos = new ObjectOutputStream(sockets_l.get(i)
							.getOutputStream());
					oos.writeObject(data_l);

					oos = new ObjectOutputStream(sockets_l.get(i)
							.getOutputStream());
					oos.writeObject(monster_l);

					if (client_state == 1)
					{// kicked by server

						dcClient(i);

					} else if (client_state == 2)
					{// server disconnected

						dcClient(i);
					}
					Thread.sleep(10);
				} catch (Exception ex)
				{
				}

			}

		}

	};

	private static Runnable receive = new Runnable()
	{
		@Override
		public void run()
		{
			int i = -1;
			ObjectInputStream ois;
			Socket socket = sockets_l.get(sockets_l.size() - 1);
			while (sockets_l.contains(socket))
			{

				i = sockets_l.indexOf(socket);
				try
				{

					ois = new ObjectInputStream(sockets_l.get(i)
							.getInputStream());
					int received_state = (Integer) ois.readObject();

					ois = new ObjectInputStream(sockets_l.get(i)
							.getInputStream());
					DataPackage dp = (DataPackage) ois.readObject();

					if (dp != null)
						;
					{
						data_l.set(i, dp);
						if (dp.hit.size() > 0)
						{
							int map = 0;
							for(int j = 0; j < loadedMaps.length; j++){
								if(loadedMaps[j] == dp.map) map = j;
							}
							for (HitData hit : dp.hit)
								damage(map, hit.monster, hit.damage,
										hit.knockback, new Point(dp.x, dp.y));
						}
					}

					if (received_state != -1)
						if (received_state == 1)
						{ // Client disconnected by User
							dcClient(i);
						}

				} catch (Exception ex)
				{// Client disconnected without notifying server
					dcClient(i);
				}

				try
				{
					Thread.sleep(10);
				} catch (InterruptedException e)
				{
				}
			}

		}

	};

	public static synchronized void damage(int map, int monster, int dmg, float speed,Point p)
	{
		int errors = 0;
		while (monsters.get(map).size() <= 0)
		{
			errors++;
			if (errors > 500)
				return;
		}
		Monster m = monsters.get(map).get(monster);
		if (dmg > 0)
		{
			m.life -= dmg;
			m.isAggro = p;
			m.aggroTimer = 5000;
			if ((int)m.life <= 0)
			{
				m.die();
		}

		if (dmg > m.getMaxLife() / 50 && m.canMove && m.eliteT != Monster.SLOW)
		{
			m.canMove = false;
			m.cantMoveTime = (long) (105 + speed * 1100);
			m.setYVelocity(-(speed * 3));
			if ((p.getX() + 60) > (m.getX() + m.getWidth() / 2))
				speed = -speed;
			m.setXVelocity(speed);
		}
		monsters.get(map).set(monster, m);
	}
	}

	public static void dcClient(int index)
	{

		try
		{
			clients_model_l.remove(index);
			client_states_l.remove(index);
			data_l.remove(index);
			sockets_l.remove(index);

		} catch (Exception e)
		{
		}
	}

	public static JFrame frame;
	public static JPanel pane1, pane2, pane3, content;

	public static JButton dc_btn;

	public static JList clients_l;
	public static DefaultListModel clients_model_l;

	public static void main(String[] args)
	{

		try
		{
			ip = InetAddress.getLocalHost().getHostAddress() + ":" + port;

			server = new ServerSocket(port, 0, InetAddress.getLocalHost());

			new Thread(accept).start();

		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
					"Alert", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		dc_btn = new JButton();
		dc_btn.setText("Disconnect");
		dc_btn.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selected = clients_l.getSelectedIndex();

				if (selected != -1)
				{

					try
					{

						client_states_l.set(selected, 1);

					} catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null,
								"Error: " + ex.getMessage(), "Alert",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}

		});

		clients_model_l = new DefaultListModel();
		clients_l = new JList(clients_model_l);
		clients_l.addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent e)
			{

				if (e.getValueIsAdjusting())
				{
					System.out.println(clients_l.getSelectedIndex());
				}

			}

		});

		frame = new JFrame();
		frame.setTitle("Server - " + ip);

		frame.addWindowListener(new WindowListener()
		{

			public void windowActivated(WindowEvent arg0)
			{
			}

			public void windowClosed(WindowEvent e)
			{
			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				int tries = 0;
				while (sockets_l.size() != 0 && tries < 400)
				{
					try
					{

						for (int i = 0; i < client_states_l.size(); i++)
						{
							client_states_l.set(i, 2);
						}
						Thread.sleep(10);
						tries++;
					} catch (Exception ex)
					{
					}
				}

				System.exit(0);
			}

			public void windowDeactivated(WindowEvent arg0)
			{
			}

			public void windowDeiconified(WindowEvent arg0)
			{
			}

			public void windowIconified(WindowEvent arg0)
			{
			}

			public void windowOpened(WindowEvent arg0)
			{
			}

		});

		pane1 = new JPanel();
		pane1.setLayout(new GridLayout(1, 1, 1, 1));
		pane1.add(dc_btn);

		pane2 = new JPanel();
		pane2.add(new JLabel(ip));

		pane3 = new JPanel();
		pane3.setLayout(new BorderLayout(1, 1));
		pane3.add(pane1, BorderLayout.NORTH);
		pane3.add(new JScrollPane(clients_l), BorderLayout.CENTER);
		pane3.add(pane2, BorderLayout.SOUTH);

		content = new JPanel();
		content.setLayout(new GridLayout(1, 1, 1, 1));
		content.add(pane3);

		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		frame.setContentPane(content);
		frame.pack();
		frame.setSize(350, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
