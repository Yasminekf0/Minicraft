package entity;

import main.GamePanel;
import main.KeyInputs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyInputs keyI;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyInputs keyI) {

        this.gp = gp;
        this.keyI = keyI;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        setDefaultValues();
        getPlayerImage();

    }
    public void setDefaultValues() {

        worldX = gp.tileSize*20;
        worldY = gp.tileSize*20;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            upstand = ImageIO.read(getClass().getResource("/player/upstand.png"));
            upwalk1 = ImageIO.read(getClass().getResource("/player/upwalk1.png"));
            upwalk2 = ImageIO.read(getClass().getResource("/player/upwalk2.png"));
            updo = ImageIO.read(getClass().getResource("/player/updo.png"));

            rightstand = ImageIO.read(getClass().getResource("/player/rightstand.png"));
            rightwalk1 = ImageIO.read(getClass().getResource("/player/rightwalk1.png"));
            rightwalk2 = ImageIO.read(getClass().getResource("/player/rightwalk2.png"));
            rightdo = ImageIO.read(getClass().getResource("/player/rightdo.png"));

            downstand = ImageIO.read(getClass().getResource("/player/downstand.png"));
            downwalk1 = ImageIO.read(getClass().getResource("/player/downwalk1.png"));
            downwalk2 = ImageIO.read(getClass().getResource("/player/downwalk2.png"));
            downdo = ImageIO.read(getClass().getResource("/player/downdo.png"));

            leftstand = ImageIO.read(getClass().getResource("/player/leftstand.png"));
            leftwalk1 = ImageIO.read(getClass().getResource("/player/leftwalk1.png"));
            leftwalk2 = ImageIO.read(getClass().getResource("/player/leftwalk2.png"));
            leftdo = ImageIO.read(getClass().getResource("/player/leftdo.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public void update() {
        boolean moving = false;
        boolean Do = false;

        if (keyI.upPressed) {
            direction = "up";
            worldY -= speed;
            moving = true;
        }
        else if (keyI.downPressed) {
            direction = "down";
            worldY += speed;
            moving = true;
        }
        else if (keyI.leftPressed) {
            direction = "left";
            worldX -= speed;
            moving = true;
        }
        else if (keyI.rightPressed) {
            direction = "right";
            worldX += speed;
            moving = true;
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
    public void draw(Graphics g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1 || spriteNum == 3) {
                    image = upstand;
                }
                if (spriteNum == 2){
                    image = upwalk1;
                }
                if (spriteNum == 4){
                    image = upwalk2;
                }
                break;
            case "down":
                if (spriteNum == 1 || spriteNum == 3) {
                    image = downstand;
                }
                if (spriteNum == 2){
                    image = downwalk1;
                }
                if (spriteNum == 4){
                    image = downwalk2;
                }
                break;
            case "left":
                if (spriteNum == 1 || spriteNum == 3) {
                    image= leftstand;
                }
                if (spriteNum == 2){
                    image= leftwalk1;
                }
                if (spriteNum == 4){
                    image= leftwalk2;
                }
                break;
            case "right":
                if (spriteNum == 1 || spriteNum == 3) {
                    image= rightstand;
                }
                if (spriteNum == 2){
                    image= rightwalk1;
                }
                if (spriteNum == 4){
                    image= rightwalk2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
