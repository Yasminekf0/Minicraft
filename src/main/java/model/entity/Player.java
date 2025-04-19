package model.entity;

import model.position.WorldPosition;
import model.world.WorldBlock;
import model.world.World;

import javax.swing.*;

import static java.lang.Math.round;
import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.tileSize;

public class Player extends Entity {

    private static Player instance;

    private final Inventory inventory;

    private final World world;

    private Player() {
        instance = this;
        this.world = World.getInstance();
        this.worldPos = new WorldPosition((tileSize/2.0) + (worldSize*tileSize) /2.0,(tileSize/2.0) + (worldSize*tileSize) /2.0);
        getSpawnPos();
        this.speed = 10;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        inventory = new Inventory();
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public Inventory getInventory() { return inventory; }

    private void getSpawnPos(){
        while (!world.isWalkable(worldPos.getTileXPos(),worldPos.getTileYPos())){
            worldPos.increment(tileSize,tileSize);
        }
    }



    public void moveUntil(double dx, double dy) {
        worldPos.updateDirection(dx,dy);
        for (int x = 0; x<speed; x++) {
            if (world.hasBlock(worldPos.getTileXPos(),worldPos.getNextYTilePos( round(dy)*20))) dy = 0;
            else if (!(world.isWalkable(worldPos.getTileXPos(),worldPos.getNextYTilePos(dy)))) dy = 0;

            if (world.hasBlock(worldPos.getNextXTilePos( round(dx)*20),worldPos.getTileYPos())) dx = 0;
            else if (!(world.isWalkable(worldPos.getNextXTilePos(dx),worldPos.getTileYPos()))) dx = 0;

            worldPos.increment(dx, dy);
        }
    }

    public void use(){
        if (inventory.getSelectedItem() != null) {
            inventory.getSelectedItem().use();
        }
    }
}
