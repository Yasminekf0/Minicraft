package model.world;

import model.world.generator.MapGenerator;

public class World {

    private final Tile[][] tileMap;
    private final Block[][] blockMap;

    public World(int size, long seed){
        MapGenerator map = new MapGenerator(size, seed);
        this.tileMap = map.getTiles();
        this.blockMap = map.getBlocks();
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public Block[][] getBlockMap() {
        return blockMap;
    }

}
