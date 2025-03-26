package entity;

import world.position.WorldPosition;

import java.awt.image.BufferedImage;

public class Entity {

    protected WorldPosition worldPos;
    public int speed;

    public BufferedImage upstand, upwalk1, upwalk2, updo, rightstand, rightwalk1, rightwalk2, rightdo, downstand, downwalk1, downwalk2, downdo, leftstand, leftwalk1, leftwalk2, leftdo;
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
