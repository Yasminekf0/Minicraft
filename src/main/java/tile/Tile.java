package tile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum Tile {
    GRASS("/tiles/grass.png", true),
    STONE("/tiles/stone.png", true),
    WATER("/tiles/water.png", false);

    private String pictureFile;
    private boolean walkable;

    private BufferedImage image;

    private Tile(String pictureFile, boolean isWalkable) {
        this.pictureFile = pictureFile;
        this.walkable = isWalkable;

        try{
            this.image = ImageIO.read(getClass().getResourceAsStream(this.pictureFile));
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
