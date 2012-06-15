import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;


public class Monster extends Sprite {
		
		public static final int COBRA = 0, BIGCOBRA = 1, COC = 2, VERYBIGCOBRA = 3, MUSH = 4, BABYSPIDER = 5, MUSHY = 6, MUSHETTE = 7;
		public static final int DMG = 0, SPD = 1, DEF = 2, SLOW = 3; 
		
		long cantMoveTime;
		private int atk, def, mastery;

		float life, maxLife;

		private int exp, lvl, dropchance = 13, rarechance = 8, dropamount = 1;

		int avoid;
		private float spd, allStatsMultiplier = 1, lifeMultiplier = 1;
		private float[] statMultipliers = {1,1,1,1,1,1,1,1,1,1};
		private boolean facingLeft = true;

		boolean canMove = true, initialized = false;;

		private boolean alive = false;

		Point isAggro = null;
		int type = 0, eliteT = -1;
		boolean elite = false;
		private Image[] monstreD = new Image[8],monstreG = new Image[5];
		private Image monstreHitD, monstreHitL;
		Animation hitLeft, hitRight, left, right;
		Clip hitSound;

		private Clip dieSound;
		private Point spawnPoint;
		private long timer, deathTimer = 200, regen = 0;

		long aggroTimer = 5000;
		public String name, eliteType = "";
		
		public Monster(int i, int eliteType){
			type = i;
			getAnimations(i);
			switch (i) {
			case COBRA:
				atk = 12;
				def = 2;
				mastery = 50;
				spd = -0.240f;
				maxLife = 13;
				timer = 12000;
				exp = 4;
				lvl = 1;
				avoid = 7;
				name = "Cobra";
				break;
			case BIGCOBRA:
				atk = 22;
				def = 5;
				mastery = 65;
				spd = -0.35f;
				maxLife = 25;
				timer = 30000;
				exp = 9;
				lvl = 3;
				dropchance = 20;
				dropamount = 1;
				avoid = 12;
				name = "Big Cobra";
				break;
			case VERYBIGCOBRA:
				atk = 35;
				def = 8;
				mastery = 50;
				spd = -0.40f;
				maxLife = 41;
				timer = 30000;
				exp = 14;
				lvl = 4;
				dropamount = 1;
				avoid = 20;
				name ="VBig Cobra";
				break;
			case COC:
				atk = 28;
				def = 15;
				mastery = 70;
				spd = -0.37f;
				maxLife = 62;
				timer = 24000;
				exp = 21;
				lvl = 6;
				dropchance = 24;
				dropamount = 1;
				rarechance = 14;
				avoid = 12;
				name ="Beetle";
				break;
			}
			
			generateElite(eliteType);
		}
		
		public Monster(int i, Point spawn) {
			type = i;
			getAnimations(i);
			switch (i) {
			case COBRA:
				atk = 12;
				def = 2;
				mastery = 50;
				spd = -0.240f;
				maxLife = 13;
				timer = 12000;
				exp = 4;
				lvl = 1;
				avoid = 7;
				name = "Cobra";
				break;
			case BIGCOBRA:
				atk = 22;
				def = 5;
				mastery = 65;
				spd = -0.35f;
				maxLife = 25;
				timer = 30000;
				exp = 9;
				lvl = 3;
				dropchance = 20;
				dropamount = 1;
				avoid = 12;
				name = "Big Cobra";
				break;
			case VERYBIGCOBRA:
				atk = 35;
				def = 8;
				mastery = 50;
				spd = -0.40f;
				maxLife = 41;
				timer = 30000;
				exp = 14;
				lvl = 4;
				dropamount = 1;
				avoid = 20;
				name ="VBig Cobra";
				break;
			case COC:
				atk = 28;
				def = 15;
				mastery = 70;
				spd = -0.37f;
				maxLife = 62;
				timer = 24000;
				exp = 21;
				lvl = 6;
				dropchance = 24;
				dropamount = 1;
				rarechance = 14;
				avoid = 12;
				name ="Beetle";
				break;
			case BABYSPIDER:
				atk = 32;
				def = 7;
				mastery = 70;
				spd = -0.47f;
				maxLife = 42;
				timer = 20000;
				exp = 11;
				lvl = 8;
				avoid = 18;
				dropchance = 7;
				dropamount = 1;
				name = "Baby Spider";
				break;
			case MUSHY:
				atk = 58;
				def = 17;
				mastery = 50;
				spd = -0.27f;
				maxLife = 131;
				timer = 30000;
				exp = 40;
				lvl = 11;
				avoid = 15;
				rarechance = 11;
				dropchance = 14;
				dropamount = 1;
				name = "Mushy";
				break;
			case MUSHETTE:
				atk = 32;
				def = 7;
				mastery = 70;
				spd = -0.47f;
				maxLife = 42;
				timer = 20000;
				exp = 10;
				lvl = 8;
				avoid = 18;
				dropchance = 7;
				dropamount = 1;
				name = "Mushette";
				break;
			}
			this.spawnPoint = spawn;
		}

