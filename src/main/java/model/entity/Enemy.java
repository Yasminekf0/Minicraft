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
    private CollisionChecker collisionChecker;
    public Enemy(){
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
        this.speed = 5;
        this.health = 10;
        this.maxHealth = 10;
        this.collisionChecker = new CollisionChecker();
        this.solidArea = new Rectangle(1,1,tileSize,tileSize );
    }

    public void moveUntil(double dx, double dy){
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

        /*collisionOn = false;
        int hit = collisionChecker.checkEntity(this, moveDx, moveDy);
        if (hit >= 0) {
            collisionOn = true;
            //if (hit == 0)      interactNPC(hit);
            //else               interactEnemy(hit - 1);
        }*/
        collisionChecker.checkPlayer(this,moveDx, moveDy);

        if (!collisionOn) {
            worldPos.increment(moveDx, moveDy);
        }
    }
}