package entity;

import main.GamePanel;
import main.KeyInputs;
import world.position.WorldPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    public GamePanel gp;
    public KeyInputs keyI;

    public final int screenX;
    public final int screenY;

    private double angle;

    public Player(GamePanel gp, KeyInputs keyI) {

        super();
        this.gp = gp;
        this.keyI = keyI;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();
        getPlayerImage();

    }
    public void setDefaultValues() {

        health = 10;
        maxHealth = 10;

        worldPos = new WorldPosition(gp.tileSize*20, gp.tileSize*20);
        speed = 4;
        angle = Math.PI / 2;
    }

    public void getPlayerImage() {
        try {

            rightstand = ImageIO.read(getClass().getResource("/player/rightstand.png"));
            rightwalk1 = ImageIO.read(getClass().getResource("/player/rightwalk1.png"));
            rightwalk2 = ImageIO.read(getClass().getResource("/player/rightwalk2.png"));
            rightdo = ImageIO.read(getClass().getResource("/player/rightdo.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        boolean moving = false;
        boolean Do = false;

        double dx = 0;
        double dy = 0;

        if (keyI.upPressed) {
            dy = -speed;
            moving = true;
        }
        if (keyI.downPressed) {
            dy = speed;
            moving = true;
        }
        if (keyI.leftPressed) {
            dx = -speed;
            moving = true;
        }
        if (keyI.rightPressed) {
            dx = speed;
            moving = true;
        }

        if (moving) {
            double vectorNorm = Math.sqrt(dx*dx + dy*dy);
            double normalizedDx = dx / vectorNorm;
            double normalizedDy = dy / vectorNorm;

            angle = Math.atan2(dy, dx);
            System.out.println(angle);

            worldPos.increment(normalizedDx*speed, normalizedDy*speed);
        }

        if (moving) {
            spriteCounter++;
            if (spriteCounter > 11) { // Speed of changing frame
                spriteNum++;
                if (spriteNum > 4) {  // Cycle through frames 1-4
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;  // Reset to standing when not moving
        }

    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (spriteNum == 1 || spriteNum == 3) {
            image= rightstand;
        }
        if (spriteNum == 2){
            image= rightwalk1;
        }
        if (spriteNum == 4){
            image= rightwalk2;
        }

        assert image != null;
        AffineTransform at = createAffineTransform(image);

        g2.drawImage(image, at, null);

    }

    private AffineTransform createAffineTransform(BufferedImage image){
        AffineTransform at = new AffineTransform();

        // Move the object to the center
        at.translate(screenX, screenY);

        // Rotate it
        at.rotate(angle);

        // Scale it
        at.scale((double) gp.tileSize / image.getWidth(), (double) gp.tileSize / image.getWidth());

        // Make the object rotate around the center
        at.translate((double) -image.getWidth(null) / 2, (double) -image.getHeight(null) / 2);

        return at;
    }

    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int new_health) {
        this.health = new_health;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public void heal(int healAmount) {
        this.health = Math.min(maxHealth, this.health + healAmount);
    public KeyInputs getKeyI() {
        return keyI;
    }
}
