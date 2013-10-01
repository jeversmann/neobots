package mastermind;

import java.util.Scanner;

public class Mastermind {

	public static void main(String[] args) {
		Combination goal;
		if(args.length > 1) {
			try {
				int len = Integer.parseInt(args[1]);
				goal = Combination.makeRand(len);
			} catch(Exception e1) {
				try {
					int range;
					if(args[1].substring(0, 3).equals("int")) {
						range = Integer.parseInt(args[1].substring(3));
						char[] set = new char[range];
						for(int i = 48; i < 58 && i < 48 + range; i++) {
							set[i-48] = (char) i;
						}
						if(args.length > 2) {
							int len = Integer.parseInt(args[2]);
							goal = Combination.makeRand(set, len);
						} else {
							goal = Combination.makeRand(set);
						}
					} else if(args[1].substring(0,4).equals("char")) {
						range = Integer.parseInt(args[1].substring(4));
						char[] set = new char[range];
						for(int i = 97; i < 123 && i < 97 + range; i++) {
							set[i-97] = (char) i;
						}
						if(args.length > 2) {
							int len = Integer.parseInt(args[2]);
							goal = Combination.makeRand(set, len);
						} else {
							goal = Combination.makeRand(set);
						}
					} else {
						throw new Exception();
					}
				} catch(Exception e2) {
					System.out.println("Invalid arguments.");
					return;
				}
			}
		} else {
			goal = Combination.makeRand();
		}
		if(args.length	> 0) {
			String mode = args[0].toLowerCase();
			if(mode.equals("-n")) {
				normalPlay(goal);
			} else if(mode.equals("-a")) {
				adversaryPlay(goal);
			} else if(mode.equals("-s")) {
				selfPlay(goal);
			} else if(mode.equals("-q")) {
				quietPlay(goal);
			} else if(mode.equals("-b")) {
				battlePlay(goal);
			} else if(mode.equals("-e")) {
				evaluatorPlay(goal);
			} else {
				System.out.println("Invalid mode.");
				return;
			}
		} else {
			normalPlay(goal);
		}
	}

	public static void selfPlay(Combination goal) {
		Evaluator player = new Evaluator(goal.getSet(), goal.getSize());
		System.out.print("\033[34mI'm going to guess the code!\n");
		int tries = goal.getTries();
		Combination guess;
		for(int i = 0; i < tries; i++) {
			try {
				guess = player.guess();
				int[] score = new int[2];
				System.out.println("\033[34mI decided to guess:\033[m");
				System.out.println(guess);
				score = goal.judge(guess.clone());
				System.out.printf("\033[34mand my guess had\033[m %d \033[34min place " +
						"and had\033[m %d \033[34mout\n" +
						"of place; I have %d guess" + (i == 8 ? "": "es") +
						" remaining.\n\033[m", score[0], score[1], tries - (i + 1));
				if(score[0] == goal.getSize()) {
					System.out.println("\033[33mI win!\033[m");
					return;
				}
				player.getResult(guess, score);
			} catch (Exception e) {
				System.err.println("\033[31mMy input was invalid?\033[m");
			}
		}
		System.out.println("\033[31mI lost!\nThe code was:\033[m " + goal);
		return;
	}

	public static void normalPlay(Combination goal) {
		Scanner in = new Scanner(System.in);
		String inst = "\033[34mGuess the code! Enter your guess like this:\033[m\n";
		char[] guess = goal.getSet();
		for(int i = 0; i < goal.getSize(); i++) {
			inst += guess[0] + ",";
		}
		System.out.println(inst.substring(0,inst.length()-1));
		int tries = goal.getTries();
		for(int i = 0; i < tries; i++) {
			try {
				String[] rawIn = in.nextLine().split(",");
				guess = new char[rawIn.length];
				for(int j = 0; j < rawIn.length; j++) {
					guess[j] = rawIn[j].toLowerCase().charAt(0);
				}
				int[] score = new int[2];
				score = goal.judge(goal.make(guess));
				System.out.printf("\033[34mYour guess had\033[m %d \033[34min place " +
						"and had\033[m %d \033[34mout\n" +
						"of place; you have %d guess" + (i == 8 ? "": "es") +
						" remaining.\n\033[m", score[0], score[1], tries - (i + 1));
				if(score[0] == goal.getSize()) {
					System.out.println("You win!");
					return;
				}
			} catch (Exception e) {
				System.err.println("\033[31mYour input was invalid.\033[m");
				i--;
			}
		}
		System.out.println("\033[31mYou lose!\nThe code was:\033[m " + goal);
		return;
	}

