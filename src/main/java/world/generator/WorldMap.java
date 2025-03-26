package world.generator;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;
import tile.Tile;

import java.util.Arrays;

public class WorldMap {

    public static void main(String[] args) {
        WorldMap world = new WorldMap(4);
        System.out.println(Arrays.deepToString(world.getWorld()));
    }

    private double[][] worldArray;
    int size;

    private JNoise noiseMap;

    public WorldMap(int size){
        this.size = size;
        worldArray = new double[size][size];
        generateNoiseArray();
        generateWorld();
    }

    private void generateNoiseArray(){
        noiseMap = JNoise.newBuilder().perlin(1077, Interpolation.LINEAR, FadeFunction.CUBIC_POLY)
                .scale(1 / 16.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

    }

    private void generateWorld(){
        for (double i=0; i<size; i++){
            for (double j=0; j<size; j++){
                this.worldArray[(int) i][(int) j] = noiseMap.evaluateNoise(i, j);
            }

        }
    }
    public double[][] getWorld() {
        return worldArray;
    }
}
