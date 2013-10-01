package mastermind;

import java.util.ArrayList;
import java.util.List;

public class Adversary {

	protected List<char[]> remainingCombos;
	protected char[] charSet;
	protected int length;
	protected int scoreCount;
	protected int[][] indexedScores;
	protected Combination seed;
	
	public Adversary(Combination s) {
		charSet = s.getSet();
		length = s.getSize();
		seed = s;
		remainingCombos = new ArrayList<char[]>();
		scoreCount = 0;
		for(int i = 0; i < length + 1; i++) {
			for(int j = 0; j < length - i + 1; j++){
				scoreCount++;
			}
		}
		indexedScores = new int[scoreCount][2];
		for(int i = 0; i < length + 1; i++) {
			for(int j = 0, k = i, index = 0; j < length - i + 1; j++){
				while(k --> 0) {index += 1 + length - k;}
				indexedScores[index + j][0] = i;
				indexedScores[index + j][1] = j;
			}
		}
	}
	
	public Combination getSeed() {
		return seed;
	}
	
	public int[] worstScore(Combination guess) {
		int[] scores = new int[scoreCount];
		if(remainingCombos.size() == 0) {
			ComboMaker builder = new ComboMaker(charSet, length);
			for(char[] combo: builder) {
				try {
					Combination judge = new Combination(combo, charSet);
					int[] score = judge.judge(guess.clone());
					int index = 0;
					while(score[0] --> 0) {index += 1 + length - score[0];}
					scores[score[1] + index] += 1; 
				} catch(Exception e) {
					e.printStackTrace();
				}
			}		
		} else {
			for(char[] combo: remainingCombos) {
				try {
					Combination judge = new Combination(combo, charSet);
					int[] score = judge.judge(guess.clone());
					int index = 0;
					while(score[0] --> 0) {index += 1 + length - score[0];}
					scores[score[1] + index] += 1; 
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		int[] worst = new int[2];
		for(int i = 0; i < scoreCount; i++) {
			if(scores[i] > worst[1]) {
				worst[0] = i;
				worst[1] = scores[i];
			}
		}
		/*System.out.println();
		for(int i = 0; i < scoreCount; i++) {
			System.out.print("[" + indexedScores[i][0] + "," + indexedScores[i][1] + "] ");
		}
		System.out.println();*/
		this.restrictList(guess, indexedScores[worst[0]]);
		return indexedScores[worst[0]];
	}
	
	private void restrictList(Combination guess, int[] scores) {
		if(remainingCombos.size() == 0) {
			for(char[] combo: new ComboMaker(charSet, length)) {
				try {
					Combination judge = new Combination(combo, charSet);
					int[] score = judge.judge(guess.clone());
					if(score[0] == scores[0] && score[1] == scores[1]) {
						remainingCombos.add(combo);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			ArrayList<char[]> newCombos = new ArrayList<char[]>();
			for(char[] combo: remainingCombos) {
				try {
					Combination judge = new Combination(combo, charSet);
					int[] score = judge.judge(guess.clone());
					if(score[0] == scores[0] && score[1] == scores[1]) {
						newCombos.add(combo);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			remainingCombos = newCombos;
		}
		/*String inside = "";
		for(char[] ca : remainingCombos) {
			inside += '[';
			for(char c : ca) {
				inside += c;
			}
			inside += "], ";
		}
		System.out.println(inside);*/
	}
}
