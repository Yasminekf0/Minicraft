package model.world;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum Tile {
    GRASS("/tiles/grass.png"),
    DIRT("/tiles/dirt.png"),
    STONE("/tiles/stone.png"),
    WATER("/tiles/water.png"),
    LAVA("/tiles/lava.png"),
    SAND("/tiles/sand.png"),
    SNOW("/tiles/snow.png");

    private final String pictureFile;

    private Block block;

    private BufferedImage image;

    Tile(String pictureFile) {
        this.pictureFile = pictureFile;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(this.pictureFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
