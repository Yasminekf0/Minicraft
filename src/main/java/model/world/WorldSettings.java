package model.world;

import java.util.Random;

public class WorldSettings {

    private static Random randomSeed = new Random();
    public final static int worldSize = 1000;

    public static long seed = randomSeed.nextLong();
}
