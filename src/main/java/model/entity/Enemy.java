package model.entity;

import model.Node;
import model.Pathfinder;
import model.position.WorldPosition;
import model.world.World;

import java.awt.*;

import static view.ScreenSettings.tileSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;
import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.scale;

public class Enemy extends Mob {
    public CollisionChecker collisionChecker;
    public int wanderSteps = new Random().nextInt(100)+100;
    public int pathStage = new Random().nextInt(8);
    private int lastGoalCol = -1, lastGoalRow = -1;
    public Pathfinder pFinder =  new Pathfinder();
    public double dx, dy;
    public Enemy(){
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
        this.speed = (1+1);
        this.health = 10;
        this.maxHealth = 10;
        this.dx = 0;
        this.dy = 1;
        this.collisionChecker = new CollisionChecker();
        this.solidArea = new Rectangle(1,1,tileSize,tileSize );

    }


    public void moveUntil(double dx, double dy){
        double moveDx = dx * speed;
        double moveDy = dy * speed;

        //worldPos.updateDirection(moveDx,moveDy);
        //System.out.println("CO:" + collisionOn +"Collided:" + collided);

        /*int hit = collisionChecker.checkEntity(this, moveDx, moveDy);
        if (hit >= 0) {
            collisionOn = true;
        }*/
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


    public List<Node> searchPath(int goalCol, int goalRow) {
        /*if (onPath
                && goalCol == lastGoalCol
                && goalRow == lastGoalRow
                && !pFinder.pathList.isEmpty()) {
            return Math.atan2(dy, dx);
        }
        lastGoalCol = goalCol;
        lastGoalRow = goalRow;*/

        int startCol = (this.getWorldPos().getX().intValue()+ this.solidArea.x)/tileSize;
        int startRow = (this.getWorldPos().getY().intValue()+ this.solidArea.y)/tileSize;
        pFinder.setNode(startCol, startRow, goalCol, goalRow);
        if (pFinder.search() && !pFinder.pathList.isEmpty()){
            return pFinder.pathList;
            /*int nextX = pFinder.pathList.get(0).col*tileSize;
            int nextY = pFinder.pathList.get(0).row*tileSize;


            int entityLeftWorldX = this.getWorldPos().getX().intValue()+ this.solidArea.x;
            int entityRightWorldX = this.getWorldPos().getX().intValue()+ this.solidArea.x + this.solidArea.width;
            int entityTopWorldY = this.getWorldPos().getY().intValue()+ this.solidArea.y;
            int entityBottomWorldY = this.getWorldPos().getY().intValue()+ this.solidArea.y + this.solidArea.height;

            if (entityTopWorldY>nextY && entityLeftWorldX >= nextX && entityRightWorldX < nextX+tileSize) {
                this.moveUntil( 0, -1); //up
            } else if (entityTopWorldY<nextY && entityLeftWorldX >= nextX && entityRightWorldX < nextX+tileSize) {
                this.moveUntil( 0,  1); //down

            } else if (entityTopWorldY>=nextY && entityBottomWorldY<nextY+tileSize) {
                if (entityLeftWorldX >nextX) {
                    this.moveUntil(-1,  0); //left
                } else if (entityLeftWorldX <nextX) {
                    this.moveUntil( 1,  0); //right
                }

            } else if (entityTopWorldY >nextY && entityLeftWorldX>nextX) {
                this.moveUntil( 0, -1);//up
                //collisionChecker.checkTile(this, 0, moveDy);
                if (collisionOn) {
                    this.moveUntil(-1,  0); //left
                }
            } else if (entityTopWorldY >nextY && entityLeftWorldX<nextX) {
                this.moveUntil( 0, -1);//up
                //collisionChecker();
                if (collisionOn) {
                    this.moveUntil( 1,  0); //right
                }
            } else if (entityTopWorldY <nextY && entityLeftWorldX>nextX) {
                this.moveUntil( 0,  1); //down
                //collisionChecker();
                if (collisionOn) {
                    this.moveUntil(-1,  0); //left
                }
            } else if (entityTopWorldY <nextY && entityLeftWorldX<nextX) {
                this.moveUntil( 0,  1); //down
                //collisionChecker();
                if (collisionOn) {
                    this.moveUntil( 1,  0); //right
                }
            }*/




            //this is disabled when the goal is a player
            /*int nextCol = pFinder.pathList.get(0).col;
            int nextRow = pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }*/

        }
        return null;
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
    }*/



    /*public void update(){
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