package model.world;

import model.world.generator.MapGenerator;

public class World {

    Tile[][] map;
    public World(int size, int seed){
        MapGenerator map = new MapGenerator(size, seed);
        this.map = map.getTiles();
    }


}
