package view;

import model.entity.Player;

import javax.swing.*;
import java.awt.*;

import static view.ScreenSettings.*;

// HUDView.java
public class HUDView extends JPanel {
    private final Player player;

    public HUDView(Player player) {
        this.player = player;

        setOpaque(false);
        setLayout(new BorderLayout());

        // Widgets
        HealthBar healthBar = new HealthBar(player);
        //InventoryView inventoryView = new InventoryView(player.getInventory());

        // Health Bar Placement
        JPanel bottomCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        bottomCenter.setOpaque(false);
        bottomCenter.add(healthBar);
        add(bottomCenter, BorderLayout.SOUTH);

        // Inventory Placement
        //JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        //bottomRight.setOpaque(false);
        //bottomRight.add(inventoryView);
        //add(bottomRight, BorderLayout.SOUTH);
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
