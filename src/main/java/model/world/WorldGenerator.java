package model.world;


import java.io.Serializable;
import java.util.Map;


public class WorldGenerator implements Serializable {

    private final Noise[][] noiseArray;

    private final Tile[][] tileArray;



    int size;

    int seed;


    NoiseGenerator noiseGenerator;

    public WorldGenerator(int size, int seed){
        this.size = size;
        this.seed = seed;
        noiseGenerator = new NoiseGenerator(size, seed);
        noiseArray = noiseGenerator.getNoiseArray();
        tileArray = new Tile[size][size];

        generateTileArray();
    }


    private void generateTileArray(){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Noise noise = noiseArray[i][j];
                tileArray[ i][ j] = selectTile(noise);
            }

        }
    }

    private Tile selectTile(Noise noise){
        Biome biome = noise.getBiome();
        Map<Tile, Double> biomeWeightMap = biome.getWeightMap();
        double i = 0;
        for (var entry: biomeWeightMap.entrySet()){
            if (noise.getPerlinNoise() <= noiseGenerator.getPerlinThreshold(entry.getValue() + i)) {
                return entry.getKey();
            }
            else i += entry.getValue();
        }
        return Tile.WATER;
    }

    public Tile[][] getTiles() {
        return tileArray;
    }
    public int getSize() {
        return size;
    }
}
