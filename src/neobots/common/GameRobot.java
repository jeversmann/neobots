package neobots.common;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class GameRobot {

	//Fields
	private Point TL;
	private Robot R;
	private BufferedImage scrSave;
	private Rectangle scrSize;

	//Constants and default values
	private static final int RMB = InputEvent.BUTTON1_MASK;
	private static final int CLICK_DUR = 15;
	private static final int DEF_WIDTH = 600;
	private static final int DEF_HEIGHT = 400;

	//Constructors
	public GameRobot() {
		this(new Rectangle(DEF_WIDTH, DEF_HEIGHT));
	}

	public GameRobot(Rectangle screen) {
		TL = new Point();
		scrSave = new BufferedImage(screen.width, screen.height,
			BufferedImage.TYPE_INT_RGB);
		scrSize = screen;
		try {
			R = new Robot();
		} catch (Exception e) {
		}
	}

	/**
 	 * Attempts to locate an exemplar image on the screen
	 * and aligns the robot's (x,y) locations with the
 	 * image if it is found.
	 */
	public boolean align(BufferedImage game) {
		Color goal = new Color(game.getRGB(0,0)), found;
		save();
		boolean set = false;
		//Searches in the top left region of the screen
		for(int i = 15; i < 200 & !set; i++) {
			for(int j = 15; j < 200 & !set; j++) {
				found = getColorSave(i, j);
				R.mouseMove(i,j);
				//This logic is currently lame
				if(found.getRGB() == goal.getRGB()) {
					TL = new Point(i,j);
					set = true;
				}
			}
		}
		storeSave("test");
		return set;
	}

	public boolean align(String loc) {
		try {
			return this.align(ImageIO.read(new File(loc)));
		} catch (Exception e) {
            System.err.println(e.toString());
			return false;
		}
	}

	/**
	 * Save the state of the screen for later processing.
	 */
	public void save() {
		scrSave = R.createScreenCapture(scrSize);
	}

	/**
	 * Outputs the most recent screenshot to a png file.
	 */
	public void storeSave(String loc) {
		try {
			ImageIO.write(scrSave, "png", new File(loc + ".png"));
		} catch (Exception e) {
		}
	}

	/**
	 * Reads pixel information from the state of the screen
	 * which was most recently saved.
	 */
	public Color getColorSave(int x, int y) {
		return new Color(scrSave.getRGB(TL.x + x, TL.y + y));
	}

	public Color getColorSave(Point p) {
		return new Color(scrSave.getRGB(TL.x + p.x, TL.y + p.y));
	}

	/**
	 * Reads pixel information directly from the screen without
	 * checking the saved screen.
	 */
	public Color getColorRT(int x, int y) {
		return R.getPixelColor(TL.x + x, TL.y + y);
	}

	public Color getColorRT(Point p) {
		return R.getPixelColor(TL.x + p.x, TL.y + p.y);
	}

	/**
	 * Moves the mouse cursor to an x and y location
	 * relative to the stored top left corner.
	 */
	public void move(int x, int y) {
		R.mouseMove(TL.x + x, TL.y + y);
	}

	public void move(Point p) {
		R.mouseMove(TL.x + p.x, TL.y + p.y);
	}

	/**
	 * Methods for clicking.
	 * Can be given (x,y) locations to move to before clicking
	 * or a non-default duration to hold the click for.
	 */
	public void click(int x, int y, int dur) {
		R.mouseMove(TL.x + x, TL.y + y);
		R.mousePress(RMB);
		wait(dur);
		R.mouseRelease(RMB);
	}

	public void click(Point p, int dur) {
		click(p.x, p.y, dur);
	}

	public void click(int x, int y) {
		click(x, y, CLICK_DUR);
	}

	public void click(Point p) {
		click(p.x, p.y, CLICK_DUR);
	}

	public void click(int dur) {
		R.mousePress(RMB);
		wait(dur);
		R.mouseRelease(RMB);
	}

	public void click() {
		click(CLICK_DUR);
	}

	/**
	 * Methods for generating key presses.
	 * parse() acts as a converter from characters to KeyEvents
	 *  - Uppercase letters correspond to their keyboard key
	 *  - Special keys are designated with lower case letters
	 */
	private int parse(char key) { 
		switch (key) { 
		case ' ':
			return KeyEvent.VK_SPACE;
		//Arrow keys
		case 'u':
			return KeyEvent.VK_UP;
		case 'd':
			return KeyEvent.VK_DOWN;
		case 'l':
			return KeyEvent.VK_LEFT;
		case 'r':
			return KeyEvent.VK_RIGHT;
		//Handles actual letter keys
		default:
			return (int) key;
		}
	}

	public void press(char key) {
		int keyCode = parse(key);
		R.keyPress(keyCode);
		wait(CLICK_DUR);
		R.keyRelease(keyCode);
	}

	public void hold(char key) {
		R.keyPress(parse(key));
	}

	public void release(char key) {
		R.keyPress(parse(key));
	}

	/**
	 * Wait function which stops io for time dur in miliseconds.
	 */
	public void wait(int dur) {
		for(long time = System.currentTimeMillis();
			System.currentTimeMillis() - time < dur;);
	}
}
