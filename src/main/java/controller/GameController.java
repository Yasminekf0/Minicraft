package controller;

import model.DayCycleManager;
import model.entity.Player;
import model.world.World;
import view.*;
import controller.GameSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.GameSettings.FPS;

public class GameController {

    private final GameView gameView;

    private final DayCycleManager dayCycleManager;

    @SuppressWarnings("FieldCanBeLocal")
    private final int FPS = 60;
    private boolean gamePaused = false;
    private OptionsView optionsView;
    private DeathView deathView;
    private final HUDView hudView;
    private final NPCController npcController;
    private final EnemyController enemyController;

    public GameController(GameView gameView, NPCView npcView, EnemyView enemyView, HUDView hudView) {
        this.gameView = gameView;
        this.hudView = hudView;

        this.dayCycleManager = DayCycleManager.getInstance();
        this.npcController = new NPCController(npcView);
        this.enemyController = new EnemyController(enemyView);



        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        startGameLoop();
    }

    // Called from MainView after instantiating OptionsView.
    public void setOptionsView(OptionsView optionsView) {
        this.optionsView = optionsView;
    }

    public void setDeathView(DeathView deathView) { this.deathView = deathView; }

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

    public HUDView getHudView() {
        return hudView;
    }
}
