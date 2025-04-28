package controller;

import model.DayCycleManager;
import model.entity.Player;
import model.items.tools.Axe;
import model.world.World;
import view.*;
import model.saveloadmanager.*;
import java.io.*;
import view.SoundManager;


import javax.swing.*;

public class StartController {
    private final MainView mainView;
    private final SoundManager soundManager = SoundManager.getInstance();

    public StartController(MainView mainView) {
        this.mainView = mainView;
        initListeners();
        soundManager.loadAllSounds();
    }

    private void initListeners() {
        StartView startView = mainView.getStartView();

        // New Game Listener
        startView.addNewGameListener(_ -> {
            soundManager.playSound("button");
            startNewGame();
        });


        // TODO: Should call method in View instead
        // Load Game Listener
        startView.addLoadGameListener(_ -> {
            soundManager.playSound("button");
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
        startView.addQuitListener(_ -> {
            soundManager.playSound("button");
            System.exit(0);
        });
    }
    private void startNewGame(){
        World world = World.getInstance();
        Player player = Player.getInstance();
        DayCycleManager dayCycleManager = DayCycleManager.getInstance();
        GameView gameView = new GameView();
        NPCView npcView = gameView.getNpcView();
        HUDView hudView = new HUDView(player);

        OptionsView optionsView = mainView.getOptionsView();
        mainView.startGameView(gameView, hudView);


        // Instantiate controllers for the game.
        GameController gameController = new GameController(gameView, npcView,hudView);
        PlayerController playerController = new PlayerController(gameView.getPlayerView());
        NPCController npcController = new NPCController(gameView.getNpcView());
        KeyController keyController = new KeyController(gameController, playerController);
        OptionsController optionsController = new OptionsController(optionsView, gameController);

        gameController.setOptionsView(optionsView);
        
        soundManager.loopSoundContinuously("background");

    }
}