	public static void battlePlay(Combination seed) {
		Adversary goal = new Adversary(seed);
		System.out.print("\033[34mWelcome to the Mastermind showdown.\nLet the battle" +
				" of wits begin!\033[m\n");
		int tries = goal.getSeed().getTries();
		while(true) {
			Evaluator player = new Evaluator(goal.getSeed());
			boolean winner = false;
			for(int i = 0; i < tries; i++) {
				try {
					Combination guess = player.guess();
					int[] score = new int[2];
					score = goal.worstScore(guess.clone());
					if(score[0] == goal.getSeed().getSize()) {
						System.out.printf("\033[33mThe Evaluator wins after %d turns!\n\033[m", i + 1);
						winner = true;
						break;
					}
					player.getResult(guess, score);
				} catch (Exception e) {
					System.err.println("\033[31mYour input was invalid.\033[m");
				}
			}
			if(!winner) {
				System.out.println("\033[31mThe Adversary wins!\033[m");
			}
		}
	}

	public static void adversaryPlay(Combination seed) {
		Adversary goal = new Adversary(seed);
		Scanner in = new Scanner(System.in);
		System.out.print("\033[34mWelcome to Adversary Mastermind.\nYou can try " +
				"to guess the code, but\nI'm going to make you lose.\033[m\n");
		int tries = goal.getSeed().getTries();
		char[] guess;
		for(int i = 0; i < tries; i++) {
			try {
				String[] rawIn = in.nextLine().split(",");
				guess = new char[rawIn.length];
				for(int j = 0; j < rawIn.length; j++) {
					guess[j] = rawIn[j].toLowerCase().charAt(0);
				}
				int[] score = new int[2];
				score = goal.worstScore(goal.getSeed().make(guess));
				System.out.printf("\033[34mYour guess had\033[m %d \033[34min place " +
						"and had\033[m %d \033[34mout\n" +
						"of place; you have %d guess" + (i == 8 ? "": "es") +
						" remaining.\n\033[m", score[0], score[1], tries - (i + 1));
				if(score[0] == goal.getSeed().getSize()) {
					System.out.println("You win? That's impossible!");
					return;
				}
			} catch (Exception e) {
				System.err.println("\033[31mYour input was invalid.\033[m");
			}
		}
		System.out.println("\033[31mYou lose! I'm not even going to tell you" +
				"\nwhat the code was, because you were that bad.\033[m ");
		return;
	}

	public static void quietPlay(Combination seed) {
		boolean winner = true;
		while(winner) {
			winner = false;
			Combination goal = Combination.makeRand(seed.getSet(),seed.getSize());
			Evaluator player = new Evaluator(goal.getSet(), goal.getSize());
			int tries = goal.getTries();
			Combination guess;
			for(int i = 0; i < tries; i++) {
				System.out.print(i);
				try {
					guess = player.guess();
					int[] score = new int[2];
					score = goal.judge(guess.clone());
					if(score[0] == goal.getSize()) {
						System.out.println("\t\033[33mI win!\033[m");
						winner = true;
						break;
					}
					player.getResult(guess, score);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!winner) {
				System.out.println("\033[31mI lost!\nThe code was:\033[m " + goal);
			}
		}
	}

	public static void evaluatorPlay(Combination goal) {
		Scanner in = new Scanner(System.in);
		Evaluator player = new Evaluator(goal);
		System.out.print("\033[34mI'm going to guess your code!\n");
		int tries = goal.getTries();
		Combination guess;
		for(int i = 0; i < tries; i++) {
			try {
				guess = player.guess();
				int[] score = new int[2];
				System.out.println("\033[34mI decided to guess:\033[m");
				System.out.println(guess);
				System.out.println("\033[34mTell me what the score is.\033[m");
				String[] rawIn = in.nextLine().split(",");
				score = new int[rawIn.length];
				for(int j = 0; j < 2; j++) {
					score[j] = Integer.parseInt(rawIn[j]);
				}
				System.out.printf("\033[34mYou said my guess had\033[m %d \033[34min place " +
						"and had\033[m %d \033[34mout\n" +
						"of place; I have %d guess" + (i == 8 ? "": "es") +
						" remaining.\n\033[m", score[0], score[1], tries - (i + 1));
				if(score[0] == goal.getSize()) {
					System.out.println("\033[33mI win!\033[m");
					return;
				}
				player.getResult(guess, score);
			} catch (Exception e) {
				System.err.println("\033[31mMy input was invalid?\033[m");
				i--;
			}
		}
		System.out.println("\033[31mI'm out of guesses.\033[m");
		return;
	}
}
