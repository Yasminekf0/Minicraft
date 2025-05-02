package model.entity.npcs;

import model.entity.npcs.pathfinding.Node;
import model.entity.npcs.pathfinding.Pathfinder;
import model.entity.CollisionChecker;
import model.position.WorldPosition;

import java.awt.*;

import static view.ScreenSettings.tileSize;

import java.util.List;
import java.util.Random;

import static java.lang.Math.round;

public class Enemy extends Mob {
    public boolean skinType;
    public CollisionChecker collisionChecker;
    public int wanderSteps = new Random().nextInt(100)+100;
    public int pathStage = new Random().nextInt(8);
    private final int lastGoalCol = -1;
    private final int lastGoalRow = -1;
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
        this.skinType = Math.random() < 0.5;

    }

    public boolean getSkinType() {
        return skinType;
    }


    public void moveUntil(double dx, double dy){
        double moveDx = dx * speed;
        double moveDy = dy * speed;

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

        int startCol = (this.getWorldPos().getX().intValue()+ this.solidArea.x)/tileSize;
        int startRow = (this.getWorldPos().getY().intValue()+ this.solidArea.y)/tileSize;
        pFinder.setNode(startCol, startRow, goalCol, goalRow);

        if (pFinder.search() && !pFinder.pathList.isEmpty()){
            return pFinder.pathList;
        }
        return null;
    }

}