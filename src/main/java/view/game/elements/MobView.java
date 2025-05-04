package view.game.elements;

import model.entity.Player;
import model.entity.mobs.Mob;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static view.settings.ScreenSettings.*;

public abstract class MobView implements GameElementView{
    //private BufferedImage z, z1, z2, sk1, sk2, sk, v, v1, v2, x;

    protected final Map<Integer,BufferedImage[]> spriteMap = new HashMap<>();
    private final Player player;
    private int spriteCounter = 0;
    private int spriteNum;
    protected double[] MobsAngles;
    protected Mob[] mobs;
    public MobView() {
        this.player = Player.getInstance();
        loadImages();
    }

    protected abstract void loadImages();


    public void update(int index, boolean moving, double newAngle) {

        if (moving) {
            MobsAngles[index] = newAngle;
            spriteCounter++;
            if (spriteCounter > 40) {
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
        for(int i = 0; i < this.mobs.length; ++i) {
            if (this.mobs[i] != null) {
                int playerWorldX = player.getWorldPos().getXInt();
                int playerWorldY = player.getWorldPos().getYInt();
                int worldX = mobs[i].getWorldPos().getXInt();
                int worldY = mobs[i].getWorldPos().getYInt();

                int screenX = worldX - playerWorldX + playerScreenX;
                int screenY = worldY - playerWorldY + playerScreenY;


                if (worldX + tileSize > playerWorldX - playerScreenX &&
                        worldX - tileSize < playerWorldX + playerScreenX &&
                        worldY + tileSize > playerWorldY - playerScreenY &&
                        worldY - tileSize < playerWorldY + playerScreenY) {

                    BufferedImage image;

                    BufferedImage[] images = spriteMap.get(mobs[i].getSkinType());

                    image = switch (spriteNum) {
                        case 2 -> images[1];
                        case 4 -> images[2];
                        default -> images[0];
                    };


                    if (image == null) return;

                    AffineTransform at = createAffineTransform(image, screenX, screenY, MobsAngles[i]);
                    g2.drawImage(image, at, null);
                }
            }
        }

    }





    protected AffineTransform createAffineTransform(BufferedImage image, int screenX, int screenY, double rotationangle) {
        AffineTransform at = new AffineTransform();

        // Translate to screen position first
        at.translate(screenX + tileSize / 2.0, screenY + tileSize / 2.0);

        // Rotate around the center
        at.rotate(rotationangle);

        // Scale to fit the tileSize
        double scaleX = tileSize / (double) image.getWidth();
        double scaleY = tileSize / (double) image.getHeight();
        at.scale(scaleX, scaleY);

        // Center the image
        at.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

        return at;
    }
}
