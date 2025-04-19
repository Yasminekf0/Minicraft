package view;

import model.entity.Player;
import model.world.WorldBlock;
import model.world.Tile;
import model.world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.*;

public class WorldView extends GameElementView {

    private final Player player;
    private final Tile[][] tiles;

    private final WorldBlock[][] worldBlocks;
    private HashMap<Tile, BufferedImage> tileImageMap;
    private HashMap<WorldBlock, BufferedImage> blockImageMap;

    public WorldView() {
        this.player = Player.getInstance();
        World world = World.getInstance();
        tiles = world.getTileMap();
        worldBlocks = world.getBlockMap();
        loadTiles();
        loadBlocks();
    }

    @Override
    public void draw(Graphics2D g2) {

        int size = worldSize;
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
                    if (worldBlocks[i][j] != null) {
                        BufferedImage blockImg = blockImageMap.get(worldBlocks[i][j]);
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
        blockImageMap.put(WorldBlock.Wood,getBufferedImage("/tiles/plank.png"));
        blockImageMap.put(WorldBlock.Tree,getBufferedImage("/tiles/tree.png"));
        blockImageMap.put(WorldBlock.Rock,getBufferedImage("/tiles/rock.png"));
        blockImageMap.put(WorldBlock.Chest,getBufferedImage("/tiles/chest.png"));
    }

    private BufferedImage getBufferedImage(String path){
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
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
