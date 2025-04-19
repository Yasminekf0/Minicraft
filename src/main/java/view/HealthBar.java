// HealthBar.java
package view;

import model.entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HealthBar extends JComponent {
    private final Player player;
    private final BufferedImage fullHeart;
    private final BufferedImage emptyHeart;
    private final int heartWidth;
    private final int heartHeight;

    public HealthBar(Player player) {
        this.player = player;
        try {
            fullHeart  = ImageIO.read(getClass().getResourceAsStream("/HUD/full.png"));
            emptyHeart = ImageIO.read(getClass().getResourceAsStream("/HUD/empty_heart.png"));
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Could not load heart images", e);
        }
        heartWidth  = fullHeart.getWidth() * 2;
        heartHeight = fullHeart.getHeight() * 2;

        // Use player's maxHealth to size the component
        int totalHearts = player.getMaxHealth();
        int totalWidth  = totalHearts * heartWidth + (totalHearts - 1) * 2;
        setPreferredSize(new Dimension(totalWidth, heartHeight));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int currHealth = player.getHealth();
        int maxHearts  = player.getMaxHealth();

        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < maxHearts; i++) {
            int x = i * (heartWidth + 2);
            BufferedImage img = (i < currHealth ? fullHeart : emptyHeart);
            g2.drawImage(img, x, 0, heartWidth, heartHeight, null);
        }
    }
}