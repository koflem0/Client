import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

	public class Map {

		private ArrayList<Spot> spots = new ArrayList<Spot>();
		private int Xlimit, Ylimit;
		private ArrayList<Wall> walls = new ArrayList<Wall>();
		private ArrayList<Platform> platforms = new ArrayList<Platform>();
		private ArrayList<Monster> monsters = new ArrayList<Monster>();
		private ArrayList<Ladder> ladders = new ArrayList<Ladder>();
		private Image background;
		private Rectangle[] water = new Rectangle[5];

		public Map(int number) {
			switch (number) {
			case 0:
				Xlimit = 1500;
				Ylimit = 910;
				walls.add(new Wall(0,715,1500,10));
				walls.add(new Wall(1195,615,305,295));
				walls.add(new Wall(1363,525,137,380));
				platforms.add(new Platform(490,530,170));
				background = newImage("/map0.jpg");
				spots.add(new Spot(new Point(1390,320), new Point(5,335), 1, new Point(0,0)));
				monsters.add(new Monster(Monster.COBRA, new Point(100,715-60)));
				monsters.add(new Monster(Monster.COBRA, new Point(900,715-60)));
				monsters.add(new Monster(Monster.COBRA, new Point(570,525-60)));
				monsters.add(new Monster(Monster.COBRA, new Point(1230,600-60)));
				break;
			case 1:
				Xlimit = 3000;
				Ylimit = 1820;
				walls.add(new Wall(0,Ylimit-40,Xlimit,40));
				platforms.add(new Platform(0,550, 515));
				platforms.add(new Platform(780,550,1330-780));
				platforms.add(new Platform(1600,550,2190-1600));
				platforms.add(new Platform(2450,550,Xlimit-2450));
				platforms.add(new Platform(515,1455,775-515));
				ladders.add(new Ladder(0,platforms.get(0),460,1260));
				spots.add(new Spot(new Point(0,340), new Point(1500-125,525-200), 0, new Point(220,0)));
				spots.add(new Spot(new Point(2450+450,550-200), new Point(5,5000-240),2,new Point(0,5000-910)));
				background = newImage("/map1.jpg");
				monsters.add(new Monster(Monster.COBRA, new Point(850,540-60)));
				monsters.add(new Monster(Monster.COBRA, new Point(1100,540-60)));
				monsters.add(new Monster(Monster.BIGCOBRA, new Point(1750,540-60)));
				monsters.add(new Monster(Monster.COBRA, new Point(2000,540-60)));
				monsters.add(new Monster(Monster.BIGCOBRA, new Point(2600,Ylimit-100)));
				monsters.add(new Monster(Monster.BIGCOBRA, new Point(1600,Ylimit-100)));
				monsters.add(new Monster(Monster.BIGCOBRA, new Point(400,Ylimit-100)));
				monsters.add(new Monster(Monster.VERYBIGCOBRA, new Point(1800,Ylimit-100)));
				monsters.add(new Monster(Monster.VERYBIGCOBRA, new Point(600,Ylimit-100)));
				break;
			case 2:
				Xlimit = 2000;
				Ylimit = 5000;
				
				spots.add(new Spot(new Point(0,5000-240), new Point(2450+430,550-200), 1, new Point(3000-1280,0)));
				walls.add(new Wall(0,Ylimit-40,Xlimit,40));
				walls.add(new Wall(1580,4012,2000-1580,4035-4012));
				walls.add(new Wall(103,2483,201-103,2658-2483));
				walls.add(new Wall(215,2568,253-215,2656-2568));
				walls.add(new Wall(400,2357,605-400,2812-2380));
				platforms.add(new Platform(765,4835, 1052-765));
				platforms.add(new Platform(927,4075, 1167-927));
				platforms.add(new Platform(765,3785, 1345-765));
				platforms.add(new Platform(1465,3700, 1660-1465));
				platforms.add(new Platform(1782,3700, 2000-1782));
				platforms.add(new Platform(765,3785, 1345-765));
				platforms.add(new Platform(790,3313, 1289-777));
				platforms.add(new Platform(277,3315, 668-277));
				platforms.add(new Platform(256,2633, 668-277));
				platforms.add(new Platform(220,2353, 180));
				platforms.add(new Platform(1150,2436, 1333-1150));
				
				monsters.add(new Monster(Monster.VERYBIGCOBRA, new Point(600,Ylimit-100)));
				monsters.add(new Monster(Monster.BIGCOBRA, new Point(1000,Ylimit-100)));
				monsters.add(new Monster(Monster.COBRA, new Point(1200,Ylimit-100)));
				monsters.add(new Monster(Monster.COBRA, new Point(1400,Ylimit-100)));
				monsters.add(new Monster(Monster.COC, new Point(800,3730)));
				monsters.add(new Monster(Monster.COC, new Point(800, 3250)));
				monsters.add(new Monster(Monster.VERYBIGCOBRA, new Point(300, 3250)));
				monsters.add(new Monster(Monster.BIGCOBRA, new Point(550, 3250)));
				monsters.add(new Monster(Monster.COC, new Point(400, 2290)));
				
				background = newImage("/map2.jpg");
				ladders.add(new Ladder(987,platforms.get(1),100,4415-4020));
				ladders.add(new Ladder(945,4120,125,540));
				ladders.add(new Ladder(1200,platforms.get(2),1337-1220,4100-3870));
				ladders.add(new Ladder(1825,platforms.get(3),0,4012-3860));
				ladders.add(new Ladder(815,platforms.get(6),971-815,3773-3341));
				ladders.add(new Ladder(502,2817,0,3185-2830));
				ladders.add(new Ladder(337,platforms.get(8),0,2800-2600));
				ladders.add(new Ladder(804,2350,1110-804,2600-2350));
				spots.add(new Spot(new Point(Xlimit-115,4009-200), new Point(25,830-200),3, new Point(0,0)));
				spots.add(new Spot(new Point(1226,2224),new Point(1160, 160), 5, new Point(0,0), true));
				break;
			case 3:
				
				Xlimit = 3000;
				Ylimit = 910;
				spots.add(new Spot(new Point(25,Ylimit-260), new Point(2000-125,4009-200), 2, new Point(2000-1280,3985-710)));
				spots.add(new Spot(new Point(2880,630), new Point(25,700), 4, new Point(0,1000-910)));
				platforms.add(new Platform(880,343,1185-880));
				platforms.add(new Platform(1297,393,1650-1297));
				platforms.add(new Platform(1757,335,1893-1757));
				platforms.add(new Platform(1975,407,50));
				ladders.add(new Ladder(941,351,1050-941,825-291));
				ladders.add(new Ladder(2580,430,5,833-430));
				
				monsters.add(new Monster(Monster.COC, new Point(400,Ylimit-120)));
				monsters.add(new Monster(Monster.COC, new Point(600,Ylimit-120)));
				monsters.add(new Monster(Monster.COC, new Point(800,Ylimit-120)));
				monsters.add(new Monster(Monster.COC, new Point(1000,Ylimit-120)));
				monsters.add(new Monster(Monster.COC, new Point(1200,Ylimit-120)));
				monsters.add(new Monster(Monster.COC, new Point(1400,Ylimit-120)));
				monsters.add(new Monster(Monster.COC, new Point(1600,Ylimit-120)));
				monsters.add(new Monster(Monster.COC, new Point(1800,Ylimit-120)));
				
				background = newImage("/map3.jpg");
				walls.add(new Wall(0,Ylimit-60,Xlimit,40));
				walls.add(new Wall(2100,440,2571-2100,819-440));
				break;
			case 4:
				Xlimit = 4000;
				Ylimit = 1000;
				background = newImage("/map4.jpg");
				walls.add(new Wall(0,Ylimit-30,Xlimit,40));
				spots.add(new Spot(new Point(25,Ylimit-230), new Point(2880,630), 3, new Point(3000-1280,0)));
				break;
			case 5:
				Xlimit = 1280;
				Ylimit = 3900;
				background = newImage("/arbre1.jpg");
				
				platforms.add(new Platform(898, 363,383));
				platforms.add(new Platform(106, 895,182));
				platforms.add(new Platform(1141,1115,141));
				platforms.add(new Platform(106,1742,270-106));
				platforms.add(new Platform(771,2658,155));
				
				walls.add(new Wall(900, 827, 381, 48));
				walls.add(new Wall(285, 895, 440, 75));
				walls.add(new Wall(0, 1357, 468, 49));
				walls.add(new Wall(541, 1221, 392, 45));
				walls.add(new Wall(940, 1590, 127, 49));
				walls.add(new Wall(1130, 1495, 151, 49));
				walls.add(new Wall(1039, 1115, 103, 47));
				walls.add(new Wall(742, 1660,126, 46));
				walls.add(new Wall(540, 1219, 392, 49));
				walls.add(new Wall(107, 2128, 237, 48));
				walls.add(new Wall(536, 1742, 133, 48));
				walls.add(new Wall(555, 2129, 454, 43));
				walls.add(new Wall(0, 1743, 109, 48));
				walls.add(new Wall(1157, 2287, 127, 49));
				walls.add(new Wall(994, 2489, 285, 50));
				walls.add(new Wall(210, 2658, 563, 47));
				walls.add(new Wall(0, 2506, 132, 52));
				walls.add(new Wall(720, 3018, 240, 50));
				walls.add(new Wall(0, 3833, Xlimit, 3900-3833));
				walls.add(new Wall(93, 3341, 240, 32));
				walls.add(new Wall(225, 3066, 419, 32));
				walls.add(new Wall(424, 3475, 240, 32));
				walls.add(new Wall(820, 3475, 194, 32));
				walls.add(new Wall(1182, 3659, 99, 34));
				walls.add(new Wall(0, 3180, 83, 34));
				walls.add(new Wall(343, 1654, 130, 49));
				
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(1035, 765)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(985, 765)));
				monsters.add(new Monster(Monster.MUSHY, new Point(1135, 705)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(196, 826)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(236, 826)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(600, 1150)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(704, 2041)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(804, 2041)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(1035, 765)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(985, 765)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(216, 826)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(236, 826)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(560, 1150)));
				monsters.add(new Monster(Monster.MUSHY, new Point(600, 1150)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(604, 2041)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(804, 2041)));
				monsters.add(new Monster(Monster.MUSHY, new Point(1060, 2373)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(325, 2533)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(465, 2533)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(505, 2533)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(645, 2533)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(309, 2977)));
				monsters.add(new Monster(Monster.MUSHY, new Point(409, 2977)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(137, 3277)));
				monsters.add(new Monster(Monster.MUSHY, new Point(913, 3389)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(20, 3775)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(150, 3775)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(300, 3375)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(450, 3375)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(600, 3375)));
				monsters.add(new Monster(Monster.BABYSPIDER, new Point(900, 3375)));
				
				ladders.add(new Ladder(995, platforms.get(0), 196, 458));
				ladders.add(new Ladder(93, platforms.get(1), 189, 461));
				ladders.add(new Ladder(1140, platforms.get(2), 147, 383));
				ladders.add(new Ladder(106, platforms.get(3), 270-106,387));
				ladders.add(new Ladder(772, platforms.get(4), 154,364));
				
				spots.add(new Spot(new Point(1160, 160),
						new Point(1226, 2230), 2, new Point(1226/2,2250-480),true));

			}
			limitWalls();
		}

		public ArrayList<Ladder> getLadders() {
			return ladders;
		}
		
		public Rectangle[] getWater() {
			return water;
		}

		// quatre murs limites
		public void limitWalls() {
			walls.add(new Wall(-5, 0, 10, Ylimit));
			walls.add(new Wall(Xlimit - 5, 0, 10, Ylimit));
			walls.add(new Wall(0, Ylimit - 5, Xlimit, 10));
			walls.add(new Wall(0, -5, Xlimit, 10));
		}
		
		public Image getBackground(){return background;}
		
		// retourne les murs et les platformes de la map
		public ArrayList<Wall> getWalls() {
			return walls;
		}

		public ArrayList<Platform> getPlatforms() {
			return platforms;
		}

		// retourne un spot de téléportation
		public Rectangle getSpot(int i) {
			return spots.get(i).getArea();
		}

		// retourne tous les spots de téléportation
		public ArrayList<Spot> getSpots() {
			return spots;
		}
		
		public Image newImage(String source) {
			return new ImageIcon(getClass().getResource(source)).getImage();
		}

		// retourne la prochaine map
		public int getNextMap(int i) {
			return spots.get(i).getNextMap();
		}

		// returns the character's starting spot on the map
		public Point getStart(int i) {
			return spots.get(i).getSpawn();
		}

		// retourne les limites de la map
		public int getXLimit() {
			return Xlimit;
		}

		public int getYLimit() {
			return Ylimit + 50;
		}

		// retourne ou la caméra devrait être à la prochaine map
		public Point getXY(int i) {
			return spots.get(i).getNextXY();
		}

		// retourne les monstres de la map
		public ArrayList<Monster> getMonsters() {
			return monsters;
		}

	}
