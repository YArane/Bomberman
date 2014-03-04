public class Entity{
	private float xPosition;
	private float yPosition;
	
	//CONSTRUCTOR
	public Entity(float x, float y){
		xPosition = x;
		yPosition = y;
	}
	//getXPosition()
	public float getXPosition(){
		return xPosition;
	}
	//getYPosition()
	public float getYPosition(){
		return yPosition;
	}
	//setXPosition()
	public void setXPosition(float x){
		xPosition = x;
	}
	//setYPosition()
	public void setYPosition(float y){
		yPosition = y;
	}
}