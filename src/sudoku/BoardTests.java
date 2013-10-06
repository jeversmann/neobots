package sudoku;

public class BoardTests {

  public static void main(String[] args) {
    //Board b = new Board("051000060007600915469310080700084000543026090610003047000050020074001030085432670");
    Board b = new Board("002005040006023910510704000071098000000041678800006103400100059003000207008000030");
    Board.Move m;
    b.printStatus();
    b.markAll();
    System.out.println();
    b.printStatus();
    while(true) {
      m = b.getMove();
      for(int x = 0; x < 9 & m.VAL == 0; x++) {
        for(int y = 0; y < 9 & m.VAL == 0; y++) {
          m = b.checkPossible(x,y);
        }
      }
      if(m.VAL == 0) {
        break;
      } else {
        b.set(m);
        System.out.println("\n" + m);
        b.printStatus();
      }
    }
  }
}
