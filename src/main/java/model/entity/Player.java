package model.entity;

import model.items.Inventory;
import model.position.Direction;
import model.position.WorldPosition;

import java.io.Serializable;

import java.awt.*;

import static model.world.WorldSettings.worldSize;
import static view.settings.ScreenSettings.scale;
import static view.settings.ScreenSettings.tileSize;

public class Player extends Entity implements Serializable {

    private static Player instance;

    private final Inventory inventory;
    private boolean directionLocked = false;
    private double lockedAngle = Math.PI/2;
    private long lastDamageTime = 0;
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
        this.solidArea = new Rectangle(1,1,1,1 );//(tileSize/2, tileSize/2, tileSize/2, tileSize/2); //(8, 8, 16, 16) tilesize=16
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
        if (now - lastDamageTime < DAMAGE_COOLDOWN) {
            return;
        }

        collisionOn = false;
        double moveDx = dx * speed;
        double moveDy = dy * speed;

        if (!directionLocked){
            worldPos.updateDirection(moveDx,moveDy);
        }
        collisionOn = false;
        collisionChecker.checkTile(this, moveDx, 0);
        if (collisionOn) {
            moveDx = 0;
        }

        collisionOn = false;
        collisionChecker.checkTile(this, 0, moveDy);
        if (collisionOn) {
            moveDy = 0;
        }

        collisionOn = false;
        int hit = collisionChecker.checkEntity( moveDx, moveDy);
        if (hit >= 0) {
            collisionOn = true;
        }

        if (!collisionOn) {
            worldPos.increment(moveDx, moveDy);
        }
    }

    public void use(){
        if (inventory.getSelectedItem() != null) {
            inventory.getSelectedItem().use();
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

    public int getDefaultSpeed() {
        return defaultSpeed;
    }
}