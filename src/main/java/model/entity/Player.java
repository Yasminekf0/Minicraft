package model.entity;

import model.position.WorldPosition;

public class Player extends Entity {

    public Player(int startX, int startY) {
        this.worldPos = new WorldPosition(startX, startY);
        this.speed = 5;
        this.health = 10;
        this.maxHealth = 10;
    }

    /**
     * Move the player by (dx, dy), scaled by speed.
     */
    public void move(double dx, double dy) {
        worldPos.increment(dx * speed, dy * speed);
    }

    /**
     * If you want the player to do any autonomous updates, place them here.
     */
    public void update() {
        // For autonomous updates if needed
    }
}
