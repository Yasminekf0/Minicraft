package model.world;

public enum Tile {
    GRASS( true),
    DIRT( true),
    STONE(true),
    WATER( false),
    LAVA( false),
    SAND( true),
    SNOW( true);


    private final boolean isBlockPlaceable;





    Tile(boolean isBlockPlaceable) {
        this.isBlockPlaceable = isBlockPlaceable;
    }

}
