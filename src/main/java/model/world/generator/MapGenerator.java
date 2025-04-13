package model.world.generator;


import model.world.Biome;
import model.world.Block;
import model.world.Tile;

import java.util.Map;


public class MapGenerator {

    private final Noise[][] noiseArray;

    private final Tile[][] tileArray;

    private final Block[][] blockArray;



    int size;

    long seed;


    NoiseGenerator noiseGenerator;

    public MapGenerator(int size, long seed){
        this.size = size;
        this.seed = seed;
        noiseGenerator = new NoiseGenerator(size, seed);
        noiseArray = noiseGenerator.getNoiseArray();
        tileArray = new Tile[size][size];
        blockArray = new Block[size][size];

        generateMapArrays();
    }


    private void generateMapArrays(){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Noise noise = noiseArray[i][j];
                Tile tile = selectTile(noise);
                Block block = selectBlock(noise,tile);
                tileArray[i][j] = tile;
                blockArray[i][j] = block;

            }

        }
    }

    private Tile selectTile(Noise noise){
        Biome biome = noise.getBiome();
        Map<Tile, Double> tileWeightMap = biome.getTileWeightMap();
        double i = 0;
        for (var entry: tileWeightMap.entrySet()){
            if (noise.getTileNoise() <= noiseGenerator.getTilePerlinThreshold(entry.getValue() + i)) {
                return entry.getKey();
            }
            else i += entry.getValue();
        }
        return Tile.WATER;
    }

    private Block selectBlock(Noise noise, Tile tile){
        Biome biome = noise.getBiome();
        Map<Block, Double> blockWeightMap = biome.getBlockWeightMap();
        double i = 0;
        for (var entry: blockWeightMap.entrySet()){
            Block block = entry.getKey();
            if (noise.getBlockNoise() <= noiseGenerator.getBlockPerlinThreshold(entry.getValue() + i)) {

                if (block.isSpawnableTile(tile)) {
                    return entry.getKey();

                }

            }
            else i += entry.getValue();

        }
        return null;
    }

    public Tile[][] getTiles() {
        return tileArray;
    }

    public Block[][] getBlocks(){
        return blockArray;
    }

    public int getSize() {
        return size;
    }
}
