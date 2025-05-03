package model.entity.npcs;

import model.entity.CollisionChecker;
import model.entity.Entity;
import model.position.WorldPosition;

import java.awt.*;
import java.util.Random;

import static view.settings.ScreenSettings.tileSize;

public abstract class Mob extends Entity {


    public boolean skinType;
    public int wanderSteps = new Random().nextInt(100)+100;

    public Mob() {
        super();

        this.skinType = true;
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
        this.collisionChecker = new CollisionChecker();
        this.solidArea = new Rectangle(1,1,tileSize-1,tileSize-1);
    }

    public CollisionChecker getCollisionChecker(){
        return collisionChecker;
    }

    public void moveUntil(double dx, double dy) {
        collisionOn = false;
        double moveDx = dx * speed;
        double moveDy = dy * speed;

        worldPos.updateDirection(moveDx,moveDy);

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

        collisionChecker.checkPlayer(this,moveDx, moveDy);

        if (!collisionOn) {
            worldPos.increment(moveDx, moveDy);
        }

    }
}