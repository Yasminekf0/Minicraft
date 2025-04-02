package model.world;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;

public class WorldGenerator {
    private Tile[][] tiles;
    private int size;
    private int seed;

    public WorldGenerator(int size, int seed) {
        this.size = size;
        this.seed = seed;
        generateWorld();
    }

    private void generateWorld() {
        tiles = new Tile[size][size];
        double[][] noiseArray = new double[size][size];

        JNoise noiseMap = JNoise.newBuilder()
                .perlin(seed, Interpolation.LINEAR, FadeFunction.CUBIC_POLY)
                .scale(1 / 16.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

        // Generate noise
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                noiseArray[i][j] = noiseMap.evaluateNoise(i, j);
            }
        }

        // Fill tile array
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double noise = noiseArray[i][j];
                tiles[i][j] = selectTile(noise);
            }
        }
    }

    private Tile selectTile(double noise) {
        if (noise <= 0.3) {
            return Tile.WATER;
        } else if (noise >= 0.7) {
            return Tile.STONE;
        } else {
            return Tile.GRASS;
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getSize() {
        return size;
    }

}
