package view;

import controller.GameController;
import controller.KeyController;
import model.entity.Player;
import model.world.WorldGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainView {

    private final int worldSize = 1000;

    private final Dimension windowSize = new Dimension(GameView.getScreenWidth(), GameView.getScreenHeight());


    private WorldGenerator world;
    private Player player;
    private GameView gameView;

    private StartView startView;
    private JPanel container;
    private JFrame window;

    public MainView() {
        // Set up CardLayout
        startView = new StartView();
        startView.setPreferredSize(windowSize);

        container = new JPanel(new CardLayout());
        container.add(startView, "start");

        // Main window config
        window = new JFrame("Minicraft");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setContentPane(container);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    // This method is called only when the "New Game" button is pressed.
    public void startNewGame() {
        // Generate a new random seed (between 1000 and 9999).
        Random random = new Random();
        int seed = random.nextInt(100000);

        // Create the game world and player.
        world = new WorldGenerator(worldSize, seed);
        player = new Player();

        // Create the game view. Note that GameView uses its own getters for screen size.
        gameView = new GameView(world, player);
        container.add(gameView, "game");

        // Switch to the game view.
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, "game");

        // Instantiate controllers for the game.
        KeyController keyController = new KeyController(gameView);
        new GameController(world, player, gameView, keyController);
    }

    public StartView getStartView() {
        return startView;
    }

    public JFrame getWindow() {
        return window;
    }

    public JPanel getContainer() {
        return container;
    }
}
