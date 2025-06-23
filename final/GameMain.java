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
    private JButton backToMenuButton;

    private String playerX = "Player X";
    private String playerO = "Player O";

    public static int ROWS = 3;
    public static int COLS = 3;

    private boolean blitzMode = false;
    private int timeX = 30;
    private int timeO = 30;
    private Timer blitzTimer;

    public static void launchGame(String nameX, String nameO, boolean isBlitz) {
        SwingUtilities.invokeLater(() -> {
            SoundEffect.init();
            JFrame frame = new JFrame(TITLE);
            GameMain panel = new GameMain(isBlitz);
            panel.setPlayerNames(nameX, nameO);
            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public GameMain(boolean isBlitz) {
        this.blitzMode = isBlitz;

        int width = Cell.SIZE * COLS;
        int height = Cell.SIZE * ROWS + 30;
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);

        board = new Board(GameMain.ROWS, GameMain.COLS);

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

        backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setFont(FONT_STATUS);
        backToMenuButton.setVisible(false);
        backToMenuButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
                new MainMenu();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(restartButton);
        buttonPanel.add(backToMenuButton);

        bottomPanel.add(statusBar, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        bottomPanel.setPreferredSize(new Dimension(width, 30));
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
                            if (blitzTimer != null) blitzTimer.stop();
                        }
                        repaint();
                    }
                }
            }
        });

        newGame();
    }

    public void setPlayerNames(String x, String o) {
        this.playerX = x;
        this.playerO = o;
    }

    public void newGame() {
        board.newGame();
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        restartButton.setVisible(false);
        backToMenuButton.setVisible(false);
        timeX = 30;
        timeO = 30;

        if (blitzMode) {
            if (blitzTimer != null) blitzTimer.stop();
            blitzTimer = new Timer(1000, e -> {
                if (currentState != State.PLAYING) {
                    blitzTimer.stop();
                    return;
                }
                if (currentPlayer == Seed.CROSS) {
                    timeX--;
                    if (timeX <= 0) {
                        currentState = State.NOUGHT_WON;
                        SoundEffect.DIE.play();
                        blitzTimer.stop();
                    }
                } else {
                    timeO--;
                    if (timeO <= 0) {
                        currentState = State.CROSS_WON;
                        SoundEffect.DIE.play();
                        blitzTimer.stop();
                    }
                }
                repaint();
            });
            blitzTimer.start();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.paint(g);

        if (currentState == State.PLAYING) {
            String currentName = (currentPlayer == Seed.CROSS) ? playerX : playerO;
            String timerText = blitzMode ? " | Time - " + playerX + ": " + timeX + "s, " + playerO + ": " + timeO + "s" : "";
            statusBar.setText(currentName + "'s Turn (" + currentPlayer.getDisplay() + ")" + timerText);
        } else if (currentState == State.CROSS_WON) {
            statusBar.setText(playerX + " (X) won!");
            ScoreManager.addWin(playerX);
            restartButton.setVisible(true);
            backToMenuButton.setVisible(true);
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setText(playerO + " (O) won!");
            ScoreManager.addWin(playerO);
            restartButton.setVisible(true);
            backToMenuButton.setVisible(true);
        } else {
            statusBar.setText("Draw!");
            restartButton.setVisible(true);
            backToMenuButton.setVisible(true);
        }
    }
}
