package view;

import model.entity.Player;

import javax.swing.*;
import java.awt.*;

public class HUDView extends JPanel {
    private final Player player;           // or a small HUD model
    public HUDView(Player player) {
        this.player = player;
        setOpaque(false);
        setLayout(null);                    // absolute positioning, or use OverlayLayout
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        // draw health bar at, say, (10,10)
        int barWidth = 100;
        int health = player.getHealth(), max = player.getMaxHealth();
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(10, 10, barWidth, 10);
        g2.setColor(Color.RED);
        g2.fillRect(10, 10, (int)(barWidth * (health / (double)max)), 10);
        // draw inventory icons, etc.
    }
}
