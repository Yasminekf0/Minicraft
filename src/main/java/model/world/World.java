package model.world;


import model.world.generator.MapGenerator;

import static model.world.WorldSettings.seed;
import static model.world.WorldSettings.worldSize;

public class World {

    private static World instance;

    private final Tile[][] tileMap;
    private final Block[][] blockMap;

    private World(){
        MapGenerator map = new MapGenerator(worldSize, seed);
        this.tileMap = map.getTiles();
        this.blockMap = map.getBlocks();
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

    public Block[][] getBlockMap() {
        return blockMap;
    }

    public boolean isWalkable(int x, int y){
        return (tileMap[x][y].isTileWalkable() & blockMap[x][y] == null);
    }

    public Block getBlock(int x , int y){
        return blockMap[x][y];
    }

    public void breakBlock(int x, int y){
        blockMap[x][y] = null;
    }

}
