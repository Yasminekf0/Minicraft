package controller;

import model.entity.Player;
import view.GameView;
import view.OptionsView;

import javax.swing.*;
import java.awt.*;

public class GameController {
    private final Player player;
    private final GameView gameView;
    private final KeyController keyController;

    private final int FPS = 60;
    private boolean gamePaused = false;
    private boolean escPressedPreviously = false;
    // Reference to the OptionsView overlay.
    private OptionsView optionsView;

    public GameController( Player player, GameView gameView, KeyController keyController) {
        this.player = player;
        this.gameView = gameView;
        this.keyController = keyController;

        gameView.addKeyListener(keyController);
        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        startGameLoop();
    }

    // Called from MainView after instantiating OptionsView.
    public void setOptionsView(OptionsView optionsView) {
        this.optionsView = optionsView;
    }

    private void startGameLoop() {
        int delay = 1000 / FPS;
        Timer timer = new Timer(delay, e -> {
            update();
            gameView.repaint();
        });
        timer.start();
    }

    private void update() {
        boolean currentEscPressed = keyController.isEscPressed();

        // When Escape is pressed, toggle the overlay.
        if (currentEscPressed && !escPressedPreviously) {
            if (!gamePaused && optionsView != null) {
                gamePaused = true;
                optionsView.setVisible(true);
                optionsView.requestFocusInWindow();
            } else if (gamePaused && optionsView != null) {
                gamePaused = false;
                optionsView.setVisible(false);
                gameView.requestFocusInWindow();
            }
        }
        escPressedPreviously = currentEscPressed;

        // Update game logic only if not paused.
        if (!gamePaused) {
            double dx = 0, dy = 0;
            if (keyController.isUpPressed()) {
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
            }
            boolean moving = (dx != 0 || dy != 0);
            double angle = Math.atan2(dy, dx);
            if (moving) {
                double length = Math.sqrt(dx * dx + dy * dy);
                dx /= length;
                dy /= length;
                player.move(dx, dy);
            }
            gameView.getPlayerView().update(moving, angle);
        }
    }

    // Called from OptionsController when resuming.
    public void resumeGame() {
        gamePaused = false;
        if(optionsView != null)
            optionsView.setVisible(false);
        keyController.resetKeyState();
        gameView.requestFocusInWindow();
    }
}
