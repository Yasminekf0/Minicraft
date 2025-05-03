package view.game.elements;

import model.entity.Player;
import model.entity.npcs.Enemy;
import model.entity.npcs.Mob;
import model.entity.npcs.MobManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.settings.ScreenSettings.*;
import static view.settings.ScreenSettings.playerScreenX;
import static view.settings.ScreenSettings.playerScreenY;

public abstract class MobView extends GameElementView{
    private BufferedImage z, z1, z2, sk1, sk2, sk, v, v1, v2, x;
    private final Player player;
    private int spriteCounter = 0;
    private int spriteNum;
    protected double[] MobsAngles;
    protected Mob[] mobs;
    public MobView() {
        this.player = Player.getInstance();
        loadImages();

    }

    protected void loadImages() {
        try {
            v = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager.png")));
            v2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager2.png")));
            v1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager1.png")));
            z = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie.png")));
            z2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie2.png")));
            z1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie1.png")));
            sk = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton.png")));
            sk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton2.png")));
            sk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton1.png")));
            x = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/bat/bat_down_1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

                    image = switch (spriteNum) {
                        case 2 -> x;
                        case 4 -> x;
                        default -> x;
                    };

                    if (mobs[i].getSkinType() == 0) {
                        image = switch (spriteNum) {
                            case 2 -> v1;
                            case 4 -> v2;
                            default -> v;
                        };

                    } else if (mobs[i].getSkinType() == 1) {
                        image = switch (spriteNum) {
                            case 2 -> z1;
                            case 4 -> z2;
                            default -> z;
                        };
                    } else if (mobs[i].getSkinType() == 2 ) { // skeleton
                        image = switch (spriteNum) {
                            case 2 -> sk1;
                            case 4 -> sk2;
                            default -> sk;
                        };
                    }

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
