package controller;

import model.entity.Player;
import model.world.World;
import view.GameView;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private final Player player;
    private final GameView gameView;
    private final KeyController keyController;

    private final int FPS = 60;

    public GameController( Player player, GameView gameView, KeyController keyController) {
        this.player = player;
        this.gameView = gameView;
        this.keyController = keyController;

        // Make sure the view can receive keyboard input
        gameView.addKeyListener(keyController);
        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        startGameLoop();
    }

    private void startGameLoop() {
        int delay = 1000 / FPS; // ms per frame
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                gameView.repaint();
            }
        });
        timer.start();
    }

    private void update() {
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

        // Normalize diagonal movement
        if (moving) {
            double length = Math.sqrt(dx * dx + dy * dy);
            dx /= length;
            dy /= length;
            player.move(dx, dy);
        }

        // Update the PlayerView's animation
        gameView.getPlayerView().update(moving, angle);
    }
}
