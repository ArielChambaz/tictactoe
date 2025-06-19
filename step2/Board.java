public class Board {
   public static final int ROWS = 3;
   public static final int COLS = 3;

   Cell[][] cells;

   public Board() {
      initGame();
   }

   public void initGame() {
      cells = new Cell[ROWS][COLS];
      for (int row = 0; row < ROWS; ++row)
         for (int col = 0; col < COLS; ++col)
            cells[row][col] = new Cell(row, col);
   }

   public void newGame() {
      for (int row = 0; row < ROWS; ++row)
         for (int col = 0; col < COLS; ++col)
            cells[row][col].newGame();
   }

   public State stepGame(Seed player, int row, int col) {
      cells[row][col].content = player;

      if ((cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player) ||
          (cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player) ||
          (row == col && cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player) ||
          (row + col == 2 && cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player)) {
         return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
      }

      for (int r = 0; r < ROWS; ++r)
         for (int c = 0; c < COLS; ++c)
            if (cells[r][c].content == Seed.NO_SEED) return State.PLAYING;

      return State.DRAW;
   }

   public void paint() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            System.out.print(" ");
            cells[row][col].paint();
            System.out.print(" ");
            if (col != COLS - 1) System.out.print("|");
         }
         System.out.println();
         if (row != ROWS - 1) System.out.println("-----------");
      }
      System.out.println();
   }
}
