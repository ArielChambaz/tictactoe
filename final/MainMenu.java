import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Tic-Tac-Toe Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton startButton = new JButton("Start Game");
        JButton viewScoresButton = new JButton("View Scores");
        JButton quitButton = new JButton("Quit");

        startButton.addActionListener(e -> {
            String playerX = JOptionPane.showInputDialog(this, "Enter name for Player X:");
            String playerO = JOptionPane.showInputDialog(this, "Enter name for Player O:");

            if (playerX != null && playerO != null && !playerX.isEmpty() && !playerO.isEmpty()) {
                dispose(); // close menu
                GameMain.launchGame(playerX, playerO);
            }
        });

        viewScoresButton.addActionListener(e -> {
            String scores = ScoreManager.readScores();
            JOptionPane.showMessageDialog(this, scores, "Scores", JOptionPane.INFORMATION_MESSAGE);
        });

        quitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(viewScoresButton);
        add(quitButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
