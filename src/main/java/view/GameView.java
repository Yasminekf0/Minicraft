package view;

import model.entity.Player;
import model.world.Tile;
import model.world.WorldGenerator;
import view.hud.OptionsMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class GameView extends JPanel {
    private WorldGenerator world;
    private Player player;
    private PlayerView playerView;
    private OptionsMenuView optionsMenuView;

    // Screen settings
    private final int originalTileSize = 16; // 16x16 px
    private final int scale = 1;
    public final int tileSize = originalTileSize * scale; // 48x48 px
    public final int maxScreenCol = 70;
    public final int maxScreenRow = 40;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    // Player drawing position on screen (centered)
    public final int playerScreenX = screenWidth / 2;
    public final int playerScreenY = screenHeight / 2;
    Tile[][] tiles;

    public GameView(WorldGenerator world, Player player) {
        this.world = world;
        this.player = player;
        tiles = world.getTiles();
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        // Create the PlayerView to handle the player's sprites
        playerView = new PlayerView(player, tileSize, playerScreenX, playerScreenY);

        optionsMenuView = new OptionsMenuView(screenWidth, screenHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWorld(g);
        playerView.draw((Graphics2D) g);
        optionsMenuView.draw((Graphics2D) g);
    }

    private void drawWorld(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

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

    public PlayerView getPlayerView() {
        return playerView;
    }

    public OptionsMenuView getOptionsMenuView() {
        return optionsMenuView;
    }

    public Player getPlayer() {
        return player;
    }

    public WorldGenerator getWorld() {
        return world;
    }
}
