package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartView extends JPanel {
    private BufferedImage wallpaper;
    private BufferedImage titleImage;
    private final JLabel titleLabel;
    private float alpha = 0f;
    private Timer fadeTimer;

    private final Buttons newGameButton;
    private final Buttons loadGameButton;
    private final Buttons quitButton;

    // Field to scale the title image (1.0 = original size)
    private double titleScale = 0.9;

    public StartView() {
        // Load wallpaper and title images.
        try {
            wallpaper = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/start/wallpaper.jpg")));
            titleImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/start/title.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }

        setOpaque(false);
        setLayout(new GridBagLayout());

        // Create a content panel for the title and buttons.
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Create the title label using a scaled version of the title image.
        titleLabel = new JLabel();
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateTitleIcon();
        contentPanel.add(titleLabel);

        contentPanel.add(Box.createVerticalStrut(50));

        // Create buttons using the new ShadowImageButton class.
        newGameButton = new Buttons("New  Game");
        loadGameButton = new Buttons("Load  Game");
        quitButton    = new Buttons("Quit");

        // Panel for the buttons.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(loadGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(quitButton);

        contentPanel.add(buttonPanel);

        // Add the content panel to the center.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(contentPanel, gbc);

        // Set up the fade-in effect.
        int fadeDelay = 10; // milliseconds per update
        int duration = 500; // total duration in ms
        int steps = duration / fadeDelay;
        fadeTimer = new Timer(fadeDelay, e -> {
            alpha += 1f / steps;
            if (alpha >= 1f) {
                alpha = 1f;
                fadeTimer.stop();
            }
            repaint();
        });
        fadeTimer.start();
    }

    private void updateTitleIcon(){
        if(titleImage != null){
            int newWidth = (int)(titleImage.getWidth() * titleScale);
            int newHeight = (int)(titleImage.getHeight() * titleScale);
            Image scaledTitle = titleImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            titleLabel.setIcon(new ImageIcon(scaledTitle));
        }
    }

    public void setTitleScale(double scale){
        this.titleScale = scale;
        updateTitleIcon();
        revalidate();
        repaint();
    }

    // Listener attachment methods (MVC)
    public void addNewGameListener(ActionListener listener){
        newGameButton.addActionListener(listener);
    }

    public void addLoadGameListener(ActionListener listener){
        loadGameButton.addActionListener(listener);
    }

    public void addQuitListener(ActionListener listener){
        quitButton.addActionListener(listener);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        if (wallpaper != null) {
            g2.drawImage(wallpaper, 0, 0, getWidth(), getHeight(), this);
        }
        g2.dispose();
        Graphics2D g2ForChildren = (Graphics2D) g.create();
        g2ForChildren.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paint(g2ForChildren);
        g2ForChildren.dispose();
    }
}
