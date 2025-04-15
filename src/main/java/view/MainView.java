package view;


import javax.swing.*;
import java.awt.*;

import static view.ScreenSettings.screenHeight;
import static view.ScreenSettings.screenWidth;

public class MainView {

    private final StartView startView;
    private final JPanel container;
    private final JFrame window;

    public MainView() {
        // Set up CardLayout
        startView = new StartView();

        Dimension windowSize = new Dimension(screenWidth, screenHeight);
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
    public void startGameView(GameView gameView) {

        // Create the game view. Note that GameView uses its own getters for screen size.
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

}
