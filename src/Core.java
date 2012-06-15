import java.awt.*;


public abstract class Core {
	
	protected static final DisplayMode[] modes = {
		new DisplayMode(1280,960,32,60),
		new DisplayMode(1280,960,24,60),
		new DisplayMode(1280,960,16,60),
		new DisplayMode(1280,768,32,60),
		new DisplayMode(1280,768,24,60),
		new DisplayMode(1280,768,16,60),
	};
	private boolean running;
	private long lastFrame = 0;
	protected Screen S;
	private DisplayMode mode;
	
	//arr�te le jeu
	public void stop(){
		running = false;
	}
	
	public boolean isRunning(){return running;}
	
	//m�thode principale
	public void run(){
		try{
			init();
			gameLoop();
		}finally{S.restoreScreen();}
	}
	
	//initialise l'�cran
	public void init(){
		S = new Screen();
		mode = S.findGoodMode(modes);
		S.setFullScreen(mode);
		
		Window w = S.getFSWindow();
		w.setFont(new Font("Arial", Font.PLAIN, 24));
		w.setBackground(Color.BLACK);
		w.setForeground(Color.BLACK);
		running = true;
	}
	
	//boucle du jeu
	public void gameLoop(){
		long startTime = System.currentTimeMillis(), cumTime = startTime;
		
		while(running){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			
			if(timePassed > 20) timePassed = 20;
			update(timePassed);
			
			//dessine l'�cran seulement chaque 15 millisecondes
			lastFrame += timePassed;

			if(lastFrame >= 15){
				Graphics2D g = S.getGraphics();
				draw(g);
				g.dispose();
				lastFrame = 0;
				S.update();
			}
			//prend une pause pour pr�server le cpu
			try{
				Thread.sleep(5 - (System.currentTimeMillis()-cumTime));
			} catch(Exception e) {}
			
			
		}
	}

	//fonctions abstraites
	public void update(long timePassed){
	}
	public abstract void draw(Graphics2D g);
	
}
