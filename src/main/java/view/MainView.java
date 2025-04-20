package view;


import javax.swing.*;
import java.awt.*;

import static view.ScreenSettings.screenHeight;
import static view.ScreenSettings.screenWidth;

public class MainView {

    private final StartView startView;
    private final JPanel container;
    private final JFrame window;
    private final OptionsView optionsView;

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

        optionsView = new OptionsView();
        window.setGlassPane(optionsView);
        optionsView.setVisible(false);
    }

    // This method is called only when the "New Game" button is pressed.
    public void startGameView(GameView gameView, HUDView hudView) {

        JLayeredPane gameLayer = new JLayeredPane();
        Dimension sz = new Dimension(screenWidth, screenHeight);
        gameLayer.setPreferredSize(sz);

        gameView.setBounds(0, 0, sz.width, sz.height);
        hudView.setBounds(0, 0, sz.width, sz.height);

        gameLayer.add(gameView, JLayeredPane.DEFAULT_LAYER);
        gameLayer.add(hudView,  JLayeredPane.PALETTE_LAYER);

        container.add(gameLayer, "game");

        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, "game");

    }

    public StartView getStartView() {
        return startView;
    }

    public JFrame getWindow() {
        return window;
    }

    public OptionsView getOptionsView() { return optionsView; }

}
