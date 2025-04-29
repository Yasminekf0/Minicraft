package model.entity;

import model.Inventory;
import model.position.Direction;
import model.position.WorldPosition;
import model.world.MobManager;
import model.world.World;

import java.awt.*;

import static java.lang.Math.round;
import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.scale;
import static view.ScreenSettings.tileSize;

public class Player extends Entity {

    private static Player instance;

    private final Inventory inventory;
    private boolean directionLocked = false;
    private double lockedAngle = Math.PI/2;

    private Player() {

        instance = this;
        this.worldPos = new WorldPosition((tileSize/2.0) + (worldSize*tileSize) /2.0,(tileSize/2.0) + (worldSize*tileSize) /2.0);
        this.speed = 10;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        collisionChecker = new CollisionChecker();
        inventory = new Inventory();
        this.solidArea = new Rectangle(1,1,1,1 );//(tileSize/2, tileSize/2, tileSize/2, tileSize/2); //(8, 8, 16, 16) tilesize=16
        this.solidAreaDefault = new Rectangle(
                solidArea.x,
                solidArea.y,
                solidArea.width,
                solidArea.height
        );
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
        collisionOn = false;
        double moveDx = dx * speed;
        double moveDy = dy * speed;

        if (!directionLocked){
            worldPos.updateDirection(moveDx,moveDy);
        }

        int steps = (int)Math.ceil(Math.max(Math.abs(moveDx), Math.abs(moveDy)));
        // Per‐step increments
        double stepDx = moveDx / steps;
        double stepDy = moveDy / steps;

        Enemy[] enemies = MobManager.getInstance().getEnemies();
        Entity[] targets = new Entity[enemies.length + 1];

        targets[0] = NPC.getInstance();
        System.arraycopy(enemies, 0, targets, 1, enemies.length);

        for (int i = 0; i < steps; i++) {
            collisionChecker.checkTile(this, 0, stepDy);
            if (collisionOn) {
                collisionOn = false;
                stepDy = 0;
            }

            collisionChecker.checkTile(this, stepDx, 0);
            if (collisionOn) {
                collisionOn = false;
                stepDx = 0;
            }


            int hit = collisionChecker.checkEntity(this, stepDx, stepDy, targets);
            if (hit >= 0) {
                collisionOn = true;
                if (hit == 0)       interactNPC(hit);
                else                interactEnemy(hit - 1);
                break;
            }

            worldPos.increment(stepDx, stepDy);
        }

        /*collisionChecker.checkTile(this, moveDx, moveDy);


        int hitIndex = collisionChecker.checkEntity(this, moveDx, moveDy);
        if (hitIndex >= 0) {
            // 0 means NPC, 1 means enemy1, 2→enemy2, 3→enemy3
            if (hitIndex == 0) {
                interactNPC(hitIndex);
            } else {
                interactEnemy(hitIndex - 1);
            }
        }

        if (!collisionOn){
            if (!directionLocked){
                worldPos.updateDirection(moveDx,moveDy);
            }
            worldPos.increment(moveDx, moveDy);
        }*/
    }

    public void interactNPC(int i){
        //if (i!=-1){
            System.out.println('x');
        //}
    }

    public void interactEnemy(int i){
        //if (i!=-1){
            System.out.println('e');
        //}
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