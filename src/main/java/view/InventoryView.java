package view;

import model.entity.Inventory;
import model.items.Item;
import model.items.tools.Tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static view.ScreenSettings.scaleHUD;

public class InventoryView extends JComponent {

    private final Inventory inventory;
    private BufferedImage toolsInv, blocksInv, potionsInv;

    private static final int borderRaw = 2;
    private static final int cells      = 3;

    public InventoryView(Inventory inventory) {
        this.inventory = inventory;
        setOpaque(false);

        loadSectionImages();
        ItemLoader.init();

        setPreferredSize(new Dimension(toolsInv.getWidth(), toolsInv.getHeight()));
        setMaximumSize(getPreferredSize());
    }

    private void loadSectionImages() {
        try {
            toolsInv   = loadScale("/HUD/inv1.png");
            blocksInv  = loadScale("/HUD/inv2.png");
            potionsInv = loadScale("/HUD/inv3.png");
        } catch (IOException e) {
            throw new RuntimeException("Failed loading HUD images", e);
        }
    }

    private BufferedImage loadScale(String path) throws IOException {
        BufferedImage src = ImageIO.read(Objects.requireNonNull(
                getClass().getResourceAsStream(path)));
        AffineTransform at = AffineTransform.getScaleInstance(scaleHUD, scaleHUD);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(src, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        BufferedImage bg = switch (inventory.getCurrentSection()) {
            case "Blocks"  -> blocksInv;
            case "Potions" -> potionsInv;
            default         -> toolsInv;
        };
        g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

        int margin = borderRaw * scaleHUD;
        int gridW  = getWidth()  - 2 * margin;
        int gridH  = getHeight() - 2 * margin;
        int cellW  = gridW / cells;
        int cellH  = gridH / cells;

        String[] sections = {"Tools", "Blocks", "Potions"};

        for (int col = 0; col < cells; col++) {
            String sec = sections[col];
            boolean isActive = sec.equals(inventory.getCurrentSection());

            List<Item> list = inventory.getInventorySection(sec);
            Item selectedInSec = inventory.getSelectedItem(sec);
            int size = list.size();
            int selIdx = list.indexOf(selectedInSec);

            // Picking which item to draw in which row/col
            for (int row = 0; row < cells; row++) {
                Item itemToDraw = null;

                if (isActive) {
                    int startRow = cells - size;
                    if (row >= startRow) {
                        int offset = (cells - 1) - row;
                        int idx    = (selIdx + offset + size) % size;
                        itemToDraw = list.get(idx);
                    }

                } else if (row == cells - 1) {
                    // Inactive inv sections
                    itemToDraw = selectedInSec;
                }

                // Draw icon
                if (itemToDraw == null) continue;
                BufferedImage icon = ItemLoader.getIcon(itemToDraw);
                if (icon == null) continue;

                int iconW = Math.round(icon.getWidth()  * scaleHUD * 0.8f);
                int iconH = Math.round(icon.getHeight() * scaleHUD * 0.8f);

                // Position
                int x = margin + col * cellW + (cellW - iconW) / 2;
                int y = margin + row * cellH + (cellH - iconH) / 2;

                g2d.drawImage(icon, x, y, iconW, iconH, null);
            }
        }
        g2d.dispose();
    }
}
