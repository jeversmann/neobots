package neobots.roodoku;

import neobots.common.GameRobot;
import sudoku.*;
import java.awt.*;

public class Roobot extends GameRobot {

    public static void main(String[] args) {
        Roobot doku = new Roobot(new Rectangle(1000,800));
        doku.play();
    }

    private Board myBoard;

    public Roobot(Rectangle screen) {
        super(screen);
    }

    public void play() {
        if(!align("build/roodoku.png")) {
            return;
        }
        System.out.println("Only pick hard");
        click(200,320);
        move(0,0);
        wait(2000);
        readBoard();
        myBoard.markAll();
        myBoard.printStatus();
        System.out.println();
        Board.Move m;
        while(true) {
            m = myBoard.getMove();
            for(int x = 0; x < 9 & m.VAL == 0; x++) {
                for(int y = 0; y < 9 & m.VAL == 0; y++) {
                    m = myBoard.checkPossible(x,y);
                }
            }
            if(m.VAL == 0) {
                break;
            } else {
                myBoard.set(m);
                clickCell(m);
            }
        }
        myBoard.printStatus();
    }

    private void readBoard() {
        String board = "";
        save();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                int val = readCell(j,i);
                System.out.println(val);
                board += val;
            }
        }
        myBoard = new Board(board);
    }

    /**
     * Gets the value at a cell, returns 0 if blank.
     */
    private int readCell(int x, int y) {
        int[]  colorSum = new int[3];
        Color temp;
        Point TL = getCellLoc(x,y);
        for(int i = 1; i < 30; i++) {
            for(int j = 1; j < 30; j++) {
                temp = getColorSave(TL.x + i, TL.y + j);
                colorSum[0] += temp.getRed();
                colorSum[1] += temp.getGreen();
                colorSum[2] += temp.getBlue();
            }
        }
        System.out.printf("%d,%d: %d %d %d ", x, y,
            colorSum[0], colorSum[1], colorSum[2]);
        if(match(colorSum, 162912, 207301, 213614)) {
            return 0;
        } else if(match(colorSum, 78519, 182006, 196352)) {
            return 1;
        } else if(match(colorSum, 76323, 177482, 191506)) {
            return 2;
        } else if(match(colorSum, 75978, 177748, 191855)) {
            return 3;
        } else if(match(colorSum, 75870, 177903, 192044)) {
            return 4;
        } else if(match(colorSum, 75890, 177735, 191849)) {
            return 5;
        } else if(match(colorSum, 73416, 174493, 188499)) {
            return 6;
        } else if(match(colorSum, 78350, 181046, 195281)) {
            return 7;
        } else if(match(colorSum, 72252, 170315, 183903)) {
            return 8;
        } else if(match(colorSum, 75092, 174585, 188374)) {
            return 9;
        } else {
            return 0;
        }
    }

    private void clickCell(Board.Move move) {
        Point TL = getCellLoc(move.X, move.Y);
        int x = 0, y = 0;
        switch(move.VAL) {
            case 1: x = 4; y = 4;
                break;
            case 2: x = 15; y = 4;
                break;
            case 3: x = 26; y = 4;
                break;
            case 4: x = 4; y = 15;
                break;
            case 5: x = 15; y = 15;
                break;
            case 6: x = 26; y = 15;
                break;
            case 7: x = 4; y = 26;
                break;
            case 8: x = 15; y = 26;
                break;
            case 9: x = 26; y = 26;
                break;
        }
        click(TL.x + x, TL.y + y);
    }

    private Point getCellLoc(int x, int y) {
        return new Point(41 + 36*x, 89 + 36*y);
    }

    private boolean match(int rgb[], int r, int g, int b) {
        int range = 500;
        return( rgb[0] < r + range && rgb[0] > r - range &&
                rgb[1] < g + range && rgb[1] > g - range &&
                rgb[2] < b + range && rgb[2] > b - range );
    }

}
