package world.tile;

import main.GamePanel;
import world.WorldGenerator;

import java.awt.*;

public class TileDrawer {

    private final GamePanel gp;
    private final Tile[][] mapTiles;




    public TileDrawer(GamePanel gp, Tile[][] mapTiles) {

        this.gp = gp;

        this.mapTiles = mapTiles;

    }


    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol<gp.getWorldSize() && worldRow<gp.getWorldSize()){

            Tile tile = mapTiles[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - (int) gp.player.getWorldPos().getX() + gp.player.getScreenX();
            int screenY = worldY - (int) gp.player.getWorldPos().getY() + gp.player.getScreenY();

            if (worldX + gp.tileSize > gp.player.getWorldPos().getX() - gp.player.getScreenX() &&
                worldX - gp.tileSize < gp.player.getWorldPos().getX() + gp.player.getScreenX() &&
                worldY + gp.tileSize > gp.player.getWorldPos().getY() - gp.player.getScreenY() &&
                worldY - gp.tileSize < gp.player.getWorldPos().getY() + gp.player.getScreenY()){


                g2.drawImage(tile.getImage(),screenX,screenY, gp.tileSize,gp.tileSize,null);

            }
            worldCol++;

            if (worldCol == gp.getWorldSize()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
