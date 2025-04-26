package model.entity;
import controller.GameController;
import model.position.WorldPosition;

import java.awt.*;

public abstract class Entity {
    protected WorldPosition worldPos;

    protected int speed;
    protected int health;
    protected int maxHealth;
    protected Rectangle solidArea;
    public boolean collisionOn;

    public WorldPosition getWorldPos() {
        return worldPos;
    }

    public void setWorldPos(WorldPosition worldPos) {
        this.worldPos = worldPos;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void setSpeed(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public void heal(int healAmount) {
        this.health = Math.min(maxHealth, this.health + healAmount);
    }

}
