package model.entity.npcs;

import model.entity.Player;
import model.entity.npcs.pathfinding.Node;
import model.entity.npcs.pathfinding.Pathfinder;

import static view.settings.ScreenSettings.scale;
import static view.settings.ScreenSettings.tileSize;

import java.util.List;
import java.util.Random;


public class Enemy extends Mob {
    private boolean onPath = false;

    private long lastHitTime;

    public final Pathfinder pFinder =  new Pathfinder();

    public Enemy(){
        super();
        this.speed = 2;
        this.health = 10;
        this.maxHealth = 10;
        this.dx = 0;
        this.dy = 1;
        this.skinType = (Math.random() < 0.5)  ? 0 : 1;
        this.lastHitTime = System.currentTimeMillis();


    }

    @Override
    public void interact(){

        if (System.currentTimeMillis() - lastHitTime > 1000) {
            Player player = Player.getInstance();
            player.takeDamage(1);
            System.out.println("Got hit by enemy, health:" + player.getHealth());
            player.moveUntil(
                    worldPos.getDirectionFacing().getX() * scale,
                    worldPos.getDirectionFacing().getY() * scale
            );
            lastHitTime = System.currentTimeMillis();
        }

    }

    public boolean getOnPath(){
        return onPath;
    }
    public void setOnPath(boolean onPath){
        this.onPath = onPath;
    }
    public int getSkinType() {
        return skinType;
    }

    public List<Node> searchPath(int goalCol, int goalRow) {

        int startCol = (this.getWorldPos().getXInt()+ this.solidArea.x)/tileSize;
        int startRow = (this.getWorldPos().getYInt()+ this.solidArea.y)/tileSize;
        pFinder.setNode(startCol, startRow, goalCol, goalRow);

        if (pFinder.search() && !pFinder.pathList.isEmpty()){
            return pFinder.pathList;
        }
        return null;
    }

}