import java.awt.*;

public class Cell {
   public static final int SIZE = 120;
   public static final int PADDING = SIZE / 5;
   public static final int SEED_SIZE = SIZE - PADDING * 2;
   public static final int STROKE = 8;

   Seed content;
   int row, col;

   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      this.content = Seed.NO_SEED;
   }

   public void newGame() {
      content = Seed.NO_SEED;
   }

   public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setStroke(new BasicStroke(STROKE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      int x1 = col * SIZE + PADDING;
      int y1 = row * SIZE + PADDING;
      if (content == Seed.CROSS) {
         g2d.setColor(GameMain.COLOR_CROSS);
         int x2 = (col + 1) * SIZE - PADDING;
         int y2 = (row + 1) * SIZE - PADDING;
         g2d.drawLine(x1, y1, x2, y2);
         g2d.drawLine(x2, y1, x1, y2);
      } else if (content == Seed.NOUGHT) {
         g2d.setColor(GameMain.COLOR_NOUGHT);
         g2d.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
      }
   }
}
