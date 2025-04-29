package view;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.ScreenSettings.*;

public class PlayerView extends GameElementView {


    // Sprite images
    private BufferedImage stand, walk1, walk2, use;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private double angle = Math.PI / 2;

    SoundManager soundManager = SoundManager.getInstance();// default facing angle

    private boolean usingAction = false;

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

    public void update(boolean moving, double newAngle) {
        if (usingAction) {
            return;
        }

        if (moving) {
            this.angle = newAngle;
            spriteCounter++;
            // Increase or decrease this threshold to adjust animation speed
            if (spriteCounter > 11) {
                spriteNum++;
                SoundManager.getInstance().playSound("footstep");
                if (spriteNum > 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1; // Reset to standing when not moving
        }
    }

    public void startUse() {
        usingAction = true;
        spriteNum = 1;
        spriteCounter = 0;
    }

    public void stopUse() {
        usingAction = false;
        spriteNum = 1;
        spriteCounter = 0;
    }

    // Draw Sprite with Rotation and Scaling
    public void draw(Graphics2D g2) {
        BufferedImage image;
        if (usingAction) {
            image = use;
        } else {
            image = switch (spriteNum) {
                case 2 -> walk1;
                case 4 -> walk2;
                default -> stand;
            };
        }
        if (image == null) return;

        AffineTransform at = createAffineTransform(image);
        g2.drawImage(image, at, null);
    }

    private AffineTransform createAffineTransform(BufferedImage image) {
        AffineTransform at = new AffineTransform();

        at.translate(playerScreenX, playerScreenY);
        at.rotate(angle);

        at.scale(scale, scale);

        at.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

        return at;
    }
}
