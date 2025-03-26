package tile;

import main.GamePanel;

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

    public TileManager(GamePanel gp) {

        this.gp = gp;

        mapTiles = new Tile[gp.maxWorldCol][gp.maxWorldRow];

        loadMap();
    }

    public void loadMap() {
        try {

            int row = 0;
            // Read one line for each row in your map.
            while (row < gp.maxWorldRow) {
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    mapTiles[col][row] = Tile.GRASS;
                }
                row++;
            }

            System.out.println(mapTiles);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow){

            Tile tile = mapTiles[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - (int) gp.player.getWorldPos().getX() + gp.player.screenX;
            int screenY = worldY - (int) gp.player.getWorldPos().getY() + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.getWorldPos().getX() - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.getWorldPos().getX() + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.getWorldPos().getY() - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.getWorldPos().getY() + gp.player.screenY){


                g2.drawImage(tile.getImage(),screenX,screenY, gp.tileSize,gp.tileSize,null);

            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
