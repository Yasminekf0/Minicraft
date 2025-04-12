package model.world;

import model.world.generator.MapGenerator;

public class World {

    private final Tile[][] tileMap;
    private final Block[][] blockMap;

    private final int size;
    public World(int size, int seed){
        MapGenerator map = new MapGenerator(size, seed);
        this.tileMap = map.getTiles();
        this.blockMap = map.getBlocks();
        this.size = size;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public Block[][] getBlockMap() {
        return blockMap;
    }

    public int getSize(){
        return size;
    }
}
