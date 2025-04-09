package model.world.generator;

import de.articdive.jnoise.pipeline.JNoise;
import model.position.WorldPosition;
import model.world.Biome;

public class Noise {

    private final Biome biome;
    private final JNoise tileNoise;

    private final JNoise blockNoise;

    private final double worldX;

    private final double worldY;

    public Noise(Biome biome, JNoise tileNoise, JNoise blockNoise, WorldPosition position){
        this.biome = biome;
        this.tileNoise = tileNoise;
        this.worldX = position.getX();
        this.worldY = position.getY();
        this.blockNoise = blockNoise;

    }

    public Biome getBiome() {
        return biome;
    }

    public double getTileNoise() {
        return tileNoise.evaluateNoise(worldX,worldY);
    }

    public double getBlockNoise() {
        return blockNoise.evaluateNoise(worldX,worldY);
    }
}
