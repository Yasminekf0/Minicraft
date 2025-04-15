package model.world;

public enum Tile {
    GRASS( true, true),
    DIRT( true, true),
    STONE(true, true),
    WATER( false, false),
    LAVA( false, false),
    SAND( true, true),
    SNOW( true, true);


    private final boolean isBlockPlaceable;
    private final boolean isTileWalkable;





    Tile(boolean isBlockPlaceable, boolean isTileWalkable) {
        this.isBlockPlaceable = isBlockPlaceable;
        this.isTileWalkable = isTileWalkable;
    }

    public boolean isTileWalkable() {
        return isTileWalkable;
    }
}
