package controller;

import model.DayCycleManager;
import model.entity.Player;
import model.world.World;
import view.GameView;
import view.HUDView;
import view.NPCView;
import view.OptionsView;
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
    private final HUDView hudView;
    private final NPCController npcController;

    public GameController(GameView gameView, NPCView npcView, HUDView hudView) {
        this.gameView = gameView;
        this.hudView = hudView;

        this.dayCycleManager = DayCycleManager.getInstance();
        this.npcController = new NPCController(npcView);


        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        startGameLoop();
    }

    // Called from MainView after instantiating OptionsView.
    public void setOptionsView(OptionsView optionsView) {
        this.optionsView = optionsView;
    }

    private void startGameLoop() {

        int delay = 1000 / FPS; // ms per frame
        Timer timer = new Timer(delay, _ -> {

            dayCycleManager.tick();
            npcController.tick();

            gameView.repaint();
            hudView.repaint();
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
}
