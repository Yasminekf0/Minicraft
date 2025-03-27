package world;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;
import tile.Tile;


public class WorldGenerator {


    private double[][] noiseArray;

    private  Tile[][] tileArray;
    int size;

    int seed;

    private JNoise noiseMap;

    public WorldGenerator(int size, int seed){
        this.size = size;
        this.seed = seed;
        noiseArray = new double[size][size];
        tileArray = new Tile[size][size];
        generateNoise();
        generateNoiseArray();
        generateTileArray();
    }

    private void generateNoise(){
        noiseMap = JNoise.newBuilder().perlin(seed, Interpolation.LINEAR, FadeFunction.CUBIC_POLY)
                .scale(1/16.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

    }

    private void generateNoiseArray(){
        for (double i=0; i<size; i++){
            for (double j=0; j<size; j++){
                noiseArray[(int) i][(int) j] = noiseMap.evaluateNoise(i, j);
            }

        }
    }

    private void generateTileArray(){
        for (double i=0; i<size; i++){
            for (double j=0; j<size; j++){
                double noise = noiseArray[(int) i][(int) j];
                tileArray[(int) i][(int) j] = selectTile(noise);
            }

        }
    }

    private Tile selectTile(double noise){
        if (noise <= 0.3) return Tile.WATER;
        else if (noise >= 0.7) return Tile.STONE;
        else return Tile.GRASS;
    }

    public Tile[][] getWorld() {
        return tileArray;
    }
}
