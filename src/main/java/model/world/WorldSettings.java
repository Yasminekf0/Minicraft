package model.world;

import java.util.Random;

public class WorldSettings {

    private static final Random randomSeed = new Random();
    public final static int worldSize = 1000;

    public static final long seed = randomSeed.nextLong();
}
