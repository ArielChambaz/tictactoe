import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel {
    public static final String TITLE = "Tic-Tac-Toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(220, 220, 220);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private JButton restartButton;

    public GameMain() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Board.WIDTH, Board.HEIGHT + 30));
        setBackground(COLOR_BG);

        board = new Board();

        // Bottom panel with status and restart button
        JPanel bottomPanel = new JPanel(new BorderLayout());

        statusBar = new JLabel(" ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setOpaque(true);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        restartButton = new JButton("Restart");
        restartButton.setFont(FONT_STATUS);
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> {
            newGame();
            repaint();
        });

        bottomPanel.add(statusBar, BorderLayout.CENTER);
        bottomPanel.add(restartButton, BorderLayout.EAST);
        bottomPanel.setPreferredSize(new Dimension(Board.WIDTH, 30));
        add(bottomPanel, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / Cell.SIZE;
                int col = e.getX() / Cell.SIZE;
                if (currentState == State.PLAYING) {
                    if (board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);
                        if (currentState == State.PLAYING) {
                            SoundEffect.EAT.play();
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        } else {
                            SoundEffect.DIE.play();
                        }
                    }
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
        restartButton.setVisible(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.paint(g);

        if (currentState == State.PLAYING) {
            statusBar.setText((currentPlayer == Seed.CROSS ? "X's Turn" : "O's Turn"));
            restartButton.setVisible(false);
        } else if (currentState == State.CROSS_WON) {
            statusBar.setText("X Won!");
            restartButton.setVisible(true);
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setText("O Won!");
            restartButton.setVisible(true);
        } else {
            statusBar.setText("Draw!");
            restartButton.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SoundEffect.init();
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new GameMain());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
