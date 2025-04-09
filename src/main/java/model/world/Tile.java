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
    private final boolean isBlockPlaceable;

    private Block block;

    private BufferedImage image;

    Tile(String pictureFile, boolean isBlockPlaceable) {
        this.pictureFile = pictureFile;
        this.isBlockPlaceable = isBlockPlaceable;
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
