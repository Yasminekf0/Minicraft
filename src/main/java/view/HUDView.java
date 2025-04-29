package view;

import model.entity.Player;
import model.items.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.ScreenSettings.playerScreenX;
import static view.ScreenSettings.playerScreenY;
import static view.ScreenSettings.tileSize;

public class HUDView extends JPanel {
    private final Player player;

    private final ImageIcon slashGif;
    private boolean slashActive = false;
    private int slashTileX, slashTileY;
    private final Timer slashTimer;

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

        slashGif = new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/HUD/slash.gif")
        ));

        slashTimer = new Timer(500, e -> {
            slashActive = false;
            ((Timer)e.getSource()).stop();
            repaint();
        });
        slashTimer.setRepeats(false);
    }

    public void slashAt(int tileX, int tileY) {
        this.slashTileX = tileX;
        this.slashTileY = tileY;
        this.slashActive = true;
        slashTimer.restart();
        repaint();
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

        if (slashActive) {
            int wx2 = slashTileX * tileSize;
            int wy2 = slashTileY * tileSize;
            int sx2 = (int) (wx2 - player.getWorldPos().getX() + playerScreenX);
            int sy2 = (int) (wy2 - player.getWorldPos().getY() + playerScreenY);

            // Draw the slash gif centered on the tile
            int dx = sx2 + (tileSize - slashGif.getIconWidth()) / 2;
            int dy = sy2 + (tileSize - slashGif.getIconHeight()) / 2;
            slashGif.paintIcon(this, g2, dx, dy);

            // Draw the currently-selected item's icon on top
            Item sel = player.getInventory().getSelectedItem();
            if (sel != null) {
                BufferedImage icon = ItemLoader.getIcon(sel);
                if (icon != null) {
                    int iconSize = tileSize / 3;
                    int ix = sx2 + (tileSize - iconSize) / 2;
                    int iy = sy2 + (tileSize - iconSize) / 2;
                    g2.drawImage(icon, ix, iy, iconSize, iconSize, null);
                }
            }
        }

        g2.dispose();
    }
}
