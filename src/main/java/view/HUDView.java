package view;

import model.entity.Player;

import javax.swing.*;
import java.awt.*;

import static view.ScreenSettings.*;
import static view.ScreenSettings.tileSize;

public class HUDView extends JPanel {
    private final Player player;

    public HUDView(Player player) {
        this.player = player;

        setOpaque(false);
        setLayout(new GridBagLayout());

        // Setting up stack
        JPanel stack = new JPanel(new GridBagLayout());
        stack.setOpaque(false);
        GridBagConstraints sc = new GridBagConstraints();
        sc.anchor = GridBagConstraints.WEST;

        //Adding Widgets
        sc.gridy = 0;
        sc.insets = new Insets(0, 15, 10, 0);
        stack.add(new InventoryView(player.getInventory()), sc);

        sc.gridy = 1;
        sc.insets = new Insets(0, 15, 15, 0);
        stack.add(new HealthBarView(player), sc);

        // Adding Stack to Bottom
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;

        add(stack, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw a border around the focused tile
        Graphics2D g2 = (Graphics2D) g.create();

        // Determine day/night to pick border color
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));

        // Compute focused tile screen coordinates
        int focusedX = player.getWorldPos().getFocusedTileX();
        int focusedY = player.getWorldPos().getFocusedTileY();
        int worldX = focusedX * tileSize;
        int worldY = focusedY * tileSize;
        int screenX = (int) (worldX - player.getWorldPos().getX() + playerScreenX);
        int screenY = (int) (worldY - player.getWorldPos().getY() + playerScreenY);

        // Draw the targeting box
        g2.drawRect(screenX, screenY, tileSize, tileSize);
        g2.dispose();
    }
}
