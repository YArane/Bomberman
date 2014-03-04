public class Bomb extends Entity{
	private int blastRadius;
	private long startTime;
	private long currentTime;
	public final long DETONATE_TIME = 3200l;
	private Animation currentAnimation;

	//CONSTRUCTOR
	public Bomb(float x, float y, int r, long s, long c, Animation a){
		super(x, y);
		blastRadius = r;
		startTime = s;
		currentTime = c;
		currentAnimation = a;
	}
	//getStartTime()
	public long getStartTime(){
		return startTime;
	}
	//getCurrentTime()
	public long getCurrentTime(){
		return currentTime;
	}
	//setCurrentTime()
	public void setCurrentTime(long t){
		currentTime = t;
	}
	//getBlastRadius()
	public int getBlastRadius(){
		return blastRadius;
	}
	//setBlastRadius()
	public void setBlastRadius(int r){
		blastRadius = r;
	}
	//getCurrentAnimation()
	public Animation getCurrentAnimation(){
		return currentAnimation;
	}
	//setCurrentAnimation()
	public void setCurrentAnimation(Animation a){
		currentAnimation = a;
	}
}