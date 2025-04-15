package model.world.generator;

import de.articdive.jnoise.pipeline.JNoise;
import model.world.Biome;

public class Noise {

    private final Biome biome;
    private final JNoise tileNoise;

    private final JNoise blockNoise;

    private final int X;

    private final int Y;

    public Noise(Biome biome, JNoise tileNoise, JNoise blockNoise, int x, int y){
        this.biome = biome;
        this.tileNoise = tileNoise;
        X = x;
        Y = y;
        this.blockNoise = blockNoise;

    }

    public Biome getBiome() {
        return biome;
    }

    public double getTileNoise() {
        return tileNoise.evaluateNoise(X,Y);
    }

    public double getBlockNoise() {
        return blockNoise.evaluateNoise(X,Y);
    }
}
