package world.Generation;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.core.util.vectors.Vector;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseResult;
import de.articdive.jnoise.pipeline.JNoise;
import de.articdive.jnoise.pipeline.JNoiseDetailed;
import world.position.WorldPosition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class NoiseGenerator {

    private final Noise[][] noiseArray;
    private final Random randomBiomes;
    private final Random randomPerlin;
    private JNoiseDetailed<WorleyNoiseResult<Vector>> worleyNoise;
    private final int seed;
    private final int size;

    private final double[] perlinArray;

    private final Biome[] biomes;
    private final HashMap<Vector,Biome> biomeHashMap = new HashMap<>();

    private final HashMap<Vector,JNoise> perlinHashMap = new HashMap<>();

    public NoiseGenerator(int size, int seed){
        this.size = size;
        this.seed = seed;
        biomes = Biome.values();
        noiseArray = new Noise[size][size];
        perlinArray = new double[size*size];
        randomBiomes = new Random(seed);
        randomPerlin = new Random(seed);

        generateWorleyNoise();
        generateNoiseArray();
        Arrays.sort(perlinArray);

    }
    private JNoise generatePerlinNoise(long seed){
        JNoise perlinNoise = JNoise.newBuilder().perlin(seed, Interpolation.LINEAR, FadeFunction.NONE)
                .scale(1/16.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

        return perlinNoise;

    }

    private void generateWorleyNoise(){
        worleyNoise = JNoise.newBuilder().worley(WorleyNoiseGenerator.newBuilder().setSeed(seed).build())
                .scale(1 / 60.0)
                .clamp(0.0, 1.0)
                .buildDetailed();
    }

    private void generateNoiseArray(){
        Biome biome;
        WorleyNoiseResult<Vector> worleyNoiseResult;
        Vector worleyClosestPoint;

        JNoise perlinNoise;
        WorldPosition position;

        for (double i = 0, k = 0; i<size; i++){
            for (double j=0; j<size; j++, k++){
                worleyNoiseResult = worleyNoise.evaluateNoiseResult(i, j);
                worleyClosestPoint = worleyNoiseResult.getClosestPoint();
                biome = getBiome(worleyClosestPoint);
                perlinNoise = getPerlinNoise(worleyClosestPoint);
                position = new WorldPosition(i,j);

                noiseArray[(int) i][(int) j] = new Noise(biome,perlinNoise,position);

                perlinArray[(int) k] = perlinNoise.evaluateNoise(i, j);
            }

        }
    }

    private Biome getBiome(Vector closestPoint) {
        biomeHashMap.putIfAbsent(closestPoint,biomes[randomBiomes.nextInt(biomes.length)]);
        return biomeHashMap.get(closestPoint);
    }

    private JNoise getPerlinNoise(Vector closestPoint){
        perlinHashMap.putIfAbsent(closestPoint,generatePerlinNoise(randomPerlin.nextLong()));
        return perlinHashMap.get(closestPoint);
    }

    public Noise[][] getNoiseArray() {
        return noiseArray;
    }

    public double getPerlinThreshold(double prob){
        return perlinArray[(int) (prob * size * size-1)];
    }
}
