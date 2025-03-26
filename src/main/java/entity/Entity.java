package entity;

import world.position.WorldPosition;

import java.awt.image.BufferedImage;

public class Entity {

    protected WorldPosition worldPos;
    public int speed;

    public BufferedImage rightstand, rightwalk1, rightwalk2, rightdo;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public WorldPosition getWorldPos() {
        return worldPos;
    }

    public void setWorldPos(WorldPosition worldPos) {
        this.worldPos = worldPos;
    }
}
