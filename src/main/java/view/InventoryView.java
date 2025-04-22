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

public class InventoryView extends JComponent {

    private final Inventory inventory;
    private BufferedImage toolsInv, blocksInv, potionsInv;

    private static final int scale = 3;
    private static final int icon_size = 16;
    private static final int cells = 3;

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
            toolsInv = loadScale("/HUD/inv1.png");
            blocksInv = loadScale("/HUD/inv2.png");
            potionsInv = loadScale("/HUD/inv3.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage loadScale(String path) throws IOException {
        BufferedImage src = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        AffineTransform at = AffineTransform.getScaleInstance(3, 3);
        AffineTransformOp op = new AffineTransformOp(
                at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR
        );
        return op.filter(src, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage bg = switch (inventory.getCurrentSection()) {
            case "Blocks" -> blocksInv;
            case "Potions" -> potionsInv;
            default -> toolsInv;
        };
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

        // B) compute cell size
        int cellW = (getWidth()  / cells);
        int cellH = (getHeight() / cells);

        // C) fetch tools + selection state
        List<Item> tools    = inventory.getInventorySection("Tools");
        Item       selected = inventory.getSelectedItem();
        boolean    active   = "Tools".equals(inventory.getCurrentSection());

        // D) draw icons in column 0
        for (int row = 0; row < cells; row++) {
            Item itemToDraw = null;

            if (active) {
                // active: slot #row gets tools[row], if it exists
                if (row < tools.size()) itemToDraw = tools.get(row);
            } else {
                // inactive: only bottom slot (row 2) shows the last‑selected
                if (row == cells - 1) itemToDraw = selected;
            }

            if (itemToDraw instanceof Tool) {
                BufferedImage icon = ItemLoader.getIcon(itemToDraw);
                if (icon != null) {
                    int width =  icon.getWidth() * 2;
                    int height = icon.getHeight() * 2;

                    int x = /* col0 */ 0 * cellW + (cellW - width)  / 2;
                    int y = /* row */ row * cellH + (cellH - height) / 2;
                    g2d.drawImage(icon, x, y, width, height,null);
                }

                // highlight the selected tool when active
                if (active && itemToDraw != null && itemToDraw.equals(selected)) {
                    g2d.setColor(Color.YELLOW);
                    g2d.setStroke(new BasicStroke(3));
                    // inset by 1px so the stroke sits inside the slot edges
                    g2d.drawRect(
                            /* x */ 1,
                            /* y */ row * cellH + 1,
                            cellW - 2,
                            cellH - 2
                    );
                }
            }
        }

        g2d.dispose();
    }
}
