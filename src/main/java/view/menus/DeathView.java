package view.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class DeathView extends JPanel {
    private final Buttons quitButton;
    private final JLabel gameOverLabel;

    public DeathView() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        // Content panel
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Game Over image (put your image at /death/gameover.png on the classpath)
        gameOverLabel = new JLabel(new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/HUD/dead.png"))
        ));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Quit button
        quitButton = new Buttons("Quit  Game");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(gameOverLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(quitButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        add(content, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // exactly as in OptionsView for the dark overlay
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
    }

    public void addQuitListener(ActionListener l) {
        quitButton.addActionListener(l);
    }
}
