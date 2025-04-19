package controller;

import model.entity.Player;
import model.items.tools.Axe;
import model.world.World;
import view.*;

import javax.swing.*;

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
        World world = World.getInstance();
        Player player = Player.getInstance();
        GameView gameView = new GameView();
        HUDView hudView = new HUDView(player);

        OptionsView optionsView = mainView.getOptionsView();
        mainView.startGameView(gameView, hudView);

        // Instantiate controllers for the game.
        GameController gameController = new GameController(gameView);
        PlayerController playerController = new PlayerController(gameView.getPlayerView());
        KeyController keyController = new KeyController(gameController, playerController);
        OptionsController optionsController = new OptionsController(optionsView, gameController);

        gameController.setOptionsView(optionsView);

    }
}
