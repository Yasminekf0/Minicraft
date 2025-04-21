package view;

import model.entity.Inventory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class InventoryView extends JComponent {
    private final Inventory inventory;
    private BufferedImage toolsImg, blocksImg, potionsImg;

    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        setOpaque(false);
        loadSectionImages();
    }

    private void loadSectionImages() {
        try {
            toolsImg    = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HUD/inv1.png")));
            blocksImg   = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HUD/inv2.png")));
            potionsImg  = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HUD/inv3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage toDraw = switch (inventory.getCurrentSection()) {
            case "Blocks" -> blocksImg;
            case "Potions" -> potionsImg;
            default -> toolsImg;
        };
        g.drawImage(toDraw, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(toolsImg.getWidth() * 3, toolsImg.getHeight() * 3);
    }
}
