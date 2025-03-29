package world.Generation;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.core.util.vectors.Vector;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseResult;
import de.articdive.jnoise.pipeline.JNoise;
import de.articdive.jnoise.pipeline.JNoiseDetailed;

import java.util.HashMap;
import java.util.Random;


public class NoiseGenerator {

    public static void main(String[] args) {
        NoiseGenerator noise = new NoiseGenerator(10,100);
        noise.generateWorleyNoise();
        /*
            WorleyNoiseResult<Vector> wnoise = noise.worleyNoise.evaluateNoiseResult(11, 0);
            Vector closestPoint = wnoise.getClosestPoint();
            double evalNoise = noise.worleyNoise.evaluateNoise(closestPoint.x(), closestPoint.y());
            System.out.println(evalNoise);

         */
    }

    private final Noise[][] noiseArray;
    private final Random random;
    private JNoise perlinNoise;
    private JNoiseDetailed<WorleyNoiseResult<Vector>> worleyNoise;
    private final int seed;
    private final int size;

    private final Biome[] biomes;
    private HashMap<Vector,Biome> biomeHashMap = new HashMap<>();

    public NoiseGenerator(int size, int seed){
        this.size = size;
        this.seed = seed;
        biomes = Biome.values();
        noiseArray = new Noise[size][size];
        random = new Random(seed);

        generatePerlinNoise();
        generateWorleyNoise();

        generateNoiseArray();

    }
    private void generatePerlinNoise(){
        perlinNoise = JNoise.newBuilder().perlin(seed, Interpolation.LINEAR, FadeFunction.NONE)
                .scale(1/16.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

    }

    private void generateWorleyNoise(){
        worleyNoise = JNoise.newBuilder().worley(WorleyNoiseGenerator.newBuilder().setSeed(seed).build())
                .scale(1 / 40.0)
                .clamp(0.0, 1.0)
                .buildDetailed();
    }

    private void generateNoiseArray(){
        Biome biome;
        WorleyNoiseResult<Vector> worleyNoiseResult;
        Vector worleyClosestPoint;

        for (double i=0; i<size; i++){
            for (double j=0; j<size; j++){
                worleyNoiseResult = worleyNoise.evaluateNoiseResult(i, j);
                worleyClosestPoint = worleyNoiseResult.getClosestPoint();
                biome = getBiome(worleyClosestPoint);
                noiseArray[(int) i][(int) j] = new Noise(biome,perlinNoise.evaluateNoise(i, j));
            }

        }
    }

    private Biome getBiome(Vector closestPoint) {
        biomeHashMap.putIfAbsent(closestPoint,biomes[random.nextInt(biomes.length)]);
        return biomeHashMap.get(closestPoint);
    }

    public Noise[][] getNoiseArray() {
        return noiseArray;
    }
}
