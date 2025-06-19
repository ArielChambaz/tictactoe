import java.awt.*;

public class Board {
   public static final int ROWS = 3;
   public static final int COLS = 3;
   public static final int WIDTH = Cell.SIZE * COLS;
   public static final int HEIGHT = Cell.SIZE * ROWS;
   public static final int GRID = 8;
   public static final Color GRID_COLOR = Color.LIGHT_GRAY;

   Cell[][] cells;

   public Board() {
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

      if ((cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player) ||
          (cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player) ||
          (row == col && cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player) ||
          (row + col == 2 && cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player)) {
         return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
      }

      for (int r = 0; r < ROWS; r++)
         for (int c = 0; c < COLS; c++)
            if (cells[r][c].content == Seed.NO_SEED) return State.PLAYING;

      return State.DRAW;
   }

   public void paint(Graphics g) {
      g.setColor(GRID_COLOR);
      for (int r = 1; r < ROWS; r++)
         g.fillRect(0, Cell.SIZE * r - GRID / 2, WIDTH, GRID);
      for (int c = 1; c < COLS; c++)
         g.fillRect(Cell.SIZE * c - GRID / 2, 0, GRID, HEIGHT);

      for (int row = 0; row < ROWS; row++)
         for (int col = 0; col < COLS; col++)
            cells[row][col].paint(g);
   }
}
