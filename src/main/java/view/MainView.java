package view;

import controller.GameController;
import controller.KeyController;
import controller.OptionsController;
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
        // Generate a new random seed and create the game world.
        Random random = new Random();
        int seed = random.nextInt(100000);
        world = new WorldGenerator(worldSize, seed);
        player = new Player();

        // Create the GameView.
        gameView = new GameView(world, player);
        container.add(gameView, "game");

        // Instantiate controllers for the game.
        KeyController keyController = new KeyController(gameView);
        GameController gameController = new GameController(world, player, gameView, keyController);

        // Instead of adding OptionsView to container, add it as an overlay.
        OptionsView optionsView = new OptionsView();
        optionsView.setBounds(0, 0, window.getWidth(), window.getHeight());
        optionsView.setVisible(false);
        window.getLayeredPane().add(optionsView, JLayeredPane.MODAL_LAYER);

        gameController.setOptionsView(optionsView);

        // Optionally, create an OptionsController to handle its events.
        new OptionsController(optionsView, gameController);

        // Switch to the game view.
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, "game");
        gameView.requestFocusInWindow();
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
