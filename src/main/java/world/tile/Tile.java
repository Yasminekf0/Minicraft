package world.tile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public enum Tile {
    GRASS("/tiles/grass.png", true),
    STONE("/tiles/stone.png", true),
    WATER("/tiles/water.png", false);

    private final boolean walkable;

    private BufferedImage image;

    Tile(String pictureFile, boolean isWalkable) {
        this.walkable = isWalkable;

        try{
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pictureFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getImage() {
        return this.image;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
