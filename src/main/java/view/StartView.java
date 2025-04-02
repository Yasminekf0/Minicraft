package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartView extends JPanel {
    private CardLayout cardLayout;
    private JPanel loadingPanel;
    private JPanel landingPanel;
    private JProgressBar progressBar;

    public StartView() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create loading screen
        loadingPanel = new JPanel(new GridBagLayout());
        loadingPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Minicraft", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 80, 0);
        loadingPanel.add(titleLabel, gbc);

        progressBar = new JProgressBar(0, 100);
        progressBar.setForeground(Color.RED);
        progressBar.setBackground(Color.WHITE);
        progressBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        progressBar.setPreferredSize(new Dimension(700, 20));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        loadingPanel.add(progressBar, gbc);

        // Create landing page
        landingPanel = new JPanel(new GridBagLayout());
        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(10, 10, 10, 10);
        landingPanel.add(newGameButton, gbc1);
        landingPanel.add(loadGameButton, gbc1);

        add(loadingPanel, "loading");
        add(landingPanel, "landing");

        // Show the loading screen first
        cardLayout.show(this, "loading");

        // Simulate a 3-second loading time
        Timer timer = new Timer(3000, e -> cardLayout.show(StartView.this, "landing"));
        timer.setRepeats(false);
        timer.start();
    }

    public void addNewGameListener(ActionListener listener) {
        // Attach a listener to the "New Game" button in landingPanel
        for (Component comp : landingPanel.getComponents()) {
            if (comp instanceof JButton && "New Game".equals(((JButton) comp).getText())) {
                ((JButton) comp).addActionListener(listener);
            }
        }
    }
}
