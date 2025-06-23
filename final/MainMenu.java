import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Tic-Tac-Toe Menu");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel modeLabel = new JLabel("Select Game Mode:");
        String[] modes = {"Classic (3x3)", "4x4 Grid"};
        JComboBox<String> modeSelector = new JComboBox<>(modes);

        JCheckBox blitzCheckbox = new JCheckBox("Blitz mode (30s per player)");

        JButton startButton = new JButton("Start Game");
        JButton viewScoresButton = new JButton("View Scores");
        JButton quitButton = new JButton("Quit");

        startButton.addActionListener(e -> {
            String selected = (String) modeSelector.getSelectedItem();
            if (selected != null && selected.contains("4x4")) {
                GameMain.ROWS = 4;
                GameMain.COLS = 4;
            } else {
                GameMain.ROWS = 3;
                GameMain.COLS = 3;
            }

            String playerX = JOptionPane.showInputDialog(this, "Enter name for Player X:");
            String playerO = JOptionPane.showInputDialog(this, "Enter name for Player O:");
            if (playerX != null && playerO != null && !playerX.isEmpty() && !playerO.isEmpty()) {
                dispose();
                GameMain.launchGame(playerX, playerO, blitzCheckbox.isSelected());
            }
        });

        viewScoresButton.addActionListener(e -> {
            String scores = ScoreManager.readScores();
            JOptionPane.showMessageDialog(this, scores, "Scores", JOptionPane.INFORMATION_MESSAGE);
        });

        quitButton.addActionListener(e -> System.exit(0));

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        topPanel.add(modeLabel);
        topPanel.add(modeSelector);
        topPanel.add(blitzCheckbox);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.add(startButton);
        buttonPanel.add(viewScoresButton);
        buttonPanel.add(quitButton);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        content.add(topPanel, BorderLayout.NORTH);
        content.add(buttonPanel, BorderLayout.CENTER);

        add(content);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
