package neobots.roodoku;

import neobots.common.GameRobot;
import sudoku.*;
import java.awt.*;

public class Roobot extends GameRobot {

    public static void main(String[] args) {
        Roobot doku = new Roobot(new Rectangle(1000,800));
        doku.play();
    }

    public Roobot(Rectangle screen) {
        super(screen);
    }

    public void play() {
        if(!align("build/roodoku.png")) {
            return;
        }
        System.out.println("Only pick hard");
    }
}
