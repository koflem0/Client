
public class ReceivedProjectile
{
	long currentTime;
	int x=0, y=0, type=0;
	boolean active = true;
	Animation a;
	
	ReceivedProjectile(Animation a){
		this.a=a;
		this.a.start();
	}
	
	void setTime(long time){
		a.setTime(time);
		currentTime = time;
	}
}
