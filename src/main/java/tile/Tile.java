package tile;


public enum Tile {
    GRASS("/tiles/grass.png", true),
    STONE("/tiles/stone.png", true),
    WATER("/tiles/water.png", false);

    private String pictureFile;
    private boolean walkable;

    private Tile(String pictureFile, boolean isWalkable) {
        this.pictureFile = pictureFile;
        this.walkable = isWalkable;
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
