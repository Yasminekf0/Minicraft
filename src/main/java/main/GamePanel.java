package main;

import entity.EntityDrawer;
import entity.Player;
import world.WorldGenerator;
import world.tile.TileDrawer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 px
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 px
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    private int WorldSize = 1000;

    public int getWorldSize(){
        return WorldSize;
    }

    public void setWorldSize(int size){
        WorldSize = size;
    }

    public final int worldWidth = tileSize * WorldSize;
    public final int worldHeight = tileSize * WorldSize;

    private final int seed = 980;

    int FPS = 60;


    WorldGenerator map = new WorldGenerator(WorldSize, seed);
    TileDrawer tileM = new TileDrawer(this,map.getWorld());
    KeyInputs keyI = new KeyInputs();
    Thread gameThread;
    public Player player = new Player(this,keyI);
    private final EntityDrawer playerDrawer = new EntityDrawer(player);


    public GamePanel() {

        setFocusable(true);
        requestFocus();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyI);

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {

                // 1. UPDATE - update game information
                update();

                // 2. DRAW - draw the screen with the updated information
                repaint();

                delta--;
            }
        }
    }
    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        playerDrawer.draw(g2);

        g2.dispose();
    }



}


