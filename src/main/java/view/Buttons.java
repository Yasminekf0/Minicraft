package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Buttons extends JButton {
    private final String buttonText; // the text to be drawn with a shadow

    // Static defaults used by all buttons.
    private static BufferedImage defaultNormal;
    private static BufferedImage defaultRollover;
    private static Font defaultFont;
    private static final double SCALE_FACTOR = 2.0; // Fixed scale factor

    // Static initializer loads resources once.
    static {
        try {
            defaultNormal = ImageIO.read(Objects.requireNonNull(Buttons.class.getResourceAsStream("/start/buttons/button.png")));
            defaultRollover = ImageIO.read(Objects.requireNonNull(Buttons.class.getResourceAsStream("/start/buttons/button_highlighted.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream fontStream = Buttons.class.getResourceAsStream("/font/minecraftFont.ttf")) {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(fontStream));
            defaultFont = baseFont.deriveFont(Font.BOLD, 18f);
        } catch (Exception e) {
            e.printStackTrace();
            defaultFont = new Font("Arial", Font.BOLD, 18);
        }
    }

    /**
     * Constructor that takes only the button text.
     */
    public Buttons(String text) {
        super(text);
        this.buttonText = text;
        // Prevent the default UI from drawing the text because we'll draw it ourself.
        super.setText("");

        setFont(defaultFont);
        setForeground(Color.WHITE);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        if (defaultNormal != null) {
            BufferedImage scaledNormal = getScaledImage(defaultNormal, (int)(defaultNormal.getWidth() * SCALE_FACTOR),
                    (int)(defaultNormal.getHeight() * SCALE_FACTOR));
            setIcon(new ImageIcon(scaledNormal));
        }
        if (defaultRollover != null) {
            BufferedImage scaledRollover = getScaledImage(defaultRollover, (int)(defaultRollover.getWidth() * SCALE_FACTOR),
                    (int)(defaultRollover.getHeight() * SCALE_FACTOR));
            setRolloverIcon(new ImageIcon(scaledRollover));
        }

        // Set a fixed size based on the scaled normal image's dimensions.
        Dimension fixedSize;
        if (defaultNormal != null) {
            fixedSize = new Dimension((int)(defaultNormal.getWidth() * SCALE_FACTOR),
                    (int)(defaultNormal.getHeight() * SCALE_FACTOR));
        } else {
            fixedSize = new Dimension(800, 80);  // Fallback size
        }
        setPreferredSize(fixedSize);
        setMaximumSize(fixedSize);
    }

    /**
     * Helper method that scales an image using Graphics2D with bilinear interpolation.
     */
    private BufferedImage getScaledImage(BufferedImage src, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(src, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // First let the superclass paint the icon (it doesn't paint our text because we cleared it).
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(buttonText);
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

        // Draw the drop shadow (2 pixels down/right, semi-transparent black)
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.drawString(buttonText, x + 2, y + 2);

        // Draw the actual text
        g2d.setColor(getForeground());
        g2d.drawString(buttonText, x, y);
        g2d.dispose();
    }
}