		// initialise le monstre
		public void init() {
			randomElite();
			life = getMaxLife();
			alive = true;
			facingLeft = true;
			setXVelocity(spd);
			setX((float) spawnPoint.getX());
			setY((float) spawnPoint.getY());
			initialized = true;
		}
		
		public void generateElite(int type){
			elite = true;
			switch(type){
			case -1: elite = false; break;
			case DEF : statMultipliers[DEF] = 1.3f; eliteType = "DEF"; break;
			case DMG : statMultipliers[DMG] = 1.3f; eliteType = "DMG"; break;
			case SPD : statMultipliers[SPD] = 1.3f; eliteType = "SPD"; break;
			case SLOW: statMultipliers[DMG] = 1.2f; statMultipliers[DEF] = 1.2f; statMultipliers[SPD] = 0.5f; eliteType = "SLOW"; break;
			}
			if(elite) allStatsMultiplier = 1.3f;
		}
		
		public void randomElite(){
			Random rand = new Random();
			if(1 > rand.nextInt(10)){
				elite = true;
				allStatsMultiplier = 1.3f;
				
				switch(rand.nextInt(4)){
				case DEF : statMultipliers[DEF] = 1.3f; eliteType = "DEF"; eliteT = DEF; break;
				case DMG : statMultipliers[DMG] = 1.3f; eliteType = "DMG"; eliteT = DMG; break;
				case SPD : statMultipliers[SPD] = 1.3f; eliteType = "SPD"; eliteT = SPD; break;
				case SLOW: statMultipliers[DMG] = 1.2f; statMultipliers[DEF] = 1.2f; statMultipliers[SPD] = 0.5f; eliteType = "SLOW"; eliteT = SLOW; break;
				}
			} else {
				eliteT = -1;
				elite = false;
				allStatsMultiplier = 1;
				for(int i = 0; i < 10; i++){
					statMultipliers[i] = 1;
				}
			}
		}
		
		public void jump(){
			setYVelocity(-1);
		}
		
		// change le point ou le monstre apparait
		public void setSpawn(Point Spawn) {
			spawnPoint = Spawn;
		}
		
		public int getExp() {
			return (int)(exp*allStatsMultiplier*allStatsMultiplier);
		}
		
		// retourne la vie du monstre
		public int getLife() {
			return (int)life;
		}
		
		public void setLifeMultiplier(float lifeMultiplier){
			this.maxLife = maxLife * lifeMultiplier/this.lifeMultiplier;
			this.life = life * lifeMultiplier/this.lifeMultiplier;
			this.lifeMultiplier = lifeMultiplier;
		}

		public int getMaxLife() {
			return (int)(maxLife * allStatsMultiplier * statMultipliers[DEF]);
		}

		public int getLevel() {
			if(elite) return lvl + 2;
			return lvl;
		}

		// change les coordonées du monstre
		public void setX(float x) {
				super.setX(x);
		}

		public void setY(float y) {
				super.setY(y);
		}

		// modifie la position et la vitesse du monstre, le fais réapparaitre
		public void update(long timePassed) {
			if (alive) {
				regen+= timePassed;
				if(regen >= 40000/maxLife+200){
					if(life < maxLife)
					life++;
					regen=0;
				}
				if (getXVelocity() == getSpeed() || getXVelocity() == -getSpeed())
					if ((isFacingLeft() && getXVelocity() > 0)
							|| (!(isFacingLeft()) && getXVelocity() < 0))
						setXVelocity(-getXVelocity());
				super.update(timePassed);
				if (cantMoveTime >= 0) {
					cantMoveTime -= timePassed;
				}
				if (cantMoveTime <= 0)
					canMove = true;
				
				if(isAggro!=null){
				if(aggroTimer>=0){
					aggroTimer-=timePassed;
				}
				if(aggroTimer<=0) isAggro = null;
				}
				
			} else {
				deathTimer -= timePassed;
				if (deathTimer <= 0) {
					init();
				}
			}

		}

		// retourne si le monstre est en vie
		public boolean isAlive() {
			return alive;
		}

		// retourne si le monstre peut bouger
		public boolean canMove() {
			return canMove;
		}


		// fais mourir le monstre
		void die() {
			if(dieSound != null) dieSound.start();
			alive = false; isAggro = null;
			deathTimer = timer;
		}
		
