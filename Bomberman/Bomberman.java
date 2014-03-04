import java.awt.*;
import javax.swing.*;

public class Bomberman{
	private static final DisplayMode[] typicalModes = { //hopefully your monitor has one of these common display modes
		new DisplayMode(800, 600, 32, 0),
		new DisplayMode(800, 600, 24, 0),
		new DisplayMode(800, 600, 16, 0),
		new DisplayMode(640, 480, 32, 0),
		new DisplayMode(640, 480, 24, 0),
		new DisplayMode(640, 480, 16, 0),
	};
	//main()
	public static void main(String[] args){
		Bomberman game = new Bomberman();
		game.run();
	}
	//run()
	public void run(){
		ScreenManager screen = new ScreenManager();
		Map map = new Map("map1.txt");
		GameEngine engine = new GameEngine();
		engine.initiate(map);
		try{
			DisplayMode displayMode = screen.findFirstCompatibleMode(typicalModes);
			screen.setFullScreen(displayMode); //passing the most ideal display mode for that monitor
			Window window = screen.getFullScreenWindow();
			window.setFocusTraversalKeysEnabled(false); // gets rid of special keys (ie. tab, control, etc..)
			engine.gameLoop(screen);
		}finally{
			screen.restoreScreen();
		}
	}
} 