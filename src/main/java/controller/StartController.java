package controller;

import model.entity.Player;
import model.world.World;
import view.GameView;
import view.MainView;
import view.StartView;
import view.OptionsView;

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
        startView.addNewGameListener(_ -> startNewGame());


        // TODO: Should call method in View instead
        // Load Game Listener
        startView.addLoadGameListener(_ -> JOptionPane.showMessageDialog(
                mainView.getWindow(),
                "Load Game is not implemented yet.",
                "Information",
                JOptionPane.INFORMATION_MESSAGE
        ));

        // Quit Game Listener
        startView.addQuitListener(_ -> System.exit(0));
    }
    private void startNewGame(){
        World world = new World(worldSize, seed);
        Player player = new Player();
        GameView gameView = new GameView(world, player);

        OptionsView optionsView = mainView.getOptionsView();
        mainView.startGameView(gameView);

        // Instantiate controllers for the game.
        GameController gameController = new GameController(gameView);
        PlayerController playerController = new PlayerController(player);
        KeyController keyController = new KeyController(gameController);
        OptionsController optionsController = new OptionsController(optionsView, gameController);

        gameController.setOptionsView(optionsView);

    }
}