		int getdropamount(){
			if(elite) return dropamount+1;
			return dropamount;
		}
		
		int getdropchance(){
			return (int)(dropchance*allStatsMultiplier);
		}
		
		int getrarechance(){
			return (int)(rarechance*allStatsMultiplier);
		}

		// load les animations du monstre
		private void getAnimations(int i) {
			hitLeft = new Animation();
			hitRight = new Animation();
			right = new Animation();
			left = new Animation();
			loadpics(i);
			switch (i) {
			case COBRA:
			case BIGCOBRA:
			case VERYBIGCOBRA:
				right.addScene(monstreD[1], 220);
				right.addScene(monstreD[2], 220);
				left.addScene(monstreG[1], 220);
				left.addScene(monstreG[2], 220);
				hitLeft.addScene(monstreG[1], 200);
				hitRight.addScene(monstreD[1],200);
				break;
			case COC:
				right.addScene(monstreD[1],110);
				right.addScene(monstreD[2],110);
				right.addScene(monstreD[3],110);
				right.addScene(monstreD[4],110);
				left.addScene(monstreG[1],110);
				left.addScene(monstreG[2],110);
				left.addScene(monstreG[3],110);
				left.addScene(monstreG[4],110);
				hitLeft.addScene(monstreG[1], 200);
				hitRight.addScene(monstreD[1],200);
				break;
			case MUSH:
				right.addScene(monstreD[0], 50);
				right.addScene(monstreD[1], 50);
				right.addScene(monstreD[2], 50);
				right.addScene(monstreD[3], 50);
				right.addScene(monstreD[4], 50);
				right.addScene(monstreD[5], 50);
				right.addScene(monstreD[6], 50);
				right.addScene(monstreD[7], 50);
				right.addScene(monstreD[8], 50);
				right.addScene(monstreD[9], 50);
				left.addScene(monstreG[0], 50);
				left.addScene(monstreG[1], 50);
				left.addScene(monstreG[2], 50);
				left.addScene(monstreG[3], 50);
				left.addScene(monstreG[4], 50);
				left.addScene(monstreG[5], 50);
				left.addScene(monstreG[6], 50);
				left.addScene(monstreG[7], 50);
				left.addScene(monstreG[8], 50);
				left.addScene(monstreG[9], 50);
				hitLeft.addScene(monstreG[5], 200);
				hitRight.addScene(monstreD[5], 200);
				break;
			case BABYSPIDER:
				right.addScene(monstreD[0], 200);
				right.addScene(monstreD[1], 200);
				left.addScene(monstreG[0], 200);
				left.addScene(monstreG[1], 200);
				hitLeft.addScene(monstreG[0], 200);
				hitRight.addScene(monstreD[0], 200);
				break;
			case MUSHY:
				right.addScene(monstreD[1], 190);
				right.addScene(monstreD[2], 90);
				right.addScene(monstreD[3], 190);
				right.addScene(monstreD[2], 90);
				left.addScene(monstreG[1], 190);
				left.addScene(monstreG[2], 90);
				left.addScene(monstreG[3], 190);
				left.addScene(monstreG[2], 90);
				hitLeft.addScene(monstreG[1], 200);
				hitRight.addScene(monstreD[1], 200);
				break;
			}
		}

		// retourne l'espace ou la prochaine platforme devrait être
		public Rectangle getNextFloor() {
			if (facingLeft)
				return new Rectangle(getX() - 25, getY() + getHeight() - 5, 20,
						15);
			return new Rectangle(getX() + getWidth() + 5, getY() + getHeight()
					- 5, 20, 15);
		}

		// retourne le coté du monstre
		public Rectangle getSide() {
			if (getXVelocity() < 0)
				return new Rectangle(getX() - 10, getY() + 10, 20,
						getHeight() - 25);
			return new Rectangle(getX() + getWidth() - 10, getY() + 10, 20,
					getHeight() - 25);
		}

		// load les images du monstre
		public void loadpics(int i) {
			switch (i) {
			case COBRA:
				monstreD[1] = newImage("/cobra1D.png");
				monstreD[2] = newImage("/cobra2D.png");
				monstreG[1] = newImage("/cobra1G.png");
				monstreG[2] = newImage("/cobra2G.png");
				break;
			case BIGCOBRA:
				monstreD[1] = newImage("/bigcobra1D.png");
				monstreD[2] = newImage("/bigcobra2D.png");
				monstreG[1] = newImage("/bigcobra1G.png");
				monstreG[2] = newImage("/bigcobra2G.png");
				break;
			case VERYBIGCOBRA:
				monstreD[1] = newImage("/verybigcobra1D.png");
				monstreD[2] = newImage("/verybigcobra2D.png");
				monstreG[1] = newImage("/verybigcobra1G.png");
				monstreG[2] = newImage("/verybigcobra2G.png");
				break;
			case COC:
				monstreD[1] = newImage("/coc1D.png");
				monstreD[2] = newImage("/coc2D.png");
				monstreD[3] = newImage("/coc3D.png");
				monstreD[4] = newImage("/coc4D.png");
				monstreG[1] = newImage("/coc1G.png");
				monstreG[2] = newImage("/coc2G.png");
				monstreG[3] = newImage("/coc3G.png");
				monstreG[4] = newImage("/coc4G.png");
				break;
			case MUSH:
				monstreD[0] = newImage("/mushjumpR.png");
				monstreD[1] = newImage("/mushjumpR1.png");
				monstreD[2] = newImage("/mushjumpR2.png");
				monstreD[3] = newImage("/mushjumpR3.png");
				monstreD[4] = newImage("/mushjumpR4.png");
				monstreD[5] = newImage("/mushjumpR5.png");
				monstreD[6] = newImage("/mushjumpR6.png");
				monstreD[7] = newImage("/mushjumpR7.png");
				monstreD[8] = newImage("/mushjumpR8.png");
				monstreD[9] = newImage("/mushjumpR9.png");
				monstreG[0] = newImage("/mushjumpL.png");
				monstreG[1] = newImage("/mushjumpL1.png");
				monstreG[2] = newImage("/mushjumpL2.png");
				monstreG[3] = newImage("/mushjumpL3.png");
				monstreG[4] = newImage("/mushjumpL4.png");
				monstreG[5] = newImage("/mushjumpL5.png");
				monstreG[6] = newImage("/mushjumpL6.png");
				monstreG[7] = newImage("/mushjumpL7.png");
				monstreG[8] = newImage("/mushjumpL8.png");
				monstreG[9] = newImage("/mushjumpL9.png");
				break;
			case BABYSPIDER:
				monstreG[0] = newImage("/babyspider1G.png");
				monstreG[1] = newImage("/babyspider2G.png");
				monstreD[0] = newImage("/babyspider1D.png");
				monstreD[1] = newImage("/babyspider2D.png");
				break;
			case MUSHY:
				monstreD[1] = newImage("MUSHY1D.png");
				monstreD[2] = newImage("MUSHY2D.png");
				monstreD[3] = newImage("MUSHY3D.png");
				monstreG[1] = newImage("MUSHY1G.png");
				monstreG[2] = newImage("MUSHY2G.png");
				monstreG[3] = newImage("MUSHY3G.png");
				break;
			}
		}

		// retourne l'animation du monstre
		public Animation getAnimation(boolean left) {
			if (left){
				if(!canMove) return hitLeft;
				return this.left;
			}
			else{
				if(!canMove) return hitRight;
				return right;
			}
		}
		
		public int getWidth(){
			int width = super.getWidth();
			if(width!=0) return width;
			else return monstreD[1].getWidth(null);
		}
		
		public int getHeight(){
			int height = super.getHeight();
			if(height!=0) return height;
			else return monstreD[1].getHeight(null);
		}
		
		public Image newImage(String source) {
			return new ImageIcon(getClass().getResource(source)).getImage();
		}

		// retourne si le monstre "regarde" a gauche
		public boolean isFacingLeft() {
			return facingLeft;
		}

		public void setFacingLeft(boolean facingLeft) {
			this.facingLeft = facingLeft;
		}

		// retourne toute la surface du monstre
		public Rectangle getArea() {
			return new Rectangle(getX(), getY(), getWidth(), getHeight());
		}

		// retourne la défence du monstre
		public int getDefense() {
			return (int)(def * statMultipliers[DEF] * allStatsMultiplier);
		}

		int getAtk(){
			return (int)(atk * allStatsMultiplier * statMultipliers[DMG]);
		}
		
		int getMastery(){
			int mast = (int)(mastery * allStatsMultiplier * statMultipliers[DMG]);
			if(mast >= 100) return 99;
			return mast;
		}

		// fais tomber le monstre
		public void fall(long timePassed) {
			if (getYVelocity() < 0.8f)
				setYVelocity(getYVelocity() + 0.005f * timePassed);
		}

		// retourne la vitesse de base du monstre
		public float getSpeed() {
			return spd*allStatsMultiplier*statMultipliers[SPD];
		}

		// retourne la base du monstre
		public Rectangle getBase() {
			return new Rectangle(getX() + 10, getY() + getHeight() - 15,
					getWidth() - 20, 20);
		}

	}
