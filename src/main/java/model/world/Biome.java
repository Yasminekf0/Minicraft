package model.world;


import io.cucumber.java.eo.Do;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Biome {
    PLAINS(new LinkedHashMap<>(){{
        put(Tile.GRASS,0.9); put(Tile.WATER,0.1);}},
            new LinkedHashMap<>(){{
                put(Block.Tree,0.02);}}),
    DESERT(new LinkedHashMap<>(){{
        put(Tile.SAND,0.9); put(Tile.LAVA,0.1);}},
            new LinkedHashMap<>()),
    FOREST(new LinkedHashMap<>(){{
        put(Tile.GRASS,1.0);}},
            new LinkedHashMap<>(){{
                put(Block.Tree,0.1);}}),
    MOUNTAIN(new LinkedHashMap<>(){{
        put(Tile.LAVA,0.1);put(Tile.STONE,0.8); put(Tile.DIRT,0.1);}},
            new LinkedHashMap<>(){{
                put(Block.Rock,0.3);}}),
    SNOWY(new LinkedHashMap<>(){{
        put(Tile.SNOW,0.9); put(Tile.STONE,0.1);}},
            new LinkedHashMap<>(){{
                put(Block.Rock,0.2);}}),
    OCEAN(new LinkedHashMap<>(){{
        put(Tile.WATER,0.9); put(Tile.SAND,0.1);}},
            new LinkedHashMap<>());

    private final LinkedHashMap<Tile, Double> tileWeightMap;
    private final LinkedHashMap<Block, Double> blockWeightMap;



    Biome(LinkedHashMap<Tile, Double> tileWeightMap,LinkedHashMap<Block, Double> blockWeightMap) {
        this.tileWeightMap = tileWeightMap;
        this.blockWeightMap = blockWeightMap;

    }

    public LinkedHashMap<Tile, Double> getTileWeightMap() {
        return tileWeightMap;
    }

    public LinkedHashMap<Block, Double> getBlockWeightMap() {
        return blockWeightMap;
    }
}
