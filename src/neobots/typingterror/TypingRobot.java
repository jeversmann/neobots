import java.awt.*;

public class TypingRobot extends GameRobot {

	public static void main(String[] args) {
		TypingRobot type = new TypingRobot(new Rectangle(1000,800));
		type.play();
	}

	public static final Point[] scrLocs = new Point[]
		{new Point(80,160), //Start button
		 new Point(90, 60), //Normal button
		};

	public TypingRobot(Rectangle screen) {
		super(screen);
	}

	public void play() {
		if(!align("build/typingTerror.png")) {
			return;
		}
		click(scrLocs[0], 100);
		wait(1000);
		click(scrLocs[1], 100);
		wait(2000);
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		while(true) {
			for(char a : alpha.toCharArray()) {
				press(a);
			}
		}
	}
}
