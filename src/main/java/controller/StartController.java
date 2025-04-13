package controller;

import model.entity.Player;
import model.world.World;
import view.GameView;
import view.MainView;
import view.StartView;

import javax.swing.*;

import static model.world.WorldSettings.seed;
import static model.world.WorldSettings.worldSize;

public class StartController {
    private final MainView mainView;

    public StartController(MainView mainView) {
        this.mainView = mainView;
        initListeners();
    }

    private void initListeners() {
        StartView startView = mainView.getStartView();

        // New Game Listener
        startView.addNewGameListener(e -> startNewGame());


        // TODO: Should call method in View instead
        // Load Game Listener
        startView.addLoadGameListener(e -> JOptionPane.showMessageDialog(
                mainView.getWindow(),
                "Load Game is not implemented yet.",
                "Warning",
                JOptionPane.WARNING_MESSAGE
        ));

        // Quit Game Listener
        startView.addQuitListener(e -> System.exit(0));
    }
    private void startNewGame(){
        World world = new World(worldSize, seed);
        Player player = new Player();
        GameView gameView = new GameView(world, player);

        mainView.startGameView(gameView);

        // Instantiate controllers for the game.
        KeyController keyController = new KeyController(gameView);
        new GameController(world, player, gameView, keyController);
    }
}
