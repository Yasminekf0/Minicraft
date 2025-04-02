package view;

import model.entity.Player;
import model.world.Tile;
import model.world.WorldGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView extends JPanel {
    private WorldGenerator world;
    private Player player;
    private PlayerView playerView;

    // Screen settings
    private final int originalTileSize = 16; // 16x16 px
    private final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 px
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 20;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    // Player drawing position on screen (centered)
    public final int playerScreenX = screenWidth / 2;
    public final int playerScreenY = screenHeight / 2;

    public GameView(WorldGenerator world, Player player) {
        this.world = world;
        this.player = player;
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        // Create the PlayerView to handle the player's sprites
        playerView = new PlayerView(player, tileSize, playerScreenX, playerScreenY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWorld(g);
        playerView.draw((Graphics2D) g);
    }

    private void drawWorld(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Tile[][] tiles = world.getTiles();
        int size = world.getSize();
        int playerWorldX = player.getWorldPos().getX().intValue();
        int playerWorldY = player.getWorldPos().getY().intValue();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int worldX = i * tileSize;
                int worldY = j * tileSize;
                int screenX = worldX - playerWorldX + playerScreenX;
                int screenY = worldY - playerWorldY + playerScreenY;

                // Draw only tiles within the visible area
                if (worldX + tileSize > playerWorldX - playerScreenX &&
                        worldX - tileSize < playerWorldX + playerScreenX &&
                        worldY + tileSize > playerWorldY - playerScreenY &&
                        worldY - tileSize < playerWorldY + playerScreenY) {

                    BufferedImage img = tiles[i][j].getImage();
                    g2.drawImage(img, screenX, screenY, tileSize, tileSize, null);
                }
            }
        }
    }

    public PlayerView getPlayerView() {
        return playerView;
    }
}
