import java.util.ArrayList;

public class Animation{
	private ArrayList<Sprite> sprite;
	private int spriteIndex;
	private long animationTime;
	private long totalTime;

	//CONSTRUCTOR
	public Animation(){
		sprite = new ArrayList<Sprite>();
		totalTime = 0;
		start();
	}
	//adds image to the sprite arrayList
	public synchronized void addSprite(int x1, int y1, int x2, int y2, long time){ //synchronized makes sure multiple threads dont run this at the same time
		totalTime = totalTime + time;
		sprite.add(new Sprite(x1, y1, x2, y2, totalTime));
	}
	//starts animation
	public synchronized void start(){
		animationTime = 0;
		spriteIndex = 0;
	}
	//changes sprites
	public synchronized void update(long timePassed){
		if(sprite.size() > 1){
			animationTime = animationTime + timePassed;
			if(animationTime >= totalTime){
				animationTime = 0;
				spriteIndex = 0;
			}
			while(animationTime > getSprite(spriteIndex).endTime){
				spriteIndex++;
			}
		}
	}
	//getXSprite1
	public synchronized int getXSprite1(){
		return getSprite(spriteIndex).xSprite1;
	}
	//getYSprite1
	public synchronized int getYSprite1(){
		return getSprite(spriteIndex).ySprite1;
	}
	//getXSprite2
	public synchronized int getXSprite2(){
		return getSprite(spriteIndex).xSprite2;
	}
	//getYSprite2
	public synchronized int getYSprite2(){
		return getSprite(spriteIndex).ySprite2;
	}
	//get sprite
	private Sprite getSprite(int x){
		return (Sprite)sprite.get(x);
	}

	//SPRITE INNER CLASS//
	private class Sprite{
		int xSprite1, ySprite1, xSprite2, ySprite2;
		long endTime;

		public Sprite(int x1, int y1, int x2, int y2, long et){
			xSprite1 = x1;
			ySprite1 = y1;
			xSprite2 = x2;
			ySprite2 = y2;
			endTime = et;
		}
	}
}