package view;

import model.entity.Player;

import javax.swing.*;
import java.awt.*;

// HUDView.java
public class HUDView extends JPanel {
    public HUDView(Player player) {
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
}
