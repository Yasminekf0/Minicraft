package world.Generation;

import world.tile.Tile;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Biome {
    PLAINS(new LinkedHashMap<>(){{
        put(Tile.GRASS,0.9); put(Tile.WATER,0.1);}}),
    DESERT(new LinkedHashMap<>(){{
        put(Tile.SAND,0.9); put(Tile.LAVA,0.1);}}),
    FOREST(new LinkedHashMap<>(){{
        put(Tile.GRASS,0.9);}}),
    MOUNTAIN(new LinkedHashMap<>(){{
        put(Tile.LAVA,0.1);put(Tile.STONE,0.8); put(Tile.DIRT,0.1);}}),
    SNOWY(new LinkedHashMap<>(){{
        put(Tile.SNOW,0.9); put(Tile.STONE,0.1);}}),
    OCEAN(new LinkedHashMap<>(){{
        put(Tile.WATER,0.9); put(Tile.SAND,0.1);}});

    private final LinkedHashMap<Tile, Double> weightMap;



    Biome(LinkedHashMap<Tile, Double> weightMap) {
        this.weightMap = weightMap;

    }

    public Map<Tile, Double> getWeightMap() {
        return weightMap;
    }


}
