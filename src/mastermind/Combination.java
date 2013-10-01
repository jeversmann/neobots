package mastermind;

public class Combination {
	
	private char[] combination;
	private char[] charSet;
	private int length;
	private static final int defaultLength = 4;
	private static final char[] defaultSet = {'a','b','c','d','e','f'};
	
	public Combination(char[] guess) throws Exception {
		charSet = defaultSet;
		length = guess.length;
		combination = new char[length];
		for(int i = 0; i < length; i++) {
			boolean legal = false;
			for(char c : charSet) {
				if(c == guess[i]) {
					legal = true;
					break;
				}
			}
			if(legal) {
				combination[i] = guess[i];
			} else {
				throw new Exception("Invalid char");
			}
		}
	}
	
	public Combination(char[] guess, char[] set) throws Exception {
		charSet = set;
		length = guess.length;
		combination = new char[length];
		for(int i = 0; i < length; i++) {
			boolean legal = false;
			for(char c : charSet) {
				if(c == guess[i]) {
					legal = true;
					break;
				}
			}
			if(legal) {
				combination[i] = guess[i];
			} else {
				throw new Exception("Invalid char");
			}
		}
	}
	
	public static Combination makeRand() {
		char[] guess = new char[defaultLength];
		for(int i = 0; i < defaultLength; i++) {
			guess[i] = defaultSet[(int)(Math.random() * defaultLength)];
		}
		try {
			return new Combination(guess);
		} catch(Exception e) {
			System.err.print("makeRand() failure.");
		}
		return null;
	}
	
	public static Combination makeRand(int len) {
		char[] guess = new char[len];
		for(int i = 0; i < len; i++) {
			guess[i] = defaultSet[(int)(Math.random() * defaultLength)];
		}
		try {
			return new Combination(guess);
		} catch(Exception e) {
			System.err.print("makeRand(len) failure.");
		}
		return null;
	}
	
	public static Combination makeRand(char[] newSet) {
		char[] guess = new char[defaultLength];
		for(int i = 0; i < defaultLength; i++) {
			guess[i] = newSet[(int)(Math.random() * newSet.length)];
		}
		try {
			return new Combination(guess, newSet);
		} catch(Exception e) {
			System.err.print("makeRand(set) failure.");
		}
		return null;
	}
	
	public static Combination makeRand(char[] newSet, int len) {
		char[] guess = new char[len];
		for(int i = 0; i < len; i++) {
			guess[i] = newSet[(int)(Math.random() * newSet.length)];
		}
		try {
			return new Combination(guess, newSet);
		} catch(Exception e) {
			System.err.print("makeRand(set, len) failure.");
		}
		return null;
	}
	
	public Combination make(char[] guess) throws Exception {
		return new Combination(guess, charSet);
	}
	
	public char[] getCombination() {
		return combination;
	}
	
	public int getSize() {
		return length;
	}

	public String toString() {
		String resp = "";
		for(char c: combination) {
			resp += c + ", ";
		}
		return resp.substring(0,resp.length() - 1);
	}

	public char[] getSet() {
		return charSet;
	}
	
	public int getTries() {
		return 1 + (int) Math.log(Math.pow(charSet.length, length));
	}
	
	public Combination clone() {
		try {
			return new Combination(combination.clone(), charSet);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int[] judge(Combination guess) throws Exception {
		int[] resp = new int[2];
		char[] guessChars = guess.getCombination();
		boolean legal = false;
		char[] tempCom = combination.clone();
		if(guess.getSize() != length) {
			throw new Exception("Invalid guess.");
		}
		for(int i = 0; i < length; i++) {
			if(tempCom[i] == guessChars[i]) {
				guessChars[i] = (char) 21;
				tempCom[i] = (char) 22;
				resp[0] += 1;
			}
		}
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				if(0 == guessChars[j] - tempCom[i]) {
					tempCom[i] = (char) 22;
					guessChars[j] = (char) 21;
					resp[1] += 1;
				}
			}
		}
		return resp;
	}
}
