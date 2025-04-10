package model.world;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum Tile {
    GRASS( true),
    DIRT( true),
    STONE(true),
    WATER( false),
    LAVA( false),
    SAND( true),
    SNOW( true);


    private final boolean isBlockPlaceable;

    private Block block;



    Tile(boolean isBlockPlaceable) {
        this.isBlockPlaceable = isBlockPlaceable;
    }



    public void setBlock(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
