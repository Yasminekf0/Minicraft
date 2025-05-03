package view.game.elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.settings.ScreenSettings.*;

import model.entity.npcs.NPC;
import model.entity.Player;

@SuppressWarnings("FieldCanBeLocal")
public class NPCView extends GameElementView{
    // Sprite images
    private BufferedImage v, v1, v2;
    private final Player player;
    private final NPC npc;
    private int spriteCounter = 0;
    private int spriteNum;
    private double angle = 0;

    public NPCView() {
        this.player = Player.getInstance();
        this.npc = NPC.getInstance();
        loadImages();
    }

    protected void loadImages() {
        try {
            v = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager.png")));
            v2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager2.png")));
            v1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager1.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(boolean moving, double newAngle) {

        if (moving) {
            this.angle = newAngle;
            spriteCounter++;
            // Increase or decrease this threshold to adjust animation speed
            if (spriteCounter > 19) {
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

    public void draw(Graphics2D g2) {
        if (!npc.isAlive()) {
            return;
        }

        int playerWorldX = player.getWorldPos().getXInt();
        int playerWorldY = player.getWorldPos().getYInt();
        int worldX = npc.getWorldPos().getXInt();
        int worldY = npc.getWorldPos().getYInt();

        int screenX = worldX - playerWorldX + playerScreenX;
        int screenY = worldY - playerWorldY + playerScreenY;


        if (worldX + tileSize > playerWorldX - playerScreenX &&
            worldX - tileSize < playerWorldX + playerScreenX &&
            worldY + tileSize > playerWorldY - playerScreenY &&
            worldY - tileSize < playerWorldY + playerScreenY) {

            BufferedImage image = switch (spriteNum) {
                case 2 -> v1;
                case 4 -> v2;
                default -> v;
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


