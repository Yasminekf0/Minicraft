package view;

import controller.GameController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.*;
import static view.ScreenSettings.scale;

import model.entity.NPC;
import model.entity.Player;
import model.position.WorldPosition;

public class NPCView extends GameElementView{
    // Sprite images
    private BufferedImage up1,  up2, up3, down1, down2, right1, right2, left1, left2;
    private final Player player;
    private final NPC npc;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private double angle = 0;//Math.PI ; // default facing angle


    // Where and how large to draw the player on screen

    public NPCView() {
        this.player = Player.getInstance();
        this.npc = NPC.getInstance();
        loadImages();
    }

    protected void loadImages() {
        try {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_right_2.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/oldman/oldman_up_2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/merchant/merchant_down_2.png")));
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

        int playerWorldX = player.getWorldPos().getX().intValue();
        int playerWorldY = player.getWorldPos().getY().intValue();
        int worldX = npc.getWorldPos().getX().intValue();
        int worldY = npc.getWorldPos().getY().intValue();

        int screenX = worldX - playerWorldX + playerScreenX;
        int screenY = worldY - playerWorldY + playerScreenY;


        if (worldX + tileSize > playerWorldX - playerScreenX &&
            worldX - tileSize < playerWorldX + playerScreenX &&
            worldY + tileSize > playerWorldY - playerScreenY &&
            worldY - tileSize < playerWorldY + playerScreenY) {

            BufferedImage image = switch (spriteNum) {
                case 2 -> right1;
                case 4 -> right2;
                default -> right1;
            };
            if (image == null) return;

            AffineTransform at = createAffineTransform(image, screenX, screenY);
            g2.drawImage(image, at, null);
        }
    }

    private AffineTransform createAffineTransform(BufferedImage image, int screenX, int screenY) {
        AffineTransform at = new AffineTransform();

        // Translate to screen position first
        at.translate(screenX + tileSize / 2.0, screenY + tileSize / 2.0);

        // Rotate around the center
        at.rotate(angle);

        // Scale to fit the tileSize
        double scaleX = tileSize / (double) image.getWidth();
        double scaleY = tileSize / (double) image.getHeight();
        at.scale(scaleX, scaleY);

        // Center the image
        at.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

        return at;
    }

}


