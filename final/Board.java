import java.awt.*;

public class Board {
   public static int ROWS;
   public static int COLS;
   public static final int GRID = 8;
   public static final Color GRID_COLOR = Color.LIGHT_GRAY;

   Cell[][] cells;

   public Board(int rows, int cols) {
      ROWS = rows;
      COLS = cols;
      initGame();
   }

   public void initGame() {
      cells = new Cell[ROWS][COLS];
      for (int row = 0; row < ROWS; row++)
         for (int col = 0; col < COLS; col++)
            cells[row][col] = new Cell(row, col);
   }

   public void newGame() {
      for (int row = 0; row < ROWS; row++)
         for (int col = 0; col < COLS; col++)
            cells[row][col].newGame();
   }

   public State stepGame(Seed player, int row, int col) {
      cells[row][col].content = player;

      if (checkWin(player, row, col)) {
         return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
      }

      for (int r = 0; r < ROWS; r++)
         for (int c = 0; c < COLS; c++)
            if (cells[r][c].content == Seed.NO_SEED)
               return State.PLAYING;

      return State.DRAW;
   }

   private boolean checkWin(Seed player, int row, int col) {
      return checkLine(player, row, 0, 0, 1) || // row
             checkLine(player, 0, col, 1, 0) || // column
             checkLine(player, 0, 0, 1, 1) ||   // diagonal \
             checkLine(player, 0, COLS - 1, 1, -1); // diagonal /
   }

   private boolean checkLine(Seed player, int startRow, int startCol, int dRow, int dCol) {
      int count = 0;
      int goal = Math.min(ROWS, COLS); // 3 for 3x3, 4 for 4x4
      for (int i = 0; i < Math.max(ROWS, COLS); i++) {
         int r = startRow + i * dRow;
         int c = startCol + i * dCol;
         if (r >= ROWS || c >= COLS || r < 0 || c < 0) break;
         if (cells[r][c].content == player) {
            count++;
            if (count == goal) return true;
         } else {
            count = 0;
         }
      }
      return false;
   }

   public void paint(Graphics g) {
      g.setColor(GRID_COLOR);
      for (int r = 1; r < ROWS; r++)
         g.fillRect(0, Cell.SIZE * r - GRID / 2, Cell.SIZE * COLS, GRID);
      for (int c = 1; c < COLS; c++)
         g.fillRect(Cell.SIZE * c - GRID / 2, 0, GRID, Cell.SIZE * ROWS);

      for (int r = 0; r < ROWS; r++)
         for (int c = 0; c < COLS; c++)
            cells[r][c].paint(g);
   }
}
