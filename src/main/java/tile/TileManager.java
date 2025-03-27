package tile;

import main.GamePanel;
import world.WorldGenerator;

import java.awt.*;

import java.util.Random;

public class TileManager {

    private GamePanel gp;
    private Tile[][] mapTiles;

    private Random random;

    private WorldGenerator map;

    private int size;
    private int seed;



    public TileManager(GamePanel gp) {

        this.gp = gp;
        size = gp.getWorldSize();
        random = new Random();

        getSeed();

        map = new WorldGenerator(size,seed);

        mapTiles = map.getWorld();

    }

    private void getSeed() {
        if (gp.isSetSeed()) seed = gp.getSeed();
        else {
            seed = random.nextInt();
        }
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
