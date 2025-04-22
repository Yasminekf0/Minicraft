package model.entity;

import model.position.WorldPosition;
import model.world.World;

import java.util.Random;

import static java.lang.Math.round;
import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.scale;
import static view.ScreenSettings.tileSize;

public class NPC extends Mob {
    private static NPC instance;
    private final World world;
    public NPC(){
        instance = this;
        this.world = World.getInstance();
        this.worldPos = new WorldPosition((500*tileSize)+(tileSize/2.0),(500*tileSize)+(tileSize/2.0));
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

    public void setAction(){
        Random rand = new Random();
        int i = rand.nextInt(100)+1; //random num 1 to 100

        if (i<51) {
            this.worldPos.updateDirection(0,1);
        } else {
            this.worldPos.updateDirection(0,-1);
        }
    }

    public void moveUntil(double dx, double dy) {
        worldPos.updateDirection(dx,dy);
        for (int x = 0; x<speed; x++) {
            worldPos.increment(dx, dy);
        }
    }


}
