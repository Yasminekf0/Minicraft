package model.entity;

import model.position.WorldPosition;
import model.world.World;

import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.tileSize;

public class NPC extends Mob {
    private static NPC instance;


    private final World world;
    public NPC(){
        instance = this;
        this.world = World.getInstance();
        this.worldPos = new WorldPosition((tileSize/2.0) + (worldSize*tileSize) /2.0,(tileSize/2.0) + (worldSize*tileSize) /2.0);
        getSpawnPos();
        this.speed = 10;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
    }

    @Override
    public void update() {

    }

    public static NPC getInstance() {
        if (instance == null) {
            instance = new NPC();
        }
        return instance;
    }

    private void getSpawnPos(){
        while (!(world.isWalkable(worldPos.getTileXPos(),worldPos.getTileYPos())) | world.hasBlock(worldPos.getTileXPos(),worldPos.getTileYPos())){
            worldPos.increment(tileSize,tileSize);
        }
    }


}
