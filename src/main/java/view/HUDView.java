package view;

import model.entity.Player;

import javax.swing.*;
import java.awt.*;

public class HUDView extends JPanel {
    public HUDView(Player player) {
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
}
