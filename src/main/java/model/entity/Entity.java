package model.entity;
import model.position.WorldPosition;
import view.audio.SoundManager;

import java.awt.*;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected WorldPosition worldPos;

    protected int speed;
    protected int defaultSpeed;
    protected int health;
    protected int maxHealth;
    protected CollisionChecker collisionChecker;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public boolean onPath = false;


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
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
        SoundManager.getInstance().playSound("damage");
    }

    public void heal(int healAmount) {
        this.health = Math.min(maxHealth, this.health + healAmount);
    }
}
