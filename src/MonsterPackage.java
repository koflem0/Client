import java.io.Serializable;


public class MonsterPackage implements Serializable{

	private static final long serialVersionUID = 7108639750707236998L;
	public int life = 0, maxLife = 1, x = 0, y = 0, number = -1, type = -1, map = -1, eliteType = -1, exp = 0;
	boolean canMove = true, isFacingLeft = true, alive = false, initialized = false;
	
}
