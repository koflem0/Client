
public class ReceivedEffect
{
	int type = 0, x = 0, y = 0, number =0;
	long currentTime = 0;
	boolean active = true;
	Animation a;
	
	ReceivedEffect(Animation a){
		this.a=a;
		this.a.start();
	}
	
	void setTime(long time){
		a.setTime(currentTime = time);
	}
}
