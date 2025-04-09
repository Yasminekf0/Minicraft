package model.world;

import de.articdive.jnoise.pipeline.JNoise;
import model.position.WorldPosition;
import java.io.Serializable;

public class Noise implements Serializable {

    private final Biome biome;
    private final JNoise perlinNoise;

    private final double worldX;

    private final double worldY;

    public Noise(Biome biome, JNoise perlinNoise, WorldPosition position){
        this.biome = biome;
        this.perlinNoise = perlinNoise;
        this.worldX = position.getX();
        this.worldY = position.getY();

    }

    public Biome getBiome() {
        return biome;
    }

    public double getPerlinNoise() {
        return perlinNoise.evaluateNoise(worldX,worldY);
    }
}
