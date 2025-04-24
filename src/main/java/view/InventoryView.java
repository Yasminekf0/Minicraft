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

    private static final int BORDER_RAW = 2;
    private static final int CELLS      = 3;

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

        // 1) Draw the background for the current section
        BufferedImage bg = switch (inventory.getCurrentSection()) {
            case "Blocks"  -> blocksInv;
            case "Potions" -> potionsInv;
            default         -> toolsInv;
        };
        g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

        // 2) Compute the scaled outer-border margin (2 raw px × scaleHUD)
        int margin = BORDER_RAW * scaleHUD;

        // 3) Compute the interior grid size (excluding that margin)
        int gridW = getWidth()  - 2 * margin;
        int gridH = getHeight() - 2 * margin;

        // 4) Divide that interior into 3×3 cells
        int cellW = gridW / CELLS;
        int cellH = gridH / CELLS;

        // 5) Fetch the tools list and the selected item
        List<Item> tools    = inventory.getInventorySection("Tools");
        Item       selected = inventory.getSelectedItem();
        boolean    active   = "Tools".equals(inventory.getCurrentSection());

        // 5a) Precompute selected index and list size
        int selIdx = tools.indexOf(selected);
        int size   = tools.size();

        // 6) Loop over the three rows in column 0
        for (int row = 0; row < CELLS; row++) {
            Item itemToDraw = null;

            if (active) {
                // rotate so that selected always lands in row 2 (bottom)
                int offset = (CELLS - 1) - row; // -2, -1, 0
                int idx    = (selIdx + offset + size) % size;
                itemToDraw = tools.get(idx);
            } else if (row == CELLS - 1) {
                // when inactive, only draw selected in bottom slot
                itemToDraw = selected;
            }

            if (itemToDraw instanceof Tool) {
                BufferedImage icon = ItemLoader.getIcon(itemToDraw);
                if (icon != null) {
                    // 7) Scale icon (16×16 → 48×48 if scaleHUD==3), then shrink slightly
                    int iconW = Math.round(icon.getWidth()  * scaleHUD * 0.9f);
                    int iconH = Math.round(icon.getHeight() * scaleHUD * 0.9f);

                    // 8) Center inside its cell, offset by the outer margin
                    int x = margin
                            + /* col 0 */ 0 * cellW
                            + (cellW - iconW) / 2;
                    int y = margin
                            + row * cellH
                            + (cellH - iconH) / 2;

                    g2d.drawImage(icon, x, y, iconW, iconH, null);
                }
            }
        }

        g2d.dispose();
    }
}
