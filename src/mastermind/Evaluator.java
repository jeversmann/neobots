package mastermind;

import java.io.*;
import java.util.*;


public class Evaluator {

	//Fields	
	protected List<char[]> remainingCombos;
	protected char[] charSet;
	protected int length;
	protected int scoreCount;
	
	//Constructors
	public Evaluator(Combination c) {
		this(c.getSet(), c.getSize());
	}
	
	public Evaluator(char[] set, int len) {
		charSet = set;
		length = len;
		remainingCombos = new ArrayList<char[]>();
		scoreCount = 0;
		for(int i = 0; i < length + 1; i++) {
			for(int j = 0; j < length - i + 1; j++){
				scoreCount++;
			}
		}
	}

	public Combination guess() {
		if(remainingCombos.size() == 0) {
		//This happens on the first guess
			return Combination.makeRand(charSet, length);
		} else if(remainingCombos.size() < 3) {
			try {
				return new Combination(
					remainingCombos.get(0), charSet);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			ComboMaker builder = new ComboMaker(charSet, length);
			Guess best = new Guess(new char[1], Integer.MAX_VALUE);
			//Iterate across possible guesses
			for(char[] combo: builder) {
				try {
					Guess cur = new Guess(combo, 0);
					Combination test = new Combination(
						combo, charSet);
					int[] scores = new int[scoreCount];
					//Evaluate each combo based on the guess
					for(char[] answer: remainingCombos) {
						Combination judge = new Combination(
							answer, charSet);
						int[] score = judge.judge(test.clone());
						int index = 0;
						while(score[0] --> 0) {
							index += 1 + length - score[0];
						}
						scores[score[1] + index] += 1; 
					}
					for(int i = 0; i < scores.length; i++) {
						if(cur.worst < scores[i]) {
							cur.worst = scores[i];
						}
					}
					if(best.worst > cur.worst) {
						best = cur;
					}
				} catch (Exception e) {
					System.out.print(best.combo);
					e.printStackTrace();
				}
			}
			try {
				return new Combination(best.combo, charSet);
			} catch (Exception e) {
				e.printStackTrace();
				return Combination.makeRand(charSet, length);
			}
		}
	}
	
	public List<char[]> getResult(Combination guess, int[] score) {
		if(remainingCombos.size() == 0) {
			ComboMaker builder = new ComboMaker(charSet, length);
			for(char[] combo: builder) {
				Combination test;
				int[] testScore = new int[2];
				try {
					test = new Combination(combo, charSet);
					testScore = test.judge(guess.clone());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(testScore[0] == score[0]
					&& testScore[1] == score[1]) {
					remainingCombos.add(combo);
				}
			}
		} else {
			List<char[]> newCombos = new ArrayList<char[]>();
			for(char[] combo: remainingCombos) {
				Combination test;
				int[] testScore = new int[2];
				try {
					test = new Combination(combo, charSet);
					testScore = test.judge(guess.clone());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(testScore[0] == score[0] && 
					testScore[1] == score[1]) {
					newCombos.add(combo);
				}
			}
			remainingCombos = newCombos;
		}
		return remainingCombos;
	}

	public int count() {
		return remainingCombos.size();
	}

	private class Guess {
		public char[] combo;
		public int worst;
		public Guess(char[] c, int w) {
			worst = w;
			combo = c;
		}
	}
}
