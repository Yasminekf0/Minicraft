package model.world;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum Tile {
    GRASS("/tiles/grass.png", true),
    DIRT("/tiles/dirt.png", true),
    STONE("/tiles/stone.png", true),
    WATER("/tiles/water.png", false),
    LAVA("/tiles/lava.png", false),
    SAND("/tiles/sand.png", true),
    SNOW("/tiles/snow.png", true);

    private final String pictureFile;
    private final boolean walkable;
    private BufferedImage image;

    Tile(String pictureFile, boolean isWalkable) {
        this.pictureFile = pictureFile;
        this.walkable = isWalkable;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(this.pictureFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
