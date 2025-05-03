package view.game.elements;

import model.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.settings.ScreenSettings.playerScreenX;
import static view.settings.ScreenSettings.playerScreenY;
import static view.settings.ScreenSettings.tileSize;

import model.entity.npcs.Enemy;
import model.entity.npcs.MobManager;

@SuppressWarnings("FieldCanBeLocal")
public class EnemyView extends GameElementView{
        // Sprite images
        private BufferedImage  z, z1, z2, sk1, sk2, sk, v, v1, v2;
        private final Player player;
        private final Enemy[] enemies;
        private int spriteCounter = 0;
        private int spriteNum;
        private final double angle = 0;
        private final double[] angles = new double[3];
        //private double angles[0] = 0;
        //this.enemies[1] = Enemy.getInstance();


    public EnemyView() {
            this.player = Player.getInstance();
            this.enemies = MobManager.getInstance().getEnemies();

            loadImages();
        }
        public Enemy[ ] getEnemies() {
            return enemies;
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



                        BufferedImage image;

                        if (enemies[i].getSkinType()) {
                            image = switch (spriteNum) {
                                case 2 -> z1;
                                case 4 -> z2;
                                default -> z;
                            };
                        } else { // skeleton
                            image = switch (spriteNum) {
                                case 2 -> sk1;
                                case 4 -> sk2;
                                default -> sk;
                            };
                        }
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
