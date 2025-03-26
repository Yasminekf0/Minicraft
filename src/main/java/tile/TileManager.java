package tile;

import main.GamePanel;
import world.generator.WorldMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[][] mapTiles;

    int size;

    WorldMap map;

    public TileManager(GamePanel gp) {

        this.gp = gp;
        size = gp.WorldSize;
        map = new WorldMap(size);

        mapTiles = map.getWorld();

    }



    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol<gp.WorldSize && worldRow<gp.WorldSize){

            Tile tile = mapTiles[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){


                g2.drawImage(tile.getImage(),screenX,screenY, gp.tileSize,gp.tileSize,null);

            }
            worldCol++;

            if (worldCol == gp.WorldSize) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
