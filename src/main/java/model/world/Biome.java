package model.world;


import java.util.LinkedHashMap;

public enum Biome {
    PLAINS(new LinkedHashMap<>(){{
        put(Tile.GRASS,0.9); put(Tile.WATER,0.1);}},
            new LinkedHashMap<>(){{
                put(WorldBlock.Tree,0.02); put(WorldBlock.Chest,chestRate);}}),
    DESERT(new LinkedHashMap<>(){{
        put(Tile.SAND,0.99); put(Tile.LAVA,0.01);}},
            new LinkedHashMap<>(){{
                put(WorldBlock.Chest,chestRate);
            }}),
    FOREST(new LinkedHashMap<>(){{
        put(Tile.GRASS,1.0);}},
            new LinkedHashMap<>(){{
                put(WorldBlock.Tree,0.03); put(WorldBlock.Chest,chestRate);}}),
    MOUNTAIN(new LinkedHashMap<>(){{
        put(Tile.LAVA,0.01);put(Tile.STONE,0.94); put(Tile.DIRT,chestRate);}},
            new LinkedHashMap<>(){{
                put(WorldBlock.Rock,0.3); }}),
    SNOWY(new LinkedHashMap<>(){{
        put(Tile.SNOW,0.9); put(Tile.STONE,0.1);}},
            new LinkedHashMap<>(){{
                put(WorldBlock.Rock,0.2); }}),
    OCEAN(new LinkedHashMap<>(){{
        put(Tile.WATER,0.9); put(Tile.SAND,0.1);}},
            new LinkedHashMap<>(){{
                put(WorldBlock.Chest,chestRate);
            }});

    private final LinkedHashMap<Tile, Double> tileWeightMap;
    private final LinkedHashMap<WorldBlock, Double> blockWeightMap;

    private static final double chestRate = 0.001;

    Biome(LinkedHashMap<Tile, Double> tileWeightMap,LinkedHashMap<WorldBlock, Double> blockWeightMap) {
        this.tileWeightMap = tileWeightMap;
        this.blockWeightMap = blockWeightMap;

    }

    public LinkedHashMap<Tile, Double> getTileWeightMap() {
        return tileWeightMap;
    }

    public LinkedHashMap<WorldBlock, Double> getBlockWeightMap() {
        return blockWeightMap;
    }
}
