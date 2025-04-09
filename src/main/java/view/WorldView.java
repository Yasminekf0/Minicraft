package view;

import model.entity.Player;
import model.world.Tile;
import model.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import static view.ScreenSettings.*;

public class WorldView extends GameElementView {

    private World world;
    private Player player;
    private Tile[][] tiles;

    public WorldView(World world, Player player) {
        this.world = world;
        this.player = player;
        tiles = world.getMap();
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
                    BufferedImage img = tiles[i][j].getImage();
                    g2.drawImage(img, screenX, screenY, tileSize, tileSize, null);
                }
            }
        }
    }

    private int[] getTopLeftTileCoords(){
        int x = (int) (player.getWorldPos().getX() - playerScreenX) / tileSize;
        int y = (int) (player.getWorldPos().getY() - playerScreenY) / tileSize;
        return new int[]{x,y};
    }

}
