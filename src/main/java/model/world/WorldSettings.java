package model.world;

import java.util.Random;

public class WorldSettings {

    private static final Random randomSeed = new Random();
    public final static int worldSize = 2000;

    public static long seed = randomSeed.nextLong();
}
