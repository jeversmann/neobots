package sudoku;

public class Cell {

  private int MAX_SIZE;
  private boolean[] myOptions;
  private int myValue;

  public Cell(int max) {
    MAX_SIZE = max;
    reset();
  }

  public Cell(int val, int max) {
    MAX_SIZE = max;
    reset();
    if(val != 0) {
      set(val);
    }
  }

  public void reset() {
    myOptions = new boolean[MAX_SIZE];
    for(int i = 0; i < MAX_SIZE; i++) {
      myOptions[i] = true;
    }
   myValue = 0;
  }

  public void mark(int val) {
    if(0 < val && val <= MAX_SIZE) {
      myOptions[val - 1] = false;
    }
  }

  public int getValue() {
    return myValue;
  }

  public int check() {
    boolean test = false;
    if(myValue != 0) {
      return 0;
    }
    int ret = 0;
    for(int i = 0; i < MAX_SIZE; i++) {
      if(myOptions[i]) {
        test = true;
        if(ret == 0) {
          ret = i + 1;
        } else {
          return 0;
        }
      }
    }
    return ret;
  }

  public void set(int val) {
    if(0 < val && val <= MAX_SIZE) {
      myValue = val;
    }
    for(int i = 0; i < MAX_SIZE; i++) {
      myOptions[i] = false;
    }

  }

  public String toString() {
    return (myValue != 0) ? "" + myValue : " ";
  }

  public String getMarks() {
    String resp = "Options:";
    for(boolean option: myOptions) {
      resp += option ? "O" : "X";
    }
    return resp;
  }

  public int countMarks() {
    int resp = 0;
    for(boolean option: myOptions) {
      if(option) resp++;
    }
    return resp;
  }

  public int[] getPossible() {
    int size = 0;
    for(boolean b: myOptions) {
      if(b) size++;
    }
    int[] resp = new int[size];
    for(int i = 0, j = 0; i < MAX_SIZE; i++) {
      if(myOptions[i]) {
        resp[j++] = i + 1;
      }
    }
    return resp;
  }
}
