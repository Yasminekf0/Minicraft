package world.Generation;

import world.tile.Tile;

import java.util.Map;

public enum Biome {
    PLAINS(Map.of(Tile.GRASS,0.9,Tile.WATER,0.1)),
    DESERT(Map.of(Tile.SAND,0.9,Tile.LAVA,0.1)),
    FOREST(Map.of(Tile.GRASS,1.0)),
    MOUNTAIN(Map.of(Tile.STONE,0.8,Tile.DIRT,0.1,Tile.LAVA,0.1)),
    SNOWY(Map.of(Tile.SNOW,0.9,Tile.STONE,0.1)),
    OCEAN(Map.of(Tile.WATER,0.9,Tile.SAND,0.1));

    private final Map<Tile, Double> weightMap;



    Biome(Map<Tile, Double> weightMap) {
        this.weightMap = weightMap;

    }

    public Map<Tile, Double> getWeightMap() {
        return weightMap;
    }


}
