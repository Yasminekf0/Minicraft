package model.world.generator;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.core.util.vectors.Vector;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseResult;
import de.articdive.jnoise.pipeline.JNoise;
import de.articdive.jnoise.pipeline.JNoiseDetailed;

import model.world.Biome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class NoiseGenerator {


    private final Noise[][] noiseArray;
    private final Random randomBiomes;
    private final Random randomTileNoise;
    private final Random randomBlockNoise;
    @SuppressWarnings("NullableProblems")
    private JNoiseDetailed<WorleyNoiseResult<Vector>> worleyNoise;
    private final long seed;
    private final int size;

    private final double[] tilePerlinArray;

    private final double[] blockPerlinArray;

    private final Biome[] biomes;
    private final HashMap<Vector,Biome> biomeHashMap = new HashMap<>();

    private final HashMap<Biome,JNoise> tileHashMap = new HashMap<>();

    private final HashMap<Biome,JNoise> blockHashMap = new HashMap<>();

    public NoiseGenerator(int size, long seed){
        this.size = size;
        this.seed = seed;
        biomes = Biome.values();
        noiseArray = new Noise[size][size];
        tilePerlinArray = new double[size*size];
        blockPerlinArray = new double[size*size];
        randomBiomes = new Random(seed);
        randomTileNoise = new Random(seed);
        randomBlockNoise = new Random(seed+69);

        generateWorleyNoise();
        generateNoiseArray();
        Arrays.sort(tilePerlinArray);
        Arrays.sort(blockPerlinArray);

    }
    private JNoise generatePerlinNoise(long seed){

        return JNoise.newBuilder().perlin(seed, Interpolation.LINEAR, FadeFunction.NONE)
                .scale(1/16.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

    }

    private JNoise generateValueNoise(long seed){
        return JNoise.newBuilder().white(seed)
                .scale(1)
                .addModifier(v -> (v + 1) / 2.0)
                .build();
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

        JNoise tileNoise;
        JNoise blockNoise;

        for (int i = 0, k = 0; i<size; i++){
            for (int j=0; j<size; j++, k++){
                worleyNoiseResult = worleyNoise.evaluateNoiseResult(i, j);
                worleyClosestPoint = worleyNoiseResult.getClosestPoint();
                biome = getBiome(worleyClosestPoint);

                tileNoise = getTileNoise(biome);

                blockNoise = getBlockNoise(biome);


                noiseArray[i][j] = new Noise(biome,tileNoise,blockNoise, i, j);

                tilePerlinArray[k] = tileNoise.evaluateNoise(i, j);
                blockPerlinArray[k] = blockNoise.evaluateNoise(i, j);
            }

        }
    }

    private Biome getBiome(Vector closestPoint) {
        biomeHashMap.putIfAbsent(closestPoint,biomes[randomBiomes.nextInt(biomes.length)]);
        return biomeHashMap.get(closestPoint);
    }

    private JNoise getTileNoise(Biome biome){
        tileHashMap.putIfAbsent(biome,generatePerlinNoise(randomTileNoise.nextLong()));
        return tileHashMap.get(biome);
    }

    private JNoise getBlockNoise(Biome biome){
        if (biome == Biome.MOUNTAIN | biome == Biome.SNOWY) blockHashMap.putIfAbsent(biome,generatePerlinNoise(randomBlockNoise.nextLong()));
        else blockHashMap.putIfAbsent(biome,generateValueNoise(randomBlockNoise.nextLong()));
        return blockHashMap.get(biome);
    }

    public Noise[][] getNoiseArray() {
        return noiseArray;
    }

    public double getTilePerlinThreshold(double prob){
        return tilePerlinArray[(int) (prob * size * size-1)];
    }

    public double getBlockPerlinThreshold(double prob){
        return blockPerlinArray[(int) (prob * size * size-1)];
    }
}
