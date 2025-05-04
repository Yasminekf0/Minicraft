package model.entity;

import model.items.Inventory;
import model.position.Direction;
import model.position.WorldPosition;

import java.io.Serializable;

import java.awt.*;

import static model.world.WorldSettings.worldSize;
import static view.settings.ScreenSettings.tileSize;

public class Player extends Entity implements Serializable {

    private static Player instance;

    private final Inventory inventory;
    private boolean directionLocked = false;
    private double lockedAngle = Math.PI/2;

    // Cooldown time in milliseconds to prevent repeated damage.
    private static final long DAMAGE_COOLDOWN = 1000;

    private Player() {
        instance = this;
        this.worldPos = new WorldPosition((tileSize/2.0) + (worldSize*tileSize) /2.0,(tileSize/2.0) + (worldSize*tileSize) /2.0);
        this.speed = 10;
        this.defaultSpeed = 10;
        this.health = 10;
        this.maxHealth = 10;
        collisionChecker = new CollisionChecker();
        inventory = new Inventory();
        // Defines the collision boundary box.
        this.solidArea = new Rectangle(1,1,1,1 );
        // Makes the player spawn in a place with no collisions
        collisionChecker.getSpawnPos(this);

    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public static void setInstance(Player player) {
        Player.instance = player;
    }

    public Inventory getInventory() { return inventory; }

    public void moveUntil(double dx, double dy) {
        long now = System.currentTimeMillis();
        long lastDamageTime = 0;
        if (now - lastDamageTime < DAMAGE_COOLDOWN) {
            return;
        }

        double moveDx = dx * speed;
        double moveDy = dy * speed;

        if (!directionLocked){
            worldPos.updateDirection(moveDx,moveDy);
        }

        // Check for tile collisions in X and Y directions, that way, if walking diagonally, if one direction is free, you can continue
        if (collisionChecker.checkTile(this, moveDx, 0)) {
            moveDx = 0;
        }


        if (collisionChecker.checkTile(this, 0, moveDy)) {
            moveDy = 0;
        }

        if (!collisionChecker.checkEntity( moveDx, moveDy)) {
            worldPos.increment(moveDx, moveDy);
        }
    }

    public void lockDirection(double currentAngle){
        this.lockedAngle = currentAngle;
        this.directionLocked = true;
    }

    public void unlockDirection(){
        directionLocked = false;
    }

    public double getFacingAngle() {
        if (directionLocked) return lockedAngle;

        Direction d = worldPos.getDirectionFacing();
        return Math.atan2(d.getY(), d.getX());
    }

    public void use(){
        inventory.getSelectedItem().use();
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }
}