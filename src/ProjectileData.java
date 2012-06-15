import java.io.Serializable;


	public class ProjectileData implements Serializable{
		private static final long serialVersionUID = -660571475945905070L;
		int x = 0, y = 0, type = 0, number = 0;
		long currentTime = 0;
		boolean active = true;
	}
