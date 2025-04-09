package view;

import io.cucumber.java.hu.Ha;
import model.entity.Player;
import model.world.Block;
import model.world.Tile;
import model.world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import static view.ScreenSettings.*;

public class WorldView extends GameElementView {

    private World world;
    private Player player;
    private Tile[][] tiles;
    private HashMap<Tile, BufferedImage> tileImageMap;
    private HashMap<Block, BufferedImage> blockImageMap;

    public WorldView(World world, Player player) {
        this.world = world;
        this.player = player;
        tiles = world.getMap();
        loadTiles();
        loadBlocks();
    }

    @Override
    public void draw(Graphics2D g2) {

        int size = world.getSize();
        int playerWorldX = player.getWorldPos().getX().intValue();
        int playerWorldY = player.getWorldPos().getY().intValue();

        int[] ij = getTopLeftTileCoords();
        int rightTileX = ij[0] + maxScreenCol + 1;
        int bottomTileY = ij[1] + maxScreenRow + 1;

        for (int i = ij[0]; i < rightTileX; i++) {
            for (int j = ij[1]; j < bottomTileY; j++) {
                int worldX = i * tileSize;
                int worldY = j * tileSize;
                int screenX = worldX - playerWorldX + playerScreenX;
                int screenY = worldY - playerWorldY + playerScreenY;

                if (i>=0 & i<size & j>=0 & j<size) {
                    BufferedImage tileImg = tileImageMap.get(tiles[i][j]);
                    g2.drawImage(tileImg, screenX, screenY, tileSize, tileSize, null);
                    if (tiles[i][j].getBlock() != null) {
                        BufferedImage blockImg = blockImageMap.get(tiles[i][j].getBlock());
                        g2.drawImage(blockImg, screenX, screenY, tileSize, tileSize, null);
                    }
                }
            }
        }
    }

    private void loadTiles() {
        tileImageMap = new HashMap<>();
        tileImageMap.put(Tile.GRASS,getBufferedImage("/tiles/grass.png"));
        tileImageMap.put(Tile.DIRT,getBufferedImage("/tiles/dirt.png"));
        tileImageMap.put(Tile.STONE,getBufferedImage("/tiles/stone.png"));
        tileImageMap.put(Tile.WATER,getBufferedImage("/tiles/water.png"));
        tileImageMap.put(Tile.LAVA,getBufferedImage("/tiles/lava.png"));
        tileImageMap.put(Tile.SAND,getBufferedImage("/tiles/sand.png"));
        tileImageMap.put(Tile.SNOW,getBufferedImage("/tiles/snow.png"));
    }

    private void loadBlocks() {
        blockImageMap = new HashMap<>();
        blockImageMap.put(Block.Wood,getBufferedImage("/tiles/plank.png"));
        blockImageMap.put(Block.Tree,getBufferedImage("/tiles/tree.png"));
        blockImageMap.put(Block.Rock,getBufferedImage("/tiles/breaking.png"));
        blockImageMap.put(Block.Chest,getBufferedImage("/tiles/chest.png"));
    }

    private BufferedImage getBufferedImage(String path){
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int[] getTopLeftTileCoords(){
        int x = (int) (player.getWorldPos().getX() - playerScreenX) / tileSize;
        int y = (int) (player.getWorldPos().getY() - playerScreenY) / tileSize;
        return new int[]{x,y};
    }

}
