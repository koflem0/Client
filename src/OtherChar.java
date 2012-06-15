
public class OtherChar{
	
	public int x = 0, y = 0, life = 0, maxLife = 1, counter = 0, currentSkill = -1, map = -1;
	public boolean alive = true, invincible = false, isPlaying = true, blink = false, isFacingLeft;
	public String username = "";
	private Animation a;
	private int animationType = -1;
	
	public OtherChar(){
		isPlaying = true;
	}
	
	public Animation getAnimation(){
		return a;
	}
	
	public int getAnimationType() {
		return animationType;
	}

	public void setAnimation(Animation a, int type) {
		this.animationType = type;
		this.a = a;
	}
	public void setAnimation(Animation a) {
		this.a = a;
	}
	
	public void update(long timePassed){
		a.update(timePassed);
	}
	
	public float getLifePercentage(){
		float f = life * 100;
		f = f / maxLife;
		return f;
	}
	
	
	
}
