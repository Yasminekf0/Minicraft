package model.entity;

import model.Inventory;
import model.position.Direction;
import model.position.WorldPosition;
import model.world.World;

import java.awt.*;

import static java.lang.Math.round;
import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.scale;
import static view.ScreenSettings.tileSize;

public class Player extends Entity {

    private static Player instance;

    private final Inventory inventory;
    private CollisionChecker collisionChecker;
    private boolean directionLocked = false;
    private double lockedAngle = Math.PI/2;

    private Player() {
        instance = this;
        this.worldPos = new WorldPosition((tileSize/2.0) + (worldSize*tileSize) /2.0,(tileSize/2.0) + (worldSize*tileSize) /2.0);
        //getSpawnPos();
        this.speed = 10;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        collisionChecker = new CollisionChecker();
        inventory = new Inventory();
        solidArea = new Rectangle(0,0,0,0);//(tileSize/2, tileSize/2, tileSize/2, tileSize/2); //(8, 8, 16, 16)

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
        collisionOn = false;
        double moveDx = dx * speed;
        double moveDy = dy * speed;

        collisionChecker.checkTile(this, moveDx, moveDy);

        if (!collisionOn){
            if (!directionLocked){
                worldPos.updateDirection(moveDx,moveDy);
            }
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
}