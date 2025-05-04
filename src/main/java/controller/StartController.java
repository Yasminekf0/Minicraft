package controller;

import model.world.DayCycleManager;
import model.entity.Player;
import model.entity.mobs.MobManager;
import model.world.World;
import model.saveloadmanager.*;
import view.audio.SoundManager;
import view.game.core.MainView;
import view.menus.StartView;

import java.io.*;


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
        World _ = World.getInstance();
        Player _ = Player.getInstance();
        DayCycleManager _ = DayCycleManager.getInstance();
        MobManager _ = MobManager.getInstance();


        // Instantiate controllers for the game.
        GameController _ = new GameController(mainView);


        soundManager.loopSoundContinuously("background");

    }
}
