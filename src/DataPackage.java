import java.io.Serializable;
import java.util.Vector;


public class DataPackage implements Serializable{

	private static final long serialVersionUID = 6405012426139982467L;
	
	public int x = 0, y = 0, map = -1, animation = 0, life = 0, maxLife = 1, currentSkill = -1;
	public boolean invincible = false, alive = true, playing = true, isFacingLeft = true;
	
	public String username = "";
	
	public Vector<ProjectileData> projectile = new Vector<ProjectileData>();
	
	public Vector<HitData> hit = new Vector<HitData>();
	
	public Vector<EffectData> effect = new Vector<EffectData>();
  
}
