import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel {
   public static final String TITLE = "Tic-Tac-Toe OO";
   public static final Color COLOR_BG = Color.WHITE;
   public static final Color COLOR_BG_STATUS = new Color(220, 220, 220);
   public static final Color COLOR_CROSS = new Color(239, 105, 80);
   public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
   public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

   private Board board;
   private State currentState;
   private Seed currentPlayer;
   private JLabel statusBar;

   public GameMain() {
      super.setLayout(new BorderLayout());
      super.setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT + 30));
      super.setBackground(COLOR_BG);

      board = new Board();
      statusBar = new JLabel(" ");
      statusBar.setFont(FONT_STATUS);
      statusBar.setOpaque(true);
      statusBar.setBackground(COLOR_BG_STATUS);
      statusBar.setPreferredSize(new Dimension(300, 30));
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

      add(statusBar, BorderLayout.SOUTH);

      addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            int row = e.getY() / Cell.SIZE;
            int col = e.getX() / Cell.SIZE;
            if (currentState == State.PLAYING) {
               if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS &&
                   board.cells[row][col].content == Seed.NO_SEED) {
                  currentState = board.stepGame(currentPlayer, row, col);
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
               }
            } else {
               newGame();
            }
            repaint();
         }
      });

      newGame();
   }

   public void newGame() {
      board.newGame();
      currentPlayer = Seed.CROSS;
      currentState = State.PLAYING;
   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      setBackground(COLOR_BG);
      board.paint(g);

      if (currentState == State.PLAYING)
         statusBar.setText((currentPlayer == Seed.CROSS ? "X" : "O") + "'s Turn");
      else if (currentState == State.CROSS_WON)
         statusBar.setText("X Won! Click to restart.");
      else if (currentState == State.NOUGHT_WON)
         statusBar.setText("O Won! Click to restart.");
      else
         statusBar.setText("Draw! Click to restart.");
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         JFrame frame = new JFrame(TITLE);
         frame.setContentPane(new GameMain());
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
      });
   }
}
