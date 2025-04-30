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
    public int wanderSteps = new Random().nextInt(100)+100;
    public int pathStage = new Random().nextInt(8);
    private int lastGoalCol = -1, lastGoalRow = -1;
    public Enemy(){
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
        this.speed = 5;
        this.health = 10;
        this.maxHealth = 10;
        this.collisionChecker = new CollisionChecker();
        this.solidArea = new Rectangle(1,1,tileSize,tileSize );

    }

    private void up()    { this.moveUntil( 0, -1); }
    private void down()  { this.moveUntil( 0,  1); }
    private void left()  { this.moveUntil(-1,  0); }
    private void right() { this.moveUntil( 1,  0); }

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

    /*public void searchPath(int goalCol, int goalRow) {
        // 1) If same goal, and we still have waypoints, just return
        if (goalCol == lastGoalCol && goalRow == lastGoalRow
                && !pFinder.pathList.isEmpty()) {
            return;
        }

        // 2) Otherwise, re-run A*
        lastGoalCol = goalCol;
        lastGoalRow = goalRow;
        pFinder.pathList.clear();
        pFinder.search(
                getWorldPos().getTileXPos(), getWorldPos().getTileYPos(),
                goalCol, goalRow
        );
    }



    public void update(){
        super.update();

        int xD = Math.abs(ent.worldX-player.worldX);
        int yD = Math.abs(ent.worldY-player.worldY);

        int tileD = (xD+yD)/tileSize;
        if (!onPath && tileD <6){
            onPath = true;
        } else if(onPath && tileD > 20){
            onPath = false;
        }
    }*/
}