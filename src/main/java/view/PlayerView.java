package view;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.ScreenSettings.*;

/**
 * Handles all rendering/animation logic for the Player, including
 * sprite switching and rotation, separate from the model.
 */
public class PlayerView extends GameElementView {


    // Sprite images
    private BufferedImage stand, walk1, walk2, use;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private double angle = Math.PI / 2; // default facing angle

    // Where and how large to draw the player on screen

    public PlayerView() {
        loadImages();
    }

    protected void loadImages() {
        try {
            stand = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand.png")));
            walk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk1.png")));
            walk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/walk2.png")));
            use = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/use.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates animation frames and rotation.
     * @param moving   true if the player is moving, false otherwise
     * @param newAngle the angle (in radians) representing the player's direction
     */
    public void update(boolean moving, double newAngle) {

        if (moving) {
            this.angle = newAngle;
            spriteCounter++;
            // Increase or decrease this threshold to adjust animation speed
            if (spriteCounter > 11) {
                spriteNum++;
                if (spriteNum > 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1; // Reset to standing when not moving
        }
    }

    /**
     * Draws the player sprite with rotation and scaling.
     */
    public void draw(Graphics2D g2) {
        BufferedImage image;
        switch (spriteNum) {
            case 2:  image = walk1; break;
            case 4:  image = walk2; break;
            case 1:
            case 3:
            default: image = stand; break;
        }
        if (image == null) return;

        AffineTransform at = createAffineTransform(image);
        g2.drawImage(image, at, null);
    }

    private AffineTransform createAffineTransform(BufferedImage image) {
        AffineTransform at = new AffineTransform();

        // Translate to the player's screen position
        at.translate(playerScreenX, playerScreenY);

        // Rotate around the sprite center
        at.rotate(angle);

        // Scale the sprite to tileSize
        at.scale(scale, scale);

        // Center the rotation on the sprite
        at.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

        return at;
    }
}
