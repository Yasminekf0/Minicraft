package model.world;

import model.world.generator.MapGenerator;

public class World {

    private Tile[][] map;
    private int size;
    public World(int size, int seed){
        MapGenerator map = new MapGenerator(size, seed);
        this.map = map.getTiles();
        this.size = size;
    }

    public Tile[][] getMap() {
        return map;
    }
    public int getSize(){
        return size;
    }
}
