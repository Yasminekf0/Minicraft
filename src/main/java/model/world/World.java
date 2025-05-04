package model.world;


import model.world.generator.MapGenerator;

import java.io.Serializable;

import static model.world.WorldSettings.seed;
import static model.world.WorldSettings.worldSize;

public class World implements Serializable {

    private static World instance;

    private final Tile[][] tileMap;
    private final WorldBlock[][] worldBlockMap;

    private World(){
        instance = this;
        MapGenerator map = new MapGenerator(worldSize, seed);
        this.tileMap = map.getTiles();
        this.worldBlockMap = map.getBlocks();
    }

    // Singleton Pattern
    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }
    public static void setInstance(World instance) {
        World.instance = instance;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public WorldBlock[][] getBlockMap() {
        return worldBlockMap;
    }

    public boolean isWalkable(int x, int y){
        return (tileMap[x][y].isTileWalkable());
    }

    public boolean hasBlock(int x, int y){
        return worldBlockMap[x][y] != null;
    }

    public boolean isPlaceable(int x, int y){ return (tileMap[x][y].isTilePlaceable() & worldBlockMap[x][y] == null);}

    public WorldBlock getBlock(int x , int y){
        return worldBlockMap[x][y];
    }

    public void breakBlock(int x, int y){
        worldBlockMap[x][y] = null;
    }

    public void placeBlock(int x, int y, WorldBlock block) {
        worldBlockMap[x][y] = block;
    }

}
