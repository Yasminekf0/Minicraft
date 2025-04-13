package model.world.generator;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.core.util.vectors.Vector;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseResult;
import de.articdive.jnoise.pipeline.JNoise;
import de.articdive.jnoise.pipeline.JNoiseDetailed;
import model.position.WorldPosition;
import model.world.Biome;
import model.world.Block;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class NoiseGenerator {


    private final Noise[][] noiseArray;
    private final Random randomBiomes;
    private final Random randomTilePerlin;
    private final Random randomBlockPerlin;
    private JNoiseDetailed<WorleyNoiseResult<Vector>> worleyNoise;
    private final long seed;
    private final int size;

    private final double[] tilePerlinArray;

    private final double[] blockPerlinArray;

    private final Biome[] biomes;
    private final HashMap<Vector,Biome> biomeHashMap = new HashMap<>();

    private final HashMap<Vector,JNoise> tileHashMap = new HashMap<>();

    private final HashMap<Vector,JNoise> blockHashMap = new HashMap<>();

    public NoiseGenerator(int size, long seed){
        this.size = size;
        this.seed = seed;
        biomes = Biome.values();
        noiseArray = new Noise[size][size];
        tilePerlinArray = new double[size*size];
        blockPerlinArray = new double[size*size];
        randomBiomes = new Random(seed);
        randomTilePerlin = new Random(seed);
        randomBlockPerlin = new Random(seed+69);

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
        WorldPosition position;

        for (double i = 0, k = 0; i<size; i++){
            for (double j=0; j<size; j++, k++){
                worleyNoiseResult = worleyNoise.evaluateNoiseResult(i, j);
                worleyClosestPoint = worleyNoiseResult.getClosestPoint();
                biome = getBiome(worleyClosestPoint);

                tileNoise = getTileNoise(worleyClosestPoint);

                blockNoise = getBlockNoise(worleyClosestPoint);

                position = new WorldPosition(i,j);

                noiseArray[(int) i][(int) j] = new Noise(biome,tileNoise,blockNoise, position);

                tilePerlinArray[(int) k] = tileNoise.evaluateNoise(i, j);
                blockPerlinArray[(int) k] = blockNoise.evaluateNoise(i, j);
            }

        }
    }

    private Biome getBiome(Vector closestPoint) {
        biomeHashMap.putIfAbsent(closestPoint,biomes[randomBiomes.nextInt(biomes.length)]);
        return biomeHashMap.get(closestPoint);
    }

    private JNoise getTileNoise(Vector closestPoint){
        tileHashMap.putIfAbsent(closestPoint,generatePerlinNoise(randomTilePerlin.nextLong()));
        return tileHashMap.get(closestPoint);
    }

    private JNoise getBlockNoise(Vector closestPoint){
        blockHashMap.putIfAbsent(closestPoint,generateValueNoise(randomBlockPerlin.nextLong()));
        return blockHashMap.get(closestPoint);
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
