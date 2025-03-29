package world.Generation;

public class Noise {

    private final Biome biome;
    private final double perlinNoise;

    public Noise(Biome biome,double perlinNoise){
        this.biome = biome;
        this.perlinNoise = perlinNoise;
    }

    public Biome getBiome() {
        return biome;
    }

    public double getPerlinNoise() {
        return perlinNoise;
    }
}
