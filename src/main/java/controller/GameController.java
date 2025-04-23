package controller;

import model.DayCycleManager;
import view.GameView;
import view.HUDView;
import view.NPCView;
import view.OptionsView;

import javax.swing.*;

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

        this.dayCycleManager = new DayCycleManager(gameView.getNightFilterView(), 1000/FPS);
        this.npcController = new NPCController(npcView);


        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        startGameLoop();
    }

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
