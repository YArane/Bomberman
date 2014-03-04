import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

public class ScreenManager{
	private GraphicsDevice videoCard; //allows you to access the video card

	//gives videoCard access to the monitor
	public ScreenManager(){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		videoCard = env.getDefaultScreenDevice();
	}

	//make frame full screen
	public void setFullScreen(DisplayMode displayMode){
		
		JFrame frame = new JFrame();
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);

		videoCard.setFullScreenWindow(frame);

		if(displayMode != null && videoCard.isDisplayChangeSupported()){
			try{
				videoCard.setDisplayMode(displayMode);
			}catch(Exception e){}
		}

		//buffered strategy copy frame to a picture, and then to the screen (not directly - reduces glitches/flickering)
		frame.createBufferStrategy(2); // 2 different buffers outside the monitor
	}

	//setting graphics object
	public Graphics2D getGraphics(){
		Window window = videoCard.getFullScreenWindow();
		if(window != null){
			BufferStrategy buffer = window.getBufferStrategy();
			return (Graphics2D)buffer.getDrawGraphics();
		}else{
			return null;
		}
	}

	//updates display
	public void update(){
		Window window = videoCard.getFullScreenWindow();
		if(window != null){
			BufferStrategy buffer = window.getBufferStrategy();
			if(buffer.contentsLost() == false){ //if there is nothing to look at, thats when you get flickering
				buffer.show(); //displays whatever is on the buffer onto the screen
			}
		}
	}

	//get full screen window
	public Window getFullScreenWindow(){
		return videoCard.getFullScreenWindow();
	}
	//get width
	public int getWidth(){
		Window window = videoCard.getFullScreenWindow();
		if(window != null){
			return	window.getWidth(); 
		}else{
			return 0;
		}
	}
	//get height
	public int getHeight(){
		Window window = videoCard.getFullScreenWindow();
		if(window != null){
			return window.getHeight();
		}else{
			return 0;
		}
	}

	//restore screen (exit full screen)
	public void restoreScreen(){
		Window window = videoCard.getFullScreenWindow();
		if(window != null){
			window.dispose();
		}
		videoCard.setFullScreenWindow(null);
	}

	//creating an image compatible with the monitor
	public BufferedImage createCompatibleImage(int width, int height, int transparancy){
		Window window = videoCard.getFullScreenWindow();
		if(window != null){
			GraphicsConfiguration gc = window.getGraphicsConfiguration(); // gets the characteristics of the current monitor
			return gc.createCompatibleImage(width, height, transparancy);
		}
		return null;
	}

	//getting all comapatible display modes (allows different computers to run program)
	public DisplayMode[] getCompatibleDisplayModes(){
		return videoCard.getDisplayModes();
	}

	//compares dsplay modes passed into video card's display mode, and checks if they match
	public DisplayMode findFirstCompatibleMode(DisplayMode[] modes){
		DisplayMode[] goodModes = videoCard.getDisplayModes();
		for(int i=0;i<modes.length;i++){
			for(int j=0;j<goodModes.length;j++){
				if(displayModesMatch(modes[i], goodModes[j]) == true){
					return goodModes[j];
				}
			}
		}
		return null;
	}

	//get current display mode
	public DisplayMode getCurrentDisplayMode(){
		return videoCard.getDisplayMode();
	}

	//compares two display modes
	public boolean displayModesMatch(DisplayMode displayMode1, DisplayMode displayMode2){
		//comparing the resolution
		if(displayMode1.getWidth() != displayMode2.getWidth() || displayMode1.getHeight() != displayMode2.getHeight()){
			return false;
		}
		//comparing the bit-depth
		if(displayMode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && displayMode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && displayMode1.getBitDepth() != displayMode2.getBitDepth()){
			return false;
		}
		//comparing refresh rate
		if(displayMode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && displayMode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && displayMode1.getRefreshRate() != displayMode2.getRefreshRate()){
			return false;
		}

		return true;
	}
}