package mastermind;

import java.util.Iterator;


public class ComboMaker implements Iterator<char[]>, Iterable<char[]> {

	private char[] charSet;
	private int length;
	private int[] location;
	private boolean canContinue;
	
	public ComboMaker(char[] set, int len) {
		charSet = set;
		length = len;
		location = new int[len];
		canContinue = true;
	}
	
	public boolean hasNext() {
		return canContinue;
	}
	
	public char[] next() {
		char[] resp = new char[length];
		for(int i = 0; i < length; i++) {
			resp[i] = charSet[location[i]];
		}
		boolean carry = true;
		for(int i = length - 1; i >= 0; i--) {
			if(carry) {
				location[i] += 1;
				if(location[i] == charSet.length) {
					location[i] = 0;
					carry = true;
				} else {
					carry = false;
				}
			}
		}
		canContinue = !carry;
		return resp;
	}
	
	public void remove() {
		//Does nothing
	}

	public Iterator<char[]> iterator() {
		return this;
	}

}
