package entity;

import main.GamePanel;
import main.KeyInputs;
import world.position.WorldPosition;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Player extends Entity {


    public KeyInputs keyI;





    public Player(GamePanel gp, KeyInputs keyI) {

        super();
        this.gp = gp;
        this.keyI = keyI;

        setScreenX(gp.screenWidth / 2 - (gp.tileSize / 2));
        setScreenY(gp.screenHeight / 2 - (gp.tileSize / 2));

        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues() {

        health = 10;
        maxHealth = 10;

        worldPos = new WorldPosition(gp.worldWidth/2.0, gp.worldHeight/2.0);
        speed = 10;
        setAngle(Math.PI / 2);
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
            double vectorNorm = Math.sqrt(dx * dx + dy * dy);
            double normalizedDx = dx / vectorNorm;
            double normalizedDy = dy / vectorNorm;

            setAngle(Math.atan2(dy, dx));

            worldPos.increment(normalizedDx * speed, normalizedDy * speed);
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
    }
    public KeyInputs getKeyI () {
        return keyI;
    }


}
