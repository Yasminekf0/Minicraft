package controller;

import model.DayCycleManager;
import model.entity.Player;
import model.items.tools.Axe;
import model.world.World;
import view.*;
import model.saveloadmanager.*;
import java.io.*;


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
        startView.addLoadGameListener(_ -> {
            try {
                GameState loadedState = SaveLoadManager.loadGame("saves/save1.dat");

                // restores the player instance and world instance from file
                Player.setInstance(loadedState.getPlayer());
                World.setInstance(loadedState.getWorld());
                DayCycleManager.setInstance(loadedState.getDayCycleManager());


                startNewGame(); // transitions to the game view using the newly loaded state

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        mainView.getWindow(),
                        "Failed to load the saved game.",
                        "Load Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Quit Game Listener
        startView.addQuitListener(_ -> System.exit(0));
    }
    private void startNewGame(){
        World world = World.getInstance();
        Player player = Player.getInstance();
        SoundManager soundManager = SoundManager.getInstance();
        DayCycleManager dayCycleManager = DayCycleManager.getInstance();
        GameView gameView = new GameView();
        NPCView npcView = gameView.getNpcView();
        EnemyView enemyView = gameView.getEnemyView();
        HUDView hudView = new HUDView(player);

        OptionsView optionsView = mainView.getOptionsView();
        mainView.startGameView(gameView, hudView);

        soundManager.loadAllSounds();

        // Instantiate controllers for the game.
        GameController gameController = new GameController(gameView, npcView,enemyView, hudView);
        PlayerController playerController = new PlayerController(gameView.getPlayerView(), gameView.getEnemyView().getEnemies());
        NPCController npcController = new NPCController(gameView.getNpcView());
        EnemyController enemyController = new EnemyController(gameView.getEnemyView());
        KeyController keyController = new KeyController(gameController, playerController);
        OptionsController optionsController = new OptionsController(optionsView, gameController);

        gameController.setOptionsView(optionsView);

    }
}
