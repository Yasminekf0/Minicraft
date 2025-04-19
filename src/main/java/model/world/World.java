package model.world;


import model.world.generator.MapGenerator;

import static model.world.WorldSettings.seed;
import static model.world.WorldSettings.worldSize;

public class World {

    private static World instance;

    private final Tile[][] tileMap;
    private final WorldBlock[][] worldBlockMap;

    private World(){
        MapGenerator map = new MapGenerator(worldSize, seed);
        this.tileMap = map.getTiles();
        this.worldBlockMap = map.getBlocks();
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public WorldBlock[][] getBlockMap() {
        return worldBlockMap;
    }

    public boolean isWalkable(int x, int y){
        return (tileMap[x][y].isTileWalkable() & worldBlockMap[x][y] == null);
    }

    public WorldBlock getBlock(int x , int y){
        return worldBlockMap[x][y];
    }

    public void breakBlock(int x, int y){
        worldBlockMap[x][y] = null;
    }

}
