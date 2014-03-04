import java.io.*;

public class Map{
	public final int MAP_WIDTH = 15;
	public final int MAP_HEIGHT = 15;
	public Tile[][] tile;

	//CONSTRUCTOR
	public Map(String filename){
		tile = new Tile[MAP_WIDTH][MAP_HEIGHT];
		readMap(filename);
	}
	//readMap()
	public void readMap(String filename){
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String word = br.readLine();
			for(int i=0;i<MAP_WIDTH;i++){
				for(int j=0;j<MAP_HEIGHT;j++){
					tile[j][i] = new Tile(i, j, Integer.parseInt("" + word.charAt(j)));
				}
				word = br.readLine();
			}
			br.close();
		}catch(Exception e){
			System.out.println("Error reading from file: " + e);
		}
	}
	//getXTile()
	public int getXTile(float x){
		return (int)(Math.floor(x/tile[0][0].TILE_WIDTH));
	}
	//getYTile()
	public int getYTile(float y){
		return (int)(Math.floor(y/tile[0][0].TILE_HEIGHT));

	}
	//isblocked()
	public boolean isBlocked(int x, int y){
		switch(tile[x][y].getBlock()){
			case destructableBlock : return true;
			case indestructableBlock : return true;
		}
		return false;
	}
	//clearExplosions()
	public void clearExplosions(){
		for(int i=0;i<MAP_WIDTH;i++){
			for(int j=0;j<MAP_HEIGHT;j++){
				tile[i][j].setExplosion(false);
			}
		}
	}
}