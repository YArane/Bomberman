public class Tile{
	private int xCoordinate;
	private int yCoordinate;
	public final float TILE_WIDTH = 16f;
	public final float TILE_HEIGHT = 16f;
	public int xSprite1, xSprite2, ySprite1, ySprite2;
	private boolean explosion;
	public enum BLOCK{
		destructableBlock,
		indestructableBlock,
		walkingPath1,
		walkingPath2,
	};
	public BLOCK block;

	//CONSTRUCTOR
	public Tile(int x, int y, int b){
		xCoordinate = x;
		yCoordinate = y;
		setBlock(b);
		explosion = false;
	}
	//getXCoordinate()
	public int getXCoordinate(){
		return xCoordinate;
	}
	//getYCoordinate()
	public int getYCoordinate(){
		return yCoordinate;
	}
	//getBlock()
	public BLOCK getBlock(){
		return block;
	}
	//getExplosion()
	public boolean getExplosion(){
		return explosion;
	}
	//setExplosion()
	public void setExplosion(boolean e){
		explosion = e;
	}
	//setBlock()
	public void setBlock(int b){
		switch(b){
			case 0 : block = BLOCK.walkingPath1; xSprite1 = 122; ySprite1 = 175; xSprite2 = 138; ySprite2 = 191;	
			break;
			case 1 : block = BLOCK.indestructableBlock; xSprite1 = 71; ySprite1 = 175; xSprite2 = 87; ySprite2 = 191;	
			break;
			case 2 : block = BLOCK.destructableBlock; xSprite1 = 88; ySprite1 = 175; xSprite2 = 104; ySprite2 = 191;	
			break;
			case 3 : block = BLOCK.walkingPath2; xSprite1 = 105; ySprite1 = 175; xSprite2 = 121; ySprite2 = 191;	
			break;
		}
	}
}