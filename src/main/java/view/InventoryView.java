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

        // 1) Draw the HUD background for whatever tab is active
        BufferedImage bg = switch (inventory.getCurrentSection()) {
            case "Blocks"  -> blocksInv;
            case "Potions" -> potionsInv;
            default         -> toolsInv;
        };
        g2d.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

        // 2) Carve out the 2px-raw border (scaled) before splitting into 3×3
        int margin = borderRaw * scaleHUD;
        int gridW  = getWidth()  - 2 * margin;
        int gridH  = getHeight() - 2 * margin;
        int cellW  = gridW / cells;  // cells = 3
        int cellH  = gridH / cells;

        // 3) Our three sections in left→right order
        String[] sections = {"Tools", "Blocks", "Potions"};

        // 4) For each column…
        for (int col = 0; col < cells; col++) {
            String sec       = sections[col];
            boolean isActive = sec.equals(inventory.getCurrentSection());

            List<Item> list           = inventory.getInventorySection(sec);
            Item       selectedInSec  = inventory.getSelectedItem(sec);
            int        size           = list.size();
            int        selIdx         = list.indexOf(selectedInSec);

            // 5) And for each of the 3 rows in that column…
            for (int row = 0; row < cells; row++) {
                // pick the item to draw:
                Item itemToDraw;
                if (isActive) {
                    // DOWN rotation: bottom row (row=2) shows selIdx,
                    // row=1 shows selIdx+1, row=0 shows selIdx+2, wrapping
                    int offset = (cells - 1) - row;           // 2,1,0
                    int idx    = (selIdx + offset + size) % size;
                    itemToDraw = list.get(idx);
                } else if (row == cells - 1) {
                    // inactive: only bottom slot
                    itemToDraw = selectedInSec;
                } else {
                    continue;
                }

                // 6) Draw its icon if we have one
                BufferedImage icon = ItemLoader.getIcon(itemToDraw);
                if (icon == null) continue;

                // Scale to HUD and shrink slightly
                int iconW = Math.round(icon.getWidth()  * scaleHUD * 0.8f);
                int iconH = Math.round(icon.getHeight() * scaleHUD * 0.8f);

                // 8) Compute the exact pixel position inside the (col,row) cell
                int x = margin + col * cellW + (cellW - iconW) / 2;
                int y = margin + row * cellH - 1 + (cellH - iconH) / 2;

                g2d.drawImage(icon, x, y, iconW, iconH, null);
            }
        }

        g2d.dispose();
    }

}
