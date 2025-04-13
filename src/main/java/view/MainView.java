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

        // Switch to the game view.
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, "game");

    }

    public StartView getStartView() {
        return startView;
    }

    public JFrame getWindow() {
        return window;
    }

}
