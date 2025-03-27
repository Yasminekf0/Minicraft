package entity;

import main.GamePanel;
import world.position.WorldPosition;

import java.awt.image.BufferedImage;

public class Entity {

    protected WorldPosition worldPos;
    public int speed;
    public int health;
    public int maxHealth;

    public GamePanel gp;

    private int screenX;
    private int screenY;
    private double angle;

    protected BufferedImage rightstand, rightwalk1, rightwalk2, rightdo;


    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int getSpriteNum() {
        return spriteNum;
    }


    public BufferedImage getRightstand() {
        return rightstand;
    }

    public BufferedImage getRightwalk1() {
        return rightwalk1;
    }

    public BufferedImage getRightwalk2() {
        return rightwalk2;
    }

    public BufferedImage getRightdo() {
        return rightdo;
    }


    public WorldPosition getWorldPos() {
        return worldPos;
    }

    public void setWorldPos(WorldPosition worldPos) {
        this.worldPos = worldPos;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
