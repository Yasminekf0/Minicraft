package model.entity;

import model.Inventory;
import model.position.WorldPosition;
import model.world.Block;
import model.world.World;

import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.tileSize;

public class Player extends Entity {

    private Inventory inventory;

    private final World world;

    public Player(World world) {
        this.world = world;
        this.worldPos = new WorldPosition((tileSize/2.0) + (worldSize*tileSize) /2.0,(tileSize/2.0) + (worldSize*tileSize) /2.0);
        getSpawnPos();
        this.speed = 10;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        inventory = new Inventory();
    }

    public Inventory getInventory() { return inventory; }

    private void getSpawnPos(){
        while (!world.isWalkable(worldPos.getTileXPos(),worldPos.getTileYPos())){
            worldPos.increment(tileSize,tileSize);
        }
        System.out.println(worldPos.getTileXPos());
        System.out.println(worldPos.getTileYPos());
    }



    public void moveUntil(double dx, double dy) {
        worldPos.updateDirection(dx,dy);
        for (int x = 0; x<speed; x++) {
            if (!(world.isWalkable(worldPos.getTileXPos(),worldPos.getNextYTilePos(dy)))) dy = 0;
            if (!(world.isWalkable(worldPos.getNextXTilePos(dx),worldPos.getTileYPos()))) dx = 0;
            worldPos.increment(dx, dy);
        }
    }



    private int tempBlockDurability;
    public void startBreakingBlock() {
        Block block = world.getBlock(worldPos.getFocusedTileX(), worldPos.getFocusedTileY());
        tempBlockDurability = block.getBlockDurabilty();
    }

    public void keepBreakingBlock(){
        if (tempBlockDurability <= 0) world.breakBlock(worldPos.getFocusedTileX(), worldPos.getFocusedTileY());
        else tempBlockDurability -= 10;
    }
}
