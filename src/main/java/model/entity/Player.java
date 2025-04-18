package model.entity;

import model.Inventory;
import model.items.Item;
import model.items.potions.HealthPotion;
import model.items.potions.Potion;
import model.items.potions.SpeedPotion;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;
import model.items.tools.Tool;
import model.position.WorldPosition;
import model.world.Block;
import model.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.tileSize;

public class Player extends Entity {

    Inventory inventory;

    public Player() {
        this.worldPos = new WorldPosition((worldSize*tileSize) /2.0,(worldSize*tileSize) /2.0);
        this.speed = 10;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        inventory = new Inventory();
    }

    public void moveUntil(double dx, double dy, World world) {
        worldPos.updateDirection(dx,dy);
        for (int x = 0; x<speed; x++) {
            if (!(world.isWalkable(worldPos.getTileXPos(),worldPos.getNextYTilePos(dy)))) dy = 0;
            if (!(world.isWalkable(worldPos.getNextXTilePos(dx),worldPos.getTileYPos()))) dx = 0;
            worldPos.increment(dx, dy);
        }
    }



    private int tempBlockDurability;
    public void startBreakingBlock(World world) {
        Block block = world.getBlock(worldPos.getFocusedTileX(), worldPos.getFocusedTileY());
        tempBlockDurability = block.getBlockDurabilty();
    }

    public void keepBreakingBlock(World world){
        if (tempBlockDurability <= 0) world.breakBlock(worldPos.getFocusedTileX(), worldPos.getFocusedTileY());
        else tempBlockDurability -= 10;
    }
}
