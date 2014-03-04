import java.util.ArrayList;

public class Player extends Entity{
	private boolean alive;
	private boolean human;
	public ArrayList<Bomb> bombs;
	private Ability abilities;
	public Animation currentAnimation;
	private float xVelocity;
	private float yVelocity;
	public enum MOVEMENT{
		still,
		right, 
		left,
		up,
		down
	};
	private MOVEMENT movement;

	//CONSTRUCTOR
	public Player(boolean h, float x, float y, Animation a){
		super(x, y);
		xVelocity = 0f;
		yVelocity = 0f;
		movement = MOVEMENT.still;
		alive = true;
		human = h;
		bombs = new ArrayList<Bomb>();
		abilities = new Ability(0.08f, 1, 2);
		currentAnimation = a;
	}
	//update()
	public void update(long timePassed){
		setXPosition(getXPosition() + xVelocity * timePassed);
		setYPosition(getYPosition() + yVelocity * timePassed);
	}
	//alive()
	public boolean alive(){
		return alive;
	}
	//setLife()
	public void setLife(boolean a){
		alive = a;
	}
	//isHuman()
	public boolean isHuman(){
		return human;
	}
	//getMovement()
	public MOVEMENT getMovement(){
		return movement;
	}
	//setMovement()
	public void setMovement(String m){
		switch(m){
			case "still" : movement = MOVEMENT.still;
			break;
			case "right" : movement = MOVEMENT.right;
			break;
			case "left" : movement = MOVEMENT.left;
			break;
			case "up" : movement = MOVEMENT.up;
			break;
			case "down" : movement = MOVEMENT.down;
			break;
		}
	}
	//getXVelocity()
	public float getXVelocity(){
		return xVelocity;
	}
	//getYVelocity()
	public float getYVelocity(){
		return yVelocity;
	}
	//setXVelocity()
	public void setXVelocity(float x){
		xVelocity = x;
	}
	//setYVelocity()
	public void setYVelocity(float y){
		yVelocity = y;
	}
	//getCurrentAnimation()
	public Animation getCurrentAnimation(){
		return currentAnimation;
	}
	//setCurrentAnimation()
	public void setCurrentAnimation(Animation a){
		currentAnimation = a;
	}
	//getPowerUp()
	public Ability getAbilities(){
		return abilities;
	}

	//POWERUP INNER CLASS//
	public class Ability{
		float movementSpeed;
		int maxBombs;
		int firePower;
		//CONSTRUCTOR
		public Ability(float s, int b, int f){
			movementSpeed = s;
			maxBombs = b;
			firePower = f;
		}
		//getSpeed()
		public float getSpeed(){
			return abilities.movementSpeed;
		}
		//setSpeed()
		public void setSpeed(float s){
			abilities.movementSpeed = s;
		}
		//getMaxBombs()
		public int getMaxBombs(){
			return abilities.maxBombs;
		}
		//getFirePower()
		public int getFirePower(){
			return abilities.firePower;
		}
	}
}