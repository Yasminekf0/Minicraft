package view;

import model.entity.NPC;
import model.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.ScreenSettings.*;
import static view.ScreenSettings.playerScreenX;
import static view.ScreenSettings.playerScreenY;
import static view.ScreenSettings.tileSize;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.*;
import static view.ScreenSettings.scale;

import model.entity.Enemy;
import model.entity.Player;
import model.position.WorldPosition;

public class EnemyView extends GameElementView{
        // Sprite images
        private BufferedImage up1,  up2, up3,up4,up5, down1, down2, right1, right2, left1, left2, z, z1, z2, sk1, sk2, sk, v, v1, v2;
        private final Player player;
        private final Enemy[] enemies; //***************************************
        private int spriteCounter = 0;
        private int spriteNum;
        private double angle = 0;
        private final double[] angles = new double[3];
        //private double angles[0] = 0;

        //this.enemies[1] = Enemy.getInstance();
        //this.enemies[2] = Enemy.getInstance();


    public EnemyView() {
            this.player = Player.getInstance();
            this.enemies = new Enemy[3]; //***********************************
            this.enemies[0] = new Enemy();
            this.enemies[1] = new Enemy();
            this.enemies[2] = new Enemy();

            loadImages();
        }
        public Enemy[ ] getEnemies() {
            return enemies;
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
                v = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager.png")));
                v2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager2.png")));
                v1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/villager/villager1.png")));
                z = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie.png")));
                z2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie2.png")));
                z1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/zombie/zombie1.png")));
                sk = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton.png")));
                sk2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton2.png")));
                sk1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/skeleton/skeleton1.png")));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Updates animation frames and rotation.
         * @param moving   true if the player is moving, false otherwise
         * @param newAngle the angle (in radians) representing the player's direction
         */
        public void update(int index, boolean moving, double newAngle) {

            if (moving) {
                //this.angle = newAngle;
                angles[index] = newAngle;
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

        /**
         * Draws the player sprite with rotation and scaling.
         */
        public void draw(Graphics2D g2) {
            for(int i = 0; i < this.enemies.length; ++i) {
                if (this.enemies[i] != null) {
                    int playerWorldX = player.getWorldPos().getX().intValue();
                    int playerWorldY = player.getWorldPos().getY().intValue();
                    int worldX = enemies[i].getWorldPos().getX().intValue();
                    int worldY = enemies[i].getWorldPos().getY().intValue();

                    int screenX = worldX - playerWorldX + playerScreenX;
                    int screenY = worldY - playerWorldY + playerScreenY;


                    if (worldX + tileSize > playerWorldX - playerScreenX &&
                            worldX - tileSize < playerWorldX + playerScreenX &&
                            worldY + tileSize > playerWorldY - playerScreenY &&
                            worldY - tileSize < playerWorldY + playerScreenY) {

                        BufferedImage image = switch (spriteNum) {
                            case 2 -> z1;
                            case 4 -> z2;
                            default -> z;
                        };
                        if (image == null) return;

                        AffineTransform at = createAffineTransform(image, screenX, screenY, angles[i]);
                        g2.drawImage(image, at, null);
                    }
                }
            }

        }

        private AffineTransform createAffineTransform(BufferedImage image, int screenX, int screenY,  double rotationangle) {
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
