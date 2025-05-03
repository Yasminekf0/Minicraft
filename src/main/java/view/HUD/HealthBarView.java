package view.HUD;

import model.entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HealthBarView extends JComponent {
    private final Player player;
    private BufferedImage fullHeart;
    private BufferedImage emptyHeart;
    private final int heartWidth;
    private final int heartHeight;

    public HealthBarView(Player player) {
        this.player = player;
        try {
            fullHeart  = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HUD/full.png")));
            emptyHeart = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HUD/empty_heart.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
        heartWidth  = fullHeart.getWidth() * 3;
        heartHeight = fullHeart.getHeight() * 3;

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