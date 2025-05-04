package model.world;

public enum Tile {
    GRASS( true, true),
    DIRT( true, true),
    STONE(true, true),
    WATER( false, false),
    LAVA( false, false),
    SAND( true, true),
    SNOW( true, true);


    // Can a block be placed on top
    private final boolean isBlockPlaceable;

    // Can Entities walk over it
    private final boolean isTileWalkable;





    Tile(boolean isBlockPlaceable, boolean isTileWalkable) {
        this.isBlockPlaceable = isBlockPlaceable;
        this.isTileWalkable = isTileWalkable;
    }

    public boolean isTileWalkable() {
        return isTileWalkable;
    }

    public boolean isTilePlaceable() { return isBlockPlaceable;
    }
}
