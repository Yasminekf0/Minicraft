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
    private CollisionChecker collisionChecker;
    private final World world;
    public NPC(){
        instance = this;
        this.world = World.getInstance();
        this.worldPos = new WorldPosition((505*tileSize)+(tileSize/2.0),(495*tileSize)+(tileSize/2.0));
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


        for (int i = 0; i < speed; i++) {
            double stepX = dx;
            double stepY = dy;

            if (!collisionChecker.canMoveY(worldPos, stepY)) {
                stepY = 0;
            }
            if (!collisionChecker.canMoveX(worldPos, stepX)) {
                stepX = 0;
            }

            worldPos.increment(stepX, stepY);
        }




        for (int x = 0; x<speed; x++) {
            if (world.hasBlock(worldPos.getTileXPos(),worldPos.getNextYTilePos( round(dy)*4*scale))) dy = 0;
            else if (!(world.isWalkable(worldPos.getTileXPos(),worldPos.getNextYTilePos(dy)))) dy = 0;

            if (world.hasBlock(worldPos.getNextXTilePos( round(dx)*4*scale),worldPos.getTileYPos())) dx = 0;
            else if (!(world.isWalkable(worldPos.getNextXTilePos(dx),worldPos.getTileYPos()))) dx = 0;

            if (world.hasBlock(worldPos.getNextXTilePos( round(dx)*4*scale),worldPos.getTileYPos())) dx = 0;
            else if (!(world.isWalkable(worldPos.getNextXTilePos(dx),worldPos.getTileYPos()))) dx = 0;

            worldPos.increment(dx, dy);
        }
    }


}
