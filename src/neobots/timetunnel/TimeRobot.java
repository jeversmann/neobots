package neobots.timetunnel;

import neobots.common.GameRobot;
import mastermind.*;
import java.awt.*;

public class TimeRobot extends GameRobot {

	public static void main(String[] args) {
		TimeRobot time = new TimeRobot(new Rectangle(1000,800));
		time.play();
	}

	public static final int[] button = new int[]
		{189, 212, 235, 260};
	public static final Point[] scrLocs = new Point[]
		{new Point(87, 27), //Start button
		 new Point(517, 235), //Submit button
		 new Point(245, 255), //Pixel to check for rotation
		//Result circles
		 new Point(393, 306),
		 new Point(388, 314),
		 new Point(384, 322),
		 new Point(380, 329)};

	public TimeRobot(Rectangle screen) {
		super(screen);
	}

	public void play() {
		if(!align("build/timeTunnel.png")) {
				return;
		}
		click(scrLocs[0], 100);
		wait(1000);
		for(int level = 0; level < 12; level++) {
			System.out.println("Level: "+level);
			storeSave(""+level);
			char[] set;
			if(level < 5) {
				set = new char[]{'0','1','2','3'};
			} else if(level < 9) {
				set = new char[]{'0','1','2','3','4'};
			} else {
				set = new char[]{'0','1','2','3','4','5'};
			}
			Evaluator player = new Evaluator(
				Combination.makeRand(set,4));
			for(int tries = 0; tries < 8; tries++) {
				Combination guess = player.guess();
				System.out.println("\t" + guess);
				char[] guessCs = guess.getCombination();
				int[] scores = new int[2];
				for(int i = 0; i < 4; i++) {
					int clicks = ((int) guessCs[i]) - 48;
					//Buttons share a y location
					move(button[i], 224);
					while(clicks --> 0) {
						click();
					}
				}
				click(scrLocs[1]);
				wait(500);
				Color c = getColorRT(scrLocs[2]);
				while(c.getGreen() != 184) {
					wait(100);
					c = getColorRT(scrLocs[2]);
				}
				wait(500);
				save();
				c = getColorSave(scrLocs[3]);
				if(c.getGreen() == 153) {
					if(c.getRed() > 153) {
						scores[1]++;
					} else {
						scores[0]++;
					}
				} else if(c.getGreen() != 255) {
					break;
				}
				c = getColorSave(scrLocs[4]);
				if(c.getGreen() == 153) {
					if(c.getRed() > 153) {
						scores[1]++;
					} else {
						scores[0]++;
					}
				}
				c = getColorSave(scrLocs[5]);
				if(c.getGreen() == 153) {
					if(c.getRed() > 153) {
						scores[1]++;
					} else {
						scores[0]++;
					}
				}
				c = getColorSave(scrLocs[6]);
				if(c.getGreen() == 153) {
					if(c.getRed() > 153) {
						scores[1]++;
					} else {
						scores[0]++;
					}
				}
				System.out.printf("\t%d correct, %d almost.\n",
					scores[0], scores[1]);
				player.getResult(guess,scores);
			}
		}
	}
}
