package neobots.nationalbank;

import neobots.common.GameRobot;
import java.awt.*;
import java.util.*;

public class BankRobot extends GameRobot {

	private static final int[] doorLoc = new int[]
		{51, 137, 223, 310, 395, 483};

	public static void main(String[] args) {
		new BankRobot().play();
	}

	public BankRobot() {}

	public void play() {
		Color[] doors = new Color[6];
		boolean[] open = new boolean[6];
		if(!align("build/nationalbank.png"))
			return;
        press(' ');
        wait(100);
		Color wall = getColorRT(267, 50);
		for(int i = 0; i < 6; i++) {
			doors[i] = getColorRT(doorLoc[i], 150);
			move(doorLoc[i], 150);
		}
		while(true) {
			/*temp = r.getPixelColor(TLCorner.x + 345, TLCorner.y + 25);
			r.mouseMove(TLCorner.x + 345, TLCorner.y + 25);
			if(temp.getRed() < 100) {
				r.keyPress(KeyEvent.VK_SPACE);
				System.out.println("Made some gold!");
				r.keyRelease(KeyEvent.VK_SPACE);
			}*/
			Color temp = getColorRT(267, 50);
			move(267, 140);
			if(wall.getRGB() != temp.getRGB()) {
				break;
			}
			for(int i = 0; i < 6; i++) {
				temp = getColorRT(doorLoc[i], 150);
				//move(doorLoc[i], 150);
			 	if(doors[i].getRGB() != temp.getRGB()) {
					int[] colorSum = new int[3];
					if(open[i]) {
						continue;
					} else {
						open[i] = true;
					}
					for(int k = -10; k <= 10; k += 20) {
						for(int j = 140; j < 200; j += 20) {
							temp = getColorRT(doorLoc[i] + k, j);
							move(doorLoc[i] + k, j);
							colorSum[0] += temp.getRed();
							colorSum[1] += temp.getGreen();
							colorSum[2] += temp.getBlue();
						}
					}
					char key = ' ';
					switch (i) {
						case 0: key = 'S'; break;
						case 1: key = 'D'; break;
						case 2: key = 'F'; break;
						case 3: key = 'J'; break;
						case 4: key = 'K'; break;
						case 5: key = 'L'; break;
					}
					if(colorSum[1] > 150 && colorSum[1] < 600) {
							if(colorSum[0] < 50) {
								System.out.println("Fuzzy blue guy is at door "+i+".");
							} else if(colorSum[0] < 170) {
								System.out.println("Blue cat thing at door "+i+".");
							} else if(colorSum[0] < 300) {
								if(colorSum[2] > 300) {
									System.out.println("Blue papers guy is at door "+i+".");
								} else if(colorSum[2] > 200) {
									System.out.println("Frog dude at door "+i+".");
								} else {
									press(key);
									System.out.println("Stopped green bandit at door "+i+".");
							}
						} else if(colorSum[0] < 400) {
							press(key);
							System.out.println("Stopped evil twins at door "+i+".");
						} else if(colorSum[0] < 600) {
							System.out.println("Penguin dude is at door "+i+".");
						} else {
							press(key);
							System.out.println("Stopped tall lizard at door "+i+".");
						}
					} else if(colorSum[1] < 200) {
						if(colorSum[2] > 5) {
							press(key);
							System.out.println("Stopped red dragon at door "+i+".");
						} else {
							System.out.println("Blue dragon at door "+i+".");
						}
					} else {
						System.out.println(""+colorSum[0]+","+colorSum[1]+","+colorSum[2]+" at door "+i+".");
					}
				} else {
					open[i] = false;
				}
			}
		}
	}
}
