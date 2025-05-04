package model.entity.mobs;

import model.entity.Player;
import model.entity.mobs.pathfinding.Node;
import model.entity.mobs.pathfinding.Pathfinder;

import static view.settings.ScreenSettings.scale;
import static view.settings.ScreenSettings.tileSize;

import java.util.List;


public class Enemy extends Mob {
    private boolean onPath = false; // Path-following status
    private int damage = 1;
    private long lastHitTime;

    public final Pathfinder pFinder =  new Pathfinder();

    public Enemy(){
        super();
        this.speed = 2;
        this.health = 50;
        this.maxHealth = 50;
        this.dx = 0;
        this.dy = 1;
        // Randomly assign a skin type (appearance variant 0 (zombie) or 1 (skeleton)).
        this.skinType = (Math.random() < 0.5)  ? 0 : 1;
        this.lastHitTime = System.currentTimeMillis();



    }

    @Override
    public void interact(){
        // Enforce a cooldown between hits (1000 ms)
        if (System.currentTimeMillis() - lastHitTime > 1000) {
            Player player = Player.getInstance();
            player.takeDamage(damage);
            // Knock-back the player slightly in the direction the enemy is facing
            player.lockDirection();
            player.moveUntil(
                    worldPos.getDirectionFacing().getX() * scale,
                    worldPos.getDirectionFacing().getY() * scale
            );
            player.unlockDirection();
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

    // Calculates a path from the enemy's current tile to a target tile using the Pathfinder
    public List<Node> searchPath(int goalCol, int goalRow) {

        int startCol = (this.getWorldPos().getXInt()+ this.solidArea.x)/tileSize;
        int startRow = (this.getWorldPos().getYInt()+ this.solidArea.y)/tileSize;
        pFinder.setNode(startCol, startRow, goalCol, goalRow); //Initialize the nodes with start and goal nodes

        if (pFinder.search() && !pFinder.pathList.isEmpty()){ // Execute the search and return the path if one was found
            return pFinder.pathList;
        }
        return null;
    }

}