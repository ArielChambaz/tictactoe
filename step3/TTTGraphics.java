import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTTGraphics extends JFrame {
   private static final int ROWS = 3, COLS = 3;
   private static final int CELL_SIZE = 120;
   private static final int SYMBOL_STROKE_WIDTH = 8;
   private static final int CELL_PADDING = CELL_SIZE / 5;
   private static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;

   enum State { PLAYING, DRAW, CROSS_WON, NOUGHT_WON }
   enum Seed  { CROSS, NOUGHT, NO_SEED }

   private State currentState;
   private Seed currentPlayer;
   private Seed[][] board;

   private JLabel statusBar;
   private GamePanel gamePanel;

   public TTTGraphics() {
      board = new Seed[ROWS][COLS];
      gamePanel = new GamePanel();
      gamePanel.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
      gamePanel.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            int row = e.getY() / CELL_SIZE;
            int col = e.getX() / CELL_SIZE;
            if (currentState == State.PLAYING) {
               if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == Seed.NO_SEED) {
                  board[row][col] = currentPlayer;
                  currentState = stepGame(currentPlayer, row, col);
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
               }
            } else {
               newGame();
            }
            repaint();
         }
      });

      statusBar = new JLabel(" ");
      statusBar.setFont(new Font("Arial", Font.PLAIN, 14));
      statusBar.setOpaque(true);
      statusBar.setBackground(new Color(220, 220, 220));
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

      setLayout(new BorderLayout());
      add(gamePanel, BorderLayout.CENTER);
      add(statusBar, BorderLayout.SOUTH);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setTitle("Tic-Tac-Toe");
      setVisible(true);

      newGame();
   }

   private void newGame() {
      for (int i = 0; i < ROWS; i++)
         for (int j = 0; j < COLS; j++)
            board[i][j] = Seed.NO_SEED;
      currentPlayer = Seed.CROSS;
      currentState = State.PLAYING;
   }

   private State stepGame(Seed player, int row, int col) {
      if ((board[row][0] == player && board[row][1] == player && board[row][2] == player) ||
          (board[0][col] == player && board[1][col] == player && board[2][col] == player) ||
          (row == col && board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
          (row + col == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
         return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
      }
      for (int i = 0; i < ROWS; i++)
         for (int j = 0; j < COLS; j++)
            if (board[i][j] == Seed.NO_SEED) return State.PLAYING;
      return State.DRAW;
   }

   class GamePanel extends JPanel {
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setColor(Color.WHITE);
         g.fillRect(0, 0, getWidth(), getHeight());

         g.setColor(Color.LIGHT_GRAY);
         for (int i = 1; i < ROWS; i++)
            g.fillRect(0, i * CELL_SIZE - 2, getWidth(), 4);
         for (int i = 1; i < COLS; i++)
            g.fillRect(i * CELL_SIZE - 2, 0, 4, getHeight());

         Graphics2D g2 = (Graphics2D) g;
         g2.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

         for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
               int x1 = col * CELL_SIZE + CELL_PADDING;
               int y1 = row * CELL_SIZE + CELL_PADDING;
               if (board[row][col] == Seed.CROSS) {
                  g2.setColor(Color.RED);
                  int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                  int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                  g2.drawLine(x1, y1, x2, y2);
                  g2.drawLine(x2, y1, x1, y2);
               } else if (board[row][col] == Seed.NOUGHT) {
                  g2.setColor(Color.BLUE);
                  g2.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
               }
            }
         }

         if (currentState == State.PLAYING)
            statusBar.setText((currentPlayer == Seed.CROSS ? "X" : "O") + "'s Turn");
         else if (currentState == State.CROSS_WON)
            statusBar.setText("X won! Click to play again.");
         else if (currentState == State.NOUGHT_WON)
            statusBar.setText("O won! Click to play again.");
         else
            statusBar.setText("Draw! Click to play again.");
      }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new TTTGraphics());
   }
}
