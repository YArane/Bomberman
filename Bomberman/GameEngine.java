import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameEngine implements KeyListener, MouseMotionListener{
	private boolean running = true, moveRight, moveLeft, moveUp, moveDown;
	private Player[] player;
	private Animation rightAnimation, leftAnimation, downAnimation, upAnimation, bombAnimation, explosionAnimation, deathAnimation;
	private Map map;
	private Image spriteSheet1;
	private final float SCALING_FACTOR = 2f;
	private final int MOVEMENT_ANIMATION_DELAY = 150, BOMB_ANIMATION_DELAY = 400, EXPLOSION_ANIMATION_DELAY = 150, SPRITE_HEIGHT = 24, SPRITE_WIDTH = 15, TOP_HALF = 9;
	private String message = "";
	//initiate()
	public void initiate(Map m){
		map = m;
		//instantiating animations
		rightAnimation = new Animation();
		rightAnimation.addSprite(179, 38, 194, 62, MOVEMENT_ANIMATION_DELAY);
		rightAnimation.addSprite(161, 38, 177, 62, MOVEMENT_ANIMATION_DELAY);
		rightAnimation.addSprite(197, 38, 212, 62, MOVEMENT_ANIMATION_DELAY);
		leftAnimation = new Animation();
		leftAnimation.addSprite(124, 38, 139, 62, MOVEMENT_ANIMATION_DELAY);
		leftAnimation.addSprite(106, 38, 120, 62, MOVEMENT_ANIMATION_DELAY);
		leftAnimation.addSprite(143, 38, 158, 62, MOVEMENT_ANIMATION_DELAY);
		upAnimation = new Animation();
		upAnimation.addSprite(237, 38, 252, 62, MOVEMENT_ANIMATION_DELAY);
		upAnimation.addSprite(219, 38, 234, 62, MOVEMENT_ANIMATION_DELAY);
		upAnimation.addSprite(255, 38, 269, 62, MOVEMENT_ANIMATION_DELAY);
		downAnimation = new Animation();
		downAnimation.addSprite(70, 38, 85, 62, MOVEMENT_ANIMATION_DELAY);
		downAnimation.addSprite(88, 38, 103, 62, MOVEMENT_ANIMATION_DELAY);
		downAnimation.addSprite(52, 38, 67, 62, MOVEMENT_ANIMATION_DELAY);
		bombAnimation = new Animation();
		bombAnimation.addSprite(50, 255, 66, 272, BOMB_ANIMATION_DELAY);
		bombAnimation.addSprite(34, 257, 48, 272, BOMB_ANIMATION_DELAY);
		bombAnimation.addSprite(20, 258, 32, 272, BOMB_ANIMATION_DELAY);
		bombAnimation.addSprite(34, 257, 48, 272, BOMB_ANIMATION_DELAY);
		explosionAnimation = new Animation();
		explosionAnimation.addSprite(154, 259, 232, 337, EXPLOSION_ANIMATION_DELAY);
		explosionAnimation.addSprite(234, 258, 314, 337, EXPLOSION_ANIMATION_DELAY);
		explosionAnimation.addSprite(317, 258, 397, 337, EXPLOSION_ANIMATION_DELAY);
		explosionAnimation.addSprite(402, 258, 482, 337, EXPLOSION_ANIMATION_DELAY);
		explosionAnimation.addSprite(486, 258, 566, 337, EXPLOSION_ANIMATION_DELAY);
		deathAnimation = new Animation();
		deathAnimation.addSprite(344, 40, 359, 61, MOVEMENT_ANIMATION_DELAY);
		deathAnimation.addSprite(361, 40, 376, 61, MOVEMENT_ANIMATION_DELAY);
		deathAnimation.addSprite(378, 40, 393, 61, MOVEMENT_ANIMATION_DELAY);
		deathAnimation.addSprite(395, 40, 410, 61, MOVEMENT_ANIMATION_DELAY);
		//instantiating players
		player = new Player[4];
		player[0] = new Player(true, map.tile[1][1].getXCoordinate()*map.tile[1][1].TILE_WIDTH, map.tile[1][1].getYCoordinate()*map.tile[1][1].TILE_HEIGHT-TOP_HALF, downAnimation);
		player[1] = new Player(false, map.tile[13][1].getXCoordinate()*map.tile[13][1].TILE_WIDTH, map.tile[13][1].getYCoordinate()*map.tile[13][1].TILE_HEIGHT-TOP_HALF, downAnimation);
		player[2] = new Player(false, map.tile[1][13].getXCoordinate()*map.tile[1][13].TILE_WIDTH, map.tile[1][13].getYCoordinate()*map.tile[1][13].TILE_HEIGHT-TOP_HALF, downAnimation);
		player[3] = new Player(false, map.tile[13][13].getXCoordinate()*map.tile[13][13].TILE_WIDTH, map.tile[13][13].getYCoordinate()*map.tile[13][13].TILE_HEIGHT-TOP_HALF, downAnimation);
		//loading sprite sheet
		spriteSheet1 = new ImageIcon("spriteSheet1.png").getImage();

	}

	//main game loop
	public void gameLoop(ScreenManager screen){
		screen.getFullScreenWindow().addKeyListener(this);
		screen.getFullScreenWindow().addMouseMotionListener(this);
		long startingTime = System.currentTimeMillis();
		long cumilativeTime = startingTime;
			
		while(running){
			long timePassed = System.currentTimeMillis() - cumilativeTime;
			cumilativeTime = cumilativeTime + timePassed;
			update(timePassed);

			//draws and updates screen
			Graphics2D g = screen.getGraphics();
			draw(g);
			g.dispose();
		
			screen.update();

			try{
				Thread.sleep(1);
			}catch(Exception e){}
		}
	}
	//update()
	public void update(long timePassed){
		for(int i=0;i<player.length;i++){
			//update movement & animations
			switch(player[i].getMovement()){
				case right : player[i].setXVelocity(player[i].getAbilities().getSpeed()); player[i].setCurrentAnimation(rightAnimation); player[i].currentAnimation.update(timePassed);
				break;
				case left : player[i].setXVelocity(-player[i].getAbilities().getSpeed()); player[i].setCurrentAnimation(leftAnimation); player[i].currentAnimation.update(timePassed);
				break;
				case up : player[i].setYVelocity(-player[i].getAbilities().getSpeed()); player[i].setCurrentAnimation(upAnimation); player[i].currentAnimation.update(timePassed);
				break;
				case down : player[i].setYVelocity(player[i].getAbilities().getSpeed()); player[i].setCurrentAnimation(downAnimation); player[i].currentAnimation.update(timePassed);
				break;
			}
			message = (map.getXTile(player[i].getXPosition() + SPRITE_WIDTH)-1) + ", " + (map.getYTile(player[i].getYPosition() + SPRITE_HEIGHT)+1);
			if(isValidLocation(player[i].getXPosition() + player[i].getXVelocity() * timePassed, player[i].getYPosition() + player[i].getYVelocity() * timePassed)){
				player[i].update(timePassed);
			}else{}/*
				if(player[i].getYVelocity() > 0){
					if(((player[i].getXPosition() + SPRITE_WIDTH)%map.tile[0][0].TILE_WIDTH) < (map.tile[0][0].TILE_WIDTH/2)){
					if(!map.isBlocked(map.getXTile(player[i].getXPosition() + SPRITE_WIDTH)-1, map.getYTile(player[i].getYPosition() + SPRITE_HEIGHT)+1)){
						//player[i].setXVelocity(-player[i].getAbilities().getSpeed());
						//player[i].setYVelocity(0f);
						player[i].setXPosition(map.tile[map.getXTile(player[i].getXPosition())][map.getYTile(player[i].getYPosition())].getXCoordinate()*map.tile[1][1].TILE_WIDTH);
						player[i].update(timePassed);
					}
					}else if(((player[i].getXPosition() + SPRITE_WIDTH)%map.tile[0][0].TILE_WIDTH) > (map.tile[0][0].TILE_WIDTH/2)){
					if(!map.isBlocked(map.getXTile(player[i].getXPosition())+1, map.getYTile(player[i].getYPosition() + SPRITE_HEIGHT)+1)){
						//player[i].setXVelocity(player[i].getAbilities().getSpeed());
						//player[i].setYVelocity(0f);
						player[i].setXPosition(map.tile[map.getXTile(player[i].getXPosition())+1][map.getYTile(player[i].getYPosition())].getXCoordinate()*map.tile[1][1].TILE_WIDTH);
						player[i].update(timePassed);
					}
					}
				}*/	 	//implement sliding  //implement sliding
			//update bombs
			for(int j=0;j<player[i].bombs.size();j++){
				player[i].bombs.get(j).setCurrentTime(player[i].bombs.get(j).getCurrentTime() + timePassed);
				player[i].bombs.get(j).getCurrentAnimation().update(timePassed);
				//handling explosion/destruction
				if(player[i].bombs.get(j).getCurrentTime() - player[i].bombs.get(j).getStartTime() >= player[i].bombs.get(j).DETONATE_TIME && player[i].bombs.get(j).getCurrentAnimation() == bombAnimation){
					player[i].bombs.get(j).setCurrentAnimation(explosionAnimation);
					handleExplosion(map.getXTile(player[i].bombs.get(j).getXPosition()), map.getYTile(player[i].bombs.get(j).getYPosition()), i, j);
					player[i].bombs.get(j).setXPosition(player[i].bombs.get(j).getXPosition()-32);
					player[i].bombs.get(j).setYPosition(player[i].bombs.get(j).getYPosition()-32);
				}				
				//check death
				if(map.tile[map.getXTile(player[i].getXPosition())][map.getYTile(player[i].getYPosition()+TOP_HALF)].getExplosion()){
					player[i].setCurrentAnimation(deathAnimation);
					player[i].currentAnimation.update(timePassed);
				}
				//removing bomb after explosion
				if(player[i].bombs.get(j).getCurrentTime() - player[i].bombs.get(j).getStartTime() >= player[i].bombs.get(j).DETONATE_TIME+600){
					player[i].bombs.remove(j);
					map.clearExplosions();
				}
			}
			
		}
	}
	//handleExplosion
	public void handleExplosion(int x, int y, int k, int j){
		boolean exit = false;
		//looks to the left of the bomb
		for(int i=x;i>0 && (x-i)<=player[k].bombs.get(j).getBlastRadius();i--){
			switch(map.tile[i][y].getBlock()){
				case walkingPath1 : case walkingPath2 :
					map.tile[i][y].setExplosion(true);
				break;
				case destructableBlock :
					exit = true;
					switch(map.tile[i][y-1].getBlock()){
						case destructableBlock : case indestructableBlock :
							map.tile[i][y].setBlock(3);
						break;
						case walkingPath1 : case walkingPath2 :
							map.tile[i][y].setBlock(0);
						break;
					}
					switch(map.tile[i][y+1].getBlock()){
						case walkingPath2 :
							map.tile[i][y+1].setBlock(0);
						break;
					}
					map.tile[i][y].setExplosion(true);
				break;
				case indestructableBlock :
					exit = true;
				break;
			}
			if(exit){
				break;
			}
		}
		exit = false;
		//looks to the right of the bomb
		for(int i=x;i<15 && (i-x)<=player[k].bombs.get(j).getBlastRadius();i++){
			switch(map.tile[i][y].getBlock()){
				case walkingPath1 : case walkingPath2 :
					map.tile[i][y].setExplosion(true);
				break;
				case destructableBlock :
					exit = true;
					switch(map.tile[i][y-1].getBlock()){
						case destructableBlock : case indestructableBlock :
							map.tile[i][y].setBlock(3);
						break;
						case walkingPath1 : case walkingPath2 :
							map.tile[i][y].setBlock(0);
						break;
					}
					switch(map.tile[i][y+1].getBlock()){
						case walkingPath2 :
							map.tile[i][y+1].setBlock(0);
						break;
					}
					map.tile[i][y].setExplosion(true);
				break;
				case indestructableBlock :
					exit = true;
				break;
			}
			if(exit){
				break;
			}			
		}
		exit = false;
		//looks above the bomb
		for(int i=y;i>0 && (y-i)<=player[k].bombs.get(j).getBlastRadius();i--){
			switch(map.tile[x][i].getBlock()){
				case walkingPath1 : case walkingPath2 :
					map.tile[x][i].setExplosion(true);
				break;
				case destructableBlock :
					exit = true;
					switch(map.tile[x][i-1].getBlock()){
						case destructableBlock : case indestructableBlock :
							map.tile[x][i].setBlock(3);
						break;
						case walkingPath1 : case walkingPath2 :
							map.tile[x][i].setBlock(0);
						break;
					}
					switch(map.tile[x][i+1].getBlock()){
						case walkingPath2 :
							map.tile[x][i+1].setBlock(0);
						break;
					}
					map.tile[x][i].setExplosion(true);
				break;
				case indestructableBlock :
					exit = true;
				break;
			}
			if(exit){
				break;
			}
		}
		exit = false;
		//looks below the bomb
		for(int i=y;i<15 && (i-y)<=player[k].bombs.get(j).getBlastRadius();i++){
			switch(map.tile[x][i].getBlock()){
				case walkingPath1 : case walkingPath2 :
					map.tile[x][i].setExplosion(true);
				break;
				case destructableBlock :
					exit = true;
					switch(map.tile[x][i-1].getBlock()){
						case destructableBlock : case indestructableBlock :
							map.tile[x][i].setBlock(3);
						break;
						case walkingPath1 : case walkingPath2 :
							map.tile[x][i].setBlock(0);
						break;
					}
					switch(map.tile[x][i+1].getBlock()){
						case walkingPath2 :
							map.tile[x][i+1].setBlock(0);
						break;
					}
					map.tile[x][i].setExplosion(true);
				break;
				case indestructableBlock :
					exit = true;
				break;
			}
			if(exit){
				break;
			}
		}
	}
	//isValidLocation()
	public boolean isValidLocation(float x, float y){
		//looks the four corners of the sprite and checks to see if there will be a collision
		//top left corner
		if(map.isBlocked(map.getXTile(x), map.getYTile(y + TOP_HALF))){
			return false;
		}
		//top right corner
		if(map.isBlocked(map.getXTile(x + SPRITE_WIDTH), map.getYTile(y + TOP_HALF))){
			return false;
		}
		//bottom left corner
		if(map.isBlocked(map.getXTile(x), map.getYTile(y + SPRITE_HEIGHT))){
			return false;
		}
		//bottom right corner
		if(map.isBlocked(map.getXTile(x + SPRITE_WIDTH), map.getYTile(y + SPRITE_HEIGHT))){
			return false;
		}
		return true;
	}
	//stop()
	public void stop(){
		running = false;
	}
	//draw()
	public void draw(Graphics g){
		//drawing map
		for(int i=0;i<map.MAP_WIDTH;i++){
			for(int j=0;j<map.MAP_HEIGHT;j++){
				g.drawImage(spriteSheet1, Math.round(SCALING_FACTOR*map.tile[i][j].TILE_WIDTH*(i)), Math.round(SCALING_FACTOR*map.tile[i][j].TILE_HEIGHT*j), Math.round(SCALING_FACTOR*map.tile[i][j].TILE_WIDTH*(i+1)), Math.round(SCALING_FACTOR*map.tile[i][j].TILE_HEIGHT*(j+1)), map.tile[i][j].xSprite1, map.tile[i][j].ySprite1, map.tile[i][j].xSprite2, map.tile[i][j].ySprite2, null);
			}
		}
		for(int i=0;i<player.length;i++){
			//drawing explosions
			for(int j=0;j<player[i].bombs.size();j++){
				g.drawImage(spriteSheet1, Math.round(SCALING_FACTOR*player[i].bombs.get(j).getXPosition()), Math.round(SCALING_FACTOR*player[i].bombs.get(j).getYPosition()), Math.round(SCALING_FACTOR*(player[i].bombs.get(j).getXPosition()+(player[i].bombs.get(j).getCurrentAnimation().getXSprite2()-player[i].bombs.get(j).getCurrentAnimation().getXSprite1()))),  Math.round(SCALING_FACTOR*(player[i].bombs.get(j).getYPosition()+(player[i].bombs.get(j).getCurrentAnimation().getYSprite2()-player[i].bombs.get(j).getCurrentAnimation().getYSprite1()))), player[i].bombs.get(j).getCurrentAnimation().getXSprite1(), player[i].bombs.get(j).getCurrentAnimation().getYSprite1(), player[i].bombs.get(j).getCurrentAnimation().getXSprite2(), player[i].bombs.get(j).getCurrentAnimation().getYSprite2(), null);
			}
		}
		//redrawing over explosion
		for(int i=0;i<map.MAP_WIDTH;i++){
			for(int j=0;j<map.MAP_HEIGHT;j++){
				if(!map.tile[i][j].getExplosion()){
					g.drawImage(spriteSheet1, Math.round(SCALING_FACTOR*map.tile[i][j].TILE_WIDTH*(i)), Math.round(SCALING_FACTOR*map.tile[i][j].TILE_HEIGHT*j), Math.round(SCALING_FACTOR*map.tile[i][j].TILE_WIDTH*(i+1)), Math.round(SCALING_FACTOR*map.tile[i][j].TILE_HEIGHT*(j+1)), map.tile[i][j].xSprite1, map.tile[i][j].ySprite1, map.tile[i][j].xSprite2, map.tile[i][j].ySprite2, null);
				}
			}
		}
		for(int i=0;i<player.length;i++){
			//drawing bombs
			for(int j=0;j<player[i].bombs.size();j++){
				if(player[i].bombs.get(j).getCurrentTime() - player[i].bombs.get(j).getStartTime() < player[i].bombs.get(j).DETONATE_TIME){
					g.drawImage(spriteSheet1, Math.round(SCALING_FACTOR*player[i].bombs.get(j).getXPosition()), Math.round(SCALING_FACTOR*player[i].bombs.get(j).getYPosition()), Math.round(SCALING_FACTOR*(player[i].bombs.get(j).getXPosition()+(player[i].bombs.get(j).getCurrentAnimation().getXSprite2()-player[i].bombs.get(j).getCurrentAnimation().getXSprite1()))),  Math.round(SCALING_FACTOR*(player[i].bombs.get(j).getYPosition()+(player[i].bombs.get(j).getCurrentAnimation().getYSprite2()-player[i].bombs.get(j).getCurrentAnimation().getYSprite1()))), player[i].bombs.get(j).getCurrentAnimation().getXSprite1(), player[i].bombs.get(j).getCurrentAnimation().getYSprite1(), player[i].bombs.get(j).getCurrentAnimation().getXSprite2(), player[i].bombs.get(j).getCurrentAnimation().getYSprite2(), null);
				}
			}
			//drawing players
			g.drawImage(spriteSheet1, Math.round(SCALING_FACTOR*player[i].getXPosition()), Math.round(SCALING_FACTOR*(player[i].getYPosition())), Math.round(SCALING_FACTOR*(player[i].getXPosition()+15)), Math.round(SCALING_FACTOR*(player[i].getYPosition()+24)), player[i].getCurrentAnimation().getXSprite1(), player[i].getCurrentAnimation().getYSprite1(), player[i].getCurrentAnimation().getXSprite2(), player[i].getCurrentAnimation().getYSprite2(), null);
		}

		g.setColor(Color.white);
		g.fillRect(500, 250, 300, 100);
		g.setColor(Color.black);
		g.drawString(message, 500, 300);
		g.drawString("(" + Math.round(SCALING_FACTOR*player[0].getXPosition()) + ", " + Math.round(SCALING_FACTOR*(player[0].getYPosition())) +") - (" + map.getXTile(SCALING_FACTOR*player[0].getXPosition()) + ", " + map.getYTile(SCALING_FACTOR*(player[0].getYPosition() + TOP_HALF)) +")", 500, 350);
	}

	//key pressed
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch(keyCode){
			//exits game
			case KeyEvent.VK_ESCAPE : stop();
			break;
			//checks for movement keys
			case KeyEvent.VK_RIGHT : player[0].setMovement("right"); moveRight = true;
			break;
			case KeyEvent.VK_LEFT : player[0].setMovement("left"); moveLeft = true;
			break;
			case KeyEvent.VK_UP : player[0].setMovement("up"); moveUp = true;
			break;
			case KeyEvent.VK_DOWN : player[0].setMovement("down"); moveDown = true;
			break;
			//checks for bomb
			case KeyEvent.VK_SPACE :
				if(player[0].bombs.size() < player[0].getAbilities().getMaxBombs()){
					int yPos = TOP_HALF;
					int xPos = 0;
					if(player[0].getCurrentAnimation() == downAnimation){
						yPos = SPRITE_HEIGHT;
					}else if(player[0].getCurrentAnimation() == rightAnimation){
						xPos = SPRITE_WIDTH;
					}
					player[0].bombs.add(new Bomb(map.getXTile(player[0].getXPosition()+xPos)*map.tile[0][0].TILE_WIDTH, map.getYTile(player[0].getYPosition() + yPos)*map.tile[0][0].TILE_HEIGHT, player[0].getAbilities().getFirePower(), System.currentTimeMillis(), System.currentTimeMillis(), bombAnimation));
				}
			break;
		}
		e.consume();
	}

	//key released
	public void keyReleased(KeyEvent e){
		int keyCode = e.getKeyCode();
		//stops movement upon release
		switch(keyCode){
			case KeyEvent.VK_RIGHT : player[0].setXVelocity(0f); moveRight = false;
			break;
			case KeyEvent.VK_LEFT : player[0].setXVelocity(0f); moveLeft = false;
			break;
			case KeyEvent.VK_UP : player[0].setYVelocity(0f); moveUp = false;
			break;
			case KeyEvent.VK_DOWN : player[0].setYVelocity(0f); moveDown = false;
		}
		if(!moveRight && !moveLeft && !moveUp && !moveDown){
			player[0].setMovement("still");
		}
		e.consume(); //eats up the event, does not wait for more key combinations (ie. alt-f4) 
	}

	//key Typed
	public void keyTyped(KeyEvent e){
		e.consume();
	}

	//MOUSE MOTION INTERFACE//
	//mouse dragged (with clicking)
	public void mouseDragged(MouseEvent e){
	}
	//mouse moved
	public void mouseMoved(MouseEvent e){
		//message = "" + MouseInfo.getPointerInfo().getLocation();
	}
}