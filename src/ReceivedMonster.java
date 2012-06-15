import java.awt.Rectangle;


public class ReceivedMonster {
	public int x = 0, y = 0, life = 0, maxLife = 1, map = -1, eliteT = -1, type, atk, def, avoid, width, height, number, mastery, lvl, exp, dropchance, dropamount, rarechance;
	public boolean alive = false, canMove = true, facingLeft = true;
	public String name, eliteType;
	private Animation left, right;
	
	public ReceivedMonster(int type, int eliteType){
		Monster m = new Monster(type, eliteType);
		this.type = type;
		this.name = m.name;
		this.left = m.left;
		this.right = m.right;
		this.atk = m.getAtk();
		this.def = m.getDefense();
		this.width = m.getWidth();
		this.height = m.getHeight();
		this.avoid = m.avoid;
		this.mastery = m.getMastery();
		this.eliteT = eliteType;
		this.eliteType=m.eliteType;
		this.exp = m.getExp();
		this.lvl = m.getLevel();
		this.dropchance = m.getdropchance();
		this.dropamount=m.getdropamount();
		this.rarechance=m.getrarechance();
	}
	
	public void setEliteType(int eliteType){
		if(this.eliteT != eliteType){
			Monster m = new Monster(type, eliteType);
			this.atk = m.getAtk();
			this.def = m.getDefense();
			this.mastery = m.getMastery();
			this.eliteT = eliteType;
			this.lvl = m.getLevel();
			this.exp = m.getExp();
			this.eliteType=m.eliteType;
			this.dropchance = m.getdropchance();
			this.dropamount=m.getdropamount();
			this.rarechance=m.getrarechance();
		}
	}
	
	public Rectangle getArea(){
		return new Rectangle(x,y,width,height);
	}
	public Animation getAnimation(){
		if(facingLeft) return left; else return right;
	}
	
	public void update(long timePassed){
		getAnimation().update(timePassed);
	}
}
