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
		if(!align("build/nationalbank.png"))
			return;
        press(' ');
        wait(100);
		Color wall = getColorRT(267, 50);
		for(int i = 0; i < 6; i++) {
			doors[i] = getColorRT(doorLoc[i], 150);
			move(doorLoc[i], 150);
		}
        int[] colorSum;
        int[] livesColor = new int[3];
		while(true) {
			Color temp = getColorRT(267, 50);
			move(267, 140);
			if(wall.getRGB() != temp.getRGB()) {
				break;
			}
			for(int i = 0; i < 6; i++) {
				temp = getColorRT(doorLoc[i], 150);
			 	if(doors[i].getRGB() != temp.getRGB()) {
					colorSum = new int[3];
                    save();
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
                    if( match(colorSum, 80000, 40000, 35000) ||
                        match(colorSum, 51000, 38000, 24000) ||
                        match(colorSum, 58000, 73000,  7000) ||
                        match(colorSum,163000, 90000, 70000) ) {
                        press(key);
                    }
                }
			}
            colorSum = new int[3];
            for(int i = 0; i <= 10; i++) {
                for(int j = 0; j <= 20; j++) {
                    temp = getColorSave(340 + i, 12 + j);
					colorSum[0] += temp.getRed();
					colorSum[1] += temp.getGreen();
					colorSum[2] += temp.getBlue();
                }
            }
            if(!( colorSum[0] == 41747 && colorSum[1] == 41747
                && colorSum[2] == 41740)) {
                press(' ');
            }
            colorSum = new int[3];
            for(int i = 0; i <= 10; i++) {
                for(int j = 0; j <= 20; j++) {
                    temp = getColorSave(250 + i, 12 + j);
					colorSum[0] += temp.getRed();
					colorSum[1] += temp.getGreen();
					colorSum[2] += temp.getBlue();
                }
            }
            if(!( colorSum[0] == livesColor[0] &&
                  colorSum[1] == livesColor[1] &&
                  colorSum[2] == livesColor[2] )) {
                storeSave("" + colorSum[0]);
                livesColor = colorSum;
            }
            
            wait(100);
		}
	}

    private boolean match(int rgb[], int r, int g, int b) {
        int range = 20000;
        return( rgb[0] < r + range && rgb[0] > r - range &&
                rgb[1] < g + range && rgb[1] > g - range &&
                rgb[2] < b + range && rgb[2] > b - range );
    }
}
