package view;

import model.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Handles all rendering/animation logic for the Player, including
 * sprite switching and rotation, separate from the model.
 */
public class PlayerView {
    private Player player;

    // Sprite images
    private BufferedImage stand, walk1, walk2, use;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private double angle = Math.PI / 2; // default facing angle

    // Where and how large to draw the player on screen
    private int tileSize;
    private int screenX;
    private int screenY;

    public PlayerView(Player player, int tileSize, int screenX, int screenY) {
        this.player = player;
        this.tileSize = tileSize;
        this.screenX = screenX;
        this.screenY = screenY;
        loadPlayerImages();
    }

    private void loadPlayerImages() {
        try {
            stand = ImageIO.read(getClass().getResourceAsStream("/player/stand.png"));
            walk1 = ImageIO.read(getClass().getResourceAsStream("/player/walk1.png"));
            walk2 = ImageIO.read(getClass().getResourceAsStream("/player/walk2.png"));
            use = ImageIO.read(getClass().getResourceAsStream("/player/use.png"));
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
        this.angle = newAngle;

        if (moving) {
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
        at.translate(screenX, screenY);

        // Rotate around the sprite center
        at.rotate(angle);

        // Scale the sprite to tileSize
        double scaleX = (double) tileSize / image.getWidth();
        double scaleY = (double) tileSize / image.getHeight();
        at.scale(scaleX, scaleY);

        // Center the rotation on the sprite
        at.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

        return at;
    }
}
