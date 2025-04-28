package model.entity;

import model.position.WorldPosition;
import model.world.World;

import java.awt.*;

import static view.ScreenSettings.tileSize;

import java.util.Random;

import static java.lang.Math.round;
import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.scale;

public class Enemy extends Mob {
    private static Enemy instance;
    private CollisionChecker collisionChecker;
    private final World world;
    public Enemy(){
        instance = this;
        this.world = World.getInstance();
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
        this.speed = 5;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        this.collisionChecker = new CollisionChecker();
        this.solidArea = new Rectangle(1,1,tileSize,tileSize );
        this.solidAreaDefault = new Rectangle(
                solidArea.x,
                solidArea.y,
                solidArea.width,
                solidArea.height
        );
    }


    public static Enemy getInstance() {
        if (instance == null) {
            instance = new Enemy();
        }
        return instance;
    }

    public void moveUntil(double dx, double dy) {
        collisionOn = false;
        double moveDx = dx * speed;
        double moveDy = dy * speed;
        collisionChecker.checkTile(this, moveDx, moveDy);
        collisionChecker.checkPlayer(this,moveDx, moveDy);

        if (!collisionOn){
            worldPos.updateDirection(moveDx,moveDy);
            worldPos.increment(moveDx, moveDy);
        }
    }
}