package entity;

import main.GamePanel;
import world.position.ScreenPosition;
import world.position.WorldPosition;

import java.awt.image.BufferedImage;

public class Entity {

    WorldPosition worldPos;
    ScreenPosition screenPos;

    int speed;
    int health;
    int maxHealth;

    GamePanel gp;

    double angle;

    BufferedImage rightstand, rightwalk1, rightwalk2, rightdo;


    int spriteCounter = 0;
    int spriteNum = 1;


    public WorldPosition getWorldPos() {
        return worldPos;
    }

    public void setWorldPos(WorldPosition worldPos) {
        this.worldPos = worldPos;
    }

    public int getScreenX() {
        return screenPos.getX();
    }


    public int getScreenY() {
        return screenPos.getY();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getSpeed(){
        return speed;
    }
}
