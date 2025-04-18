package controller;

import model.entity.Player;
import model.world.World;
import view.GameView;
import view.OptionsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {

    private final GameView gameView;
    //private final KeyController keyController;

    @SuppressWarnings("FieldCanBeLocal")
    private final int FPS = 60;
    private boolean gamePaused = false;
    private boolean escPressedPreviously = false;
    // Reference to the OptionsView overlay.
    private OptionsView optionsView;

    public GameController(GameView gameView) {
        this.gameView = gameView;

        // Not sure what this line does
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
            //update();
            gameView.repaint();
        });
        timer.start();
    }

    private void update() {
        //boolean currentEscPressed = keyController.isEscPressed();

        // When Escape is pressed, toggle the overlay.
        /*if (currentEscPressed && !escPressedPreviously) {
            if (!gamePaused && optionsView != null) {
                gamePaused = true;
                optionsView.setVisible(true);
                optionsView.requestFocusInWindow();
            } else if (gamePaused && optionsView != null) {
                gamePaused = false;
                optionsView.setVisible(false);
                gameView.requestFocusInWindow();
            }
        }*/
        //escPressedPreviously = currentEscPressed;

        // Update game logic only if not paused.
        if (!gamePaused) {
            double dx = 0, dy = 0;
            /*if (keyController.isUpPressed()) {
                dy = -1;
            }
            if (keyController.isDownPressed()) {
                dy = 1;
            }
            if (keyController.isLeftPressed()) {
                dx = -1;
            }
            if (keyController.isRightPressed()) {
                dx = 1;
            }*/

            boolean moving = (dx != 0 || dy != 0);
            double angle = Math.atan2(dy, dx);

            if (moving) {
                double length = Math.sqrt(dx * dx + dy * dy);
                dx /= length;
                dy /= length;
                //player.moveUntil(dx, dy);
            }
            gameView.getPlayerView().update(moving, angle);
        }
    }

    public boolean isGamePaused() {
        return gamePaused;
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
