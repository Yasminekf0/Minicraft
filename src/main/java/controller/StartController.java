package controller;

import model.entity.npcs.Mob;
import model.world.DayCycleManager;
import model.entity.Player;
import model.entity.npcs.MobManager;
import model.world.World;
import model.saveloadmanager.*;
import view.HUD.HUDView;
import view.audio.SoundManager;
import view.game.elements.EnemyView;
import view.game.core.GameView;
import view.game.core.MainView;
import view.game.elements.MobView;
import view.game.elements.NPCView;
import view.menus.DeathView;
import view.menus.OptionsView;
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
        World world = World.getInstance();
        Player player = Player.getInstance();
        DayCycleManager dayCycleManager = DayCycleManager.getInstance();
        MobManager mobManager = MobManager.getInstance();
        GameView gameView = new GameView();
        NPCView npcView = gameView.getNpcView();
        EnemyView enemyView = gameView.getEnemyView();
        MobView mobView = gameView.getMobView();
        HUDView hudView = new HUDView(player);

        OptionsView optionsView = mainView.getOptionsView();
        mainView.startGameView(gameView, hudView);


        // Instantiate controllers for the game.
        GameController gameController = new GameController(gameView, npcView,enemyView, hudView, mobView);
        PlayerController playerController = new PlayerController(gameView.getPlayerView(), hudView);
        NPCController npcController = new NPCController(gameView.getNpcView(), gameView.getMobView());
        EnemyController enemyController = new EnemyController(gameView.getEnemyView(), gameView.getMobView());
        KeyController keyController = new KeyController(gameController, playerController);
        OptionsController optionsController = new OptionsController(optionsView, gameController);

        DeathView deathView = mainView.getDeathView();
        gameController.setDeathView(deathView);
        deathView.addQuitListener(e -> { System.exit(0); });

        gameController.setOptionsView(optionsView);

        soundManager.loopSoundContinuously("background");

    }
}
