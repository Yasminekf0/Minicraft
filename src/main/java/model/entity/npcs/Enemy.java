package model.entity.npcs;

import model.entity.npcs.pathfinding.Node;
import model.entity.npcs.pathfinding.Pathfinder;

import static view.ScreenSettings.tileSize;

import java.util.List;
import java.util.Random;


public class Enemy extends Mob {
    public boolean skinType;

    public int wanderSteps = new Random().nextInt(100)+100;
    public int pathStage = new Random().nextInt(8);
    public Pathfinder pFinder =  new Pathfinder();
    public double dx, dy;
    public Enemy(){
        super();
        this.speed = 2;
        this.health = 10;
        this.maxHealth = 10;
        this.dx = 0;
        this.dy = 1;
        this.skinType = Math.random() < 0.5;

    }


    public boolean getSkinType() {
        return skinType;
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