package model.world.generator;


import model.world.Biome;
import model.world.WorldBlock;
import model.world.Tile;

import java.util.Map;


public class MapGenerator {

    private final Noise[][] noiseArray;

    private final Tile[][] tileArray;

    private final WorldBlock[][] worldBlockArray;



    final int size;



    final NoiseGenerator noiseGenerator;

    public MapGenerator(int size, long seed){
        this.size = size;
        noiseGenerator = new NoiseGenerator(size, seed);
        noiseArray = noiseGenerator.getNoiseArray();
        tileArray = new Tile[size][size];
        worldBlockArray = new WorldBlock[size][size];

        generateMapArrays();
    }


    private void generateMapArrays(){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Noise noise = noiseArray[i][j];
                Tile tile = selectTile(noise);
                WorldBlock worldBlock = selectBlock(noise,tile);
                tileArray[i][j] = tile;
                worldBlockArray[i][j] = worldBlock;

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

    private WorldBlock selectBlock(Noise noise, Tile tile){
        Biome biome = noise.getBiome();
        Map<WorldBlock, Double> blockWeightMap = biome.getBlockWeightMap();
        double i = 0;
        for (var entry: blockWeightMap.entrySet()){
            WorldBlock worldBlock = entry.getKey();
            if (noise.getBlockNoise() <= noiseGenerator.getBlockPerlinThreshold(entry.getValue() + i)) {

                if (worldBlock.isSpawnableTile(tile)) {
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

    public WorldBlock[][] getBlocks(){
        return worldBlockArray;
    }

}
