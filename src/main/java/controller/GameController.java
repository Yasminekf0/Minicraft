package controller;

import controller.entity.EnemyController;
import controller.entity.NPCController;
import controller.entity.PlayerController;
import model.world.DayCycleManager;
import model.entity.Player;
import view.HUD.HUDView;
import view.audio.SoundManager;
import view.game.core.MainView;
import view.game.core.GameView;
import view.menus.DeathView;
import view.menus.OptionsView;

import javax.swing.*;

public class GameController {

    private final GameView gameView;

    private final DayCycleManager dayCycleManager;

    @SuppressWarnings("FieldCanBeLocal")
    private final int FPS = 60;
    private boolean gamePaused = false;
    private final OptionsView optionsView;
    private final DeathView deathView;
    private final HUDView hudView;
    private final NPCController npcController;
    private final EnemyController enemyController;

    public GameController(MainView mainView) {
        this.gameView = new GameView();
        this.hudView = new HUDView();
        this.deathView = mainView.getDeathView();
        this.optionsView = mainView.getOptionsView();

        this.dayCycleManager = DayCycleManager.getInstance();
        this.npcController = new NPCController(gameView.getNpcView());
        this.enemyController = new EnemyController(gameView.getEnemyView());

        PlayerController playerController = new PlayerController(gameView.getPlayerView(), hudView);
        KeyController _ = new KeyController(this, playerController);
        OptionsController _ = new OptionsController(optionsView, this);

        mainView.startGameView(gameView, hudView);

        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        startGameLoop();
    }



    private void startGameLoop() {

        int delay = 1000 / FPS; // ms per frame
        Timer timer = new Timer(delay, _ -> {

            if (!gamePaused) {
                dayCycleManager.tick();
                npcController.tick();
                enemyController.tick();
                gameView.repaint();

                if (Player.getInstance().getHealth() <= 0) {
                    gamePaused = true;
                    deathView.setVisible(true);
                    deathView.requestFocusInWindow();
                    SoundManager.getInstance().playSound("dead");
                }
            }
        });
        timer.start();
    }

    GameView getGameView(){
        return gameView;
    }

    void pauseGame() {
        gamePaused = true;
        optionsView.setVisible(true);
        optionsView.requestFocusInWindow();
    }

    void resumeGame() {
        gamePaused = false;
        optionsView.setVisible(false);
        gameView.requestFocusInWindow();
    }

    HUDView getHudView() {
        return hudView;
    }
}
