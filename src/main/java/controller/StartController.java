package controller;

import model.entity.Player;
import model.world.WorldGenerator;
import view.GameView;
import view.MainView;
import view.StartView;

import javax.swing.*;
import java.awt.*;

public class StartController {
    private MainView mainView;

    public StartController(MainView mainView) {
        this.mainView = mainView;
        initListeners();
    }

    private void initListeners() {
        StartView startView = mainView.getStartView();
        JPanel container = mainView.getContainer();
        GameView gameView = mainView.getGameView();
        WorldGenerator world = mainView.getWorld();
        Player player = mainView.getPlayer();

        // New Game Listener
        startView.addNewGameListener(e -> {
            CardLayout cl = (CardLayout) container.getLayout();
            cl.show(container, "game");
            // Start game loop
            KeyController keyController = new KeyController(gameView);
            new GameController(world, player, gameView, keyController);
        });

        // Load Game Listener
        startView.addLoadGameListener(e -> {
                JOptionPane.showMessageDialog(mainView.getWindow(),
                        "Load Game is not implemented yet.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
        });

        // Quit Listener
        startView.addQuitListener(e -> System.exit(0));
    }
}
