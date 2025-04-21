package controller;

import model.DayCycleManager;
import model.MobManager;
import model.entity.Player;
import model.world.World;
import view.GameView;
import view.HUDView;
import view.OptionsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    private final GameView gameView;

    private final DayCycleManager dayCycleManager;
    private final MobManager mobManager;

    @SuppressWarnings("FieldCanBeLocal")
    private final int FPS = 60;
    private boolean gamePaused = false;
    // Reference to the OptionsView overlay.
    private OptionsView optionsView;
    private final HUDView hudView;

    public GameController(GameView gameView, HUDView hudView) {
        this.gameView = gameView;
        this.hudView = hudView;

        this.dayCycleManager = new DayCycleManager(gameView.getNightFilterView(), 1000/FPS);
        this.mobManager = new MobManager(1000/FPS);

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
            mobManager.tick();

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
