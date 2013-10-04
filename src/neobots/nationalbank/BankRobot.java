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
        int character = 0;
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
                        save();
					}
					for(int k = -10; k <= 10; k++) {
					    move(doorLoc[i] + k, 100);
						for(int j = 140; j < 200; j++) {
							temp = getColorSave(doorLoc[i] + k, j);
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
                    System.out.printf("C:%d R:%d G:%d B:%d\n",
                        character, colorSum[0], colorSum[1], colorSum[2]);
                    storeSave("" +character++);
				
				} else {
					open[i] = false;
				}
			}
		}
	}
}
