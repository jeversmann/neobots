package sudoku;

public class Board {

  private Cell[][] myCells;

  public class Move {
    public int X;
    public int Y;
    public int VAL;

    public Move(int x, int y, int val) {
      X = x;
      Y = y;
      VAL = val;
    }

    public String toString() {
      return "" + VAL + " at: (" + X + ", " + Y + ")";
    } 
  }

  public Board() {
    this(9);
  }

  public Board(int blocks) {
    myCells = new Cell[blocks][blocks];
    reset();
  }

  public Board(String nums) {
    myCells = new Cell[9][9];
    for(int i = 0; i < nums.length(); i++) {
      myCells[i/9][i%9] = new Cell(nums.charAt(i) - 48, 9);
    }
  }
      

  public void reset() {
    for(int x = 0; x < myCells.length; x++) {
      for(int y = 0; y < myCells.length; y++) {
        myCells[x][y] = new Cell(myCells.length);
      }
    }
  }

  public String toString() {
    String resp = "";
    for(int i = 0; i < myCells.length; i++) {
      for(int j = 0; j < myCells.length; j++) {
        resp += "|" + myCells[i][j].toString();
      }
      resp += "|\n";
    }
    return resp;
  }

  public void printStatus() {
    String resp = "";
    for(int i = 0; i < myCells.length; i++) {
      for(int j = 0; j < myCells.length; j++) {
        resp += "|" + myCells[i][j].toString();
      }
      resp += "|\t";
      for(int j = 0; j < myCells.length; j++) {
        resp += "|" + myCells[i][j].countMarks();
      }
      resp += "|\n";
    }
    System.out.print(resp);
  }

  public void set(int x, int y, int val) {
    myCells[x][y].set(val);
  }

  public void set(Board.Move move) {
    myCells[move.X][move.Y].set(move.VAL);
    markRow(move.X);
    markCol(move.Y);
    int section = (int) Math.sqrt(myCells.length);
    markSquare(move.X / section, move.Y / section);
  }

  public void printMarks(int x, int y) {
    System.out.println(myCells[x][y].getMarks());
  }

  public void markRow(int x) {
    if(0 > x || x >= myCells.length) {
      return;
    }
    boolean[] vals = new boolean[myCells.length + 1];
    for(int y = 0; y < myCells.length; y++) {
      vals[myCells[x][y].getValue()] = true;
    }
    for(int i = 1; i < vals.length; i++) {
      if(!vals[i]) {
        continue;
      }
      for(int y = 0; y < myCells.length; y++) {
        myCells[x][y].mark(i);
      }
    }
  }

  public void markCol(int y) {
    if(0 > y || y >= myCells.length) {
      return;
    }
    boolean[] vals = new boolean[myCells.length + 1];
    for(int x = 0; x < myCells.length; x++) {
      vals[myCells[x][y].getValue()] = true;
    }
    for(int i = 1; i < vals.length; i++) {
      if(!vals[i]) {
        continue;
      }
      for(int x = 0; x < myCells.length; x++) {
        myCells[x][y].mark(i);
      }
    }
  }

  public void markSquare(int x, int y) {
  int section = (int) Math.sqrt(myCells.length);
  if(0 > x || 0 > y || x >= section || y >= section) {
      return;
    }
    boolean[] vals = new boolean[myCells.length + 1];
    for(int i = 0; i < section; i++) {
      for(int j = 0; j < section; j++) {
        vals[myCells[x*section+i][y*section+j].getValue()] = true;
      }
    }
    for(int k = 1; k < vals.length; k++) {
      if(!vals[k]) {
         continue;
      }
      for(int i = 0; i < section; i++) {
        for(int j = 0; j < section; j++) {
          myCells[x*section+i][y*section+j].mark(k);;
        }
      }
    }
  }

  public Board.Move getMove() {
    Move resp = new Move(0,0,0);
    for(int x = 0; x < myCells.length; x++) {
      for(int y = 0; y < myCells.length; y++) {
        if(myCells[x][y].countMarks() == 1) {
          return new Move(x, y, myCells[x][y].check());
        }
      }
    }
    return resp;
  }

  public void markAll() {
    int section = (int) Math.sqrt(myCells.length);
    for(int i = 0; i < section; i++) {
      for(int j = 0; j < section; j++) {
        markSquare(i,j);
      }
    }
    for(int x = 0; x < myCells.length; x++) {
      markRow(x);
    }
    for(int y = 0; y < myCells.length; y++) {
      markCol(y);
    }
  }

  public Board.Move checkPossible(int x, int y) {
    if(myCells[x][y].getValue() != 0) {
      return new Move(0,0,0);
    }
    //Check along row
    int[] possible = myCells[x][y].getPossible();
    for(int i = 0; i < myCells.length; i++) {
      if(i == x) continue;
      for(int other: myCells[i][y].getPossible()) {
        for(int j = 0; j < possible.length; j++) {
         if(other == possible[j]) {
            possible[j] = 0;
          }
        }
      }
    }
    int resp = 0;
    for(int val: possible) {
      if(val != 0) resp = val;
    }
    if(resp != 0) {
      return new Move(x,y,resp);
    }
    //Check along column
    possible = myCells[x][y].getPossible();
    for(int i = 0; i < myCells.length; i++) {
      if(i == y) continue;
      for(int other: myCells[x][i].getPossible()) {
        for(int j = 0; j < possible.length; j++) {
         if(other == possible[j]) {
            possible[j] = 0;
          }
        }
      }
    }
    resp = 0;
    for(int val: possible) {
      if(val != 0) resp = val;
    }
    if(resp != 0) {
      return new Move(x,y,resp);
    }
    //Check squares
    int section = (int) Math.sqrt(myCells.length);
    possible = myCells[x][y].getPossible();
    for(int i = (x/section) * section;
       i < (x/section) * section + section; i++) {
      for(int j = (y/section) * section;
          j < (y/section) * section + section; j++) {
        if(i == x && j == y) continue;
        for(int other: myCells[i][y].getPossible()) {
          for(int k = 0; k < possible.length; k++) {
          if(other == possible[k]) {
              possible[k] = 0;
            }
          }
        }
      }
    }
    resp = 0;
    for(int val: possible) {
      if(val != 0) resp = val;
    }
    if(resp != 0) {
      return new Move(x,y,resp);
    }
    
    return new Move(0,0,0);
  }
}
