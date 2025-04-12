package view;

import model.entity.Player;
import model.world.WorldGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainView {

    private final int worldSize = 1000;
    private final int seed;

    private WorldGenerator world;
    private Player player;
    private GameView gameView;
    private StartView startView;
    private JPanel container;
    private JFrame window;

    public MainView() {

        // Random seed
        Random random = new Random();
        seed = random.nextInt(100000);

        world = new WorldGenerator(worldSize, seed);
        player = new Player();

        gameView = new GameView(world, player);
        startView = new StartView();

        // Creating CardLayout for different views
        container = new JPanel(new CardLayout());
        container.add(startView, "start");
        container.add(gameView, "game");

        // Configuring Main window
        window = new JFrame("Minicraft");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(container);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    // Getters
    public WorldGenerator getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public GameView getGameView() {
        return gameView;
    }

    public StartView getStartView() {
        return startView;
    }

    public JPanel getContainer() {
        return container;
    }

    public JFrame getWindow() {
        return window;
    }
}
