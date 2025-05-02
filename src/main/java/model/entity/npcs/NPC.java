package model.entity.npcs;

import model.entity.CollisionChecker;
import model.position.WorldPosition;

import java.awt.*;

import static java.lang.Math.round;
import static view.ScreenSettings.tileSize;

public class NPC extends Mob {
    private static NPC instance;
    private final CollisionChecker collisionChecker;
    private boolean alive = true;
    public NPC(){
        instance = this;
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
        this.speed = 10;
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

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }
}
