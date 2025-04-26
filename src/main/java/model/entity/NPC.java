package model.entity;

import model.position.WorldPosition;
import model.world.World;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.round;
import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.scale;
import static view.ScreenSettings.tileSize;

public class NPC extends Mob {
    private static NPC instance;
    private CollisionChecker collisionChecker;
    private final World world;
    public NPC(){
        instance = this;
        this.world = World.getInstance();
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
        this.speed = 15;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        this.collisionChecker = new CollisionChecker();
        this.solidArea = new Rectangle(0,0,0,0);
    }


    public static NPC getInstance() {
        if (instance == null) {
            instance = new NPC();
        }
        return instance;
    }

    public void moveUntil(double dx, double dy) {
        collisionOn = false;
        double moveDx = dx * speed;
        double moveDy = dy * speed;

        collisionChecker.checkTile(this, moveDx, moveDy);

        if (!collisionOn){
            worldPos.updateDirection(moveDx,moveDy);
            worldPos.increment(moveDx, moveDy);
        }
    }
}
