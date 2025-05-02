package view;

import controller.KeyController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OptionsView extends JPanel {
    private final Buttons backButton;
    private final Buttons saveButton;
    private final Buttons quitButton;
    private JLabel titleLabel;

    public OptionsView(){
        setOpaque(false);
        setLayout(new GridBagLayout());

        // Create a content panel.
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Create buttons.
        backButton = new Buttons("Back  to  Game");
        saveButton = new Buttons("Save  Game");
        quitButton = new Buttons("Quit  Game");

        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(backButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(saveButton);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(quitButton);

        // Add the content panel using GridBagConstraints.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(contentPanel, gbc);
    }

    // Override the paintComponent method to paint a semitransparent black background.
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // Create a semitransparent black with an alpha value (0-255). Here, 150 creates a good semitransparent effect.
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
    }

    // Attach ActionListeners.
    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addSaveListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void addQuitListener(ActionListener listener) {
        quitButton.addActionListener(listener);
    }
}
