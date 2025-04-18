package controller;

import model.entity.Player;
import view.PlayerView;

import javax.swing.*;

public class PlayerController {
    private final Player player;
    private final PlayerView playerView;

    private final int delay = 1000 / 60;

    private double dx = 0, dy = 0;

    private final Timer timer;

    PlayerController(Player player, PlayerView playerView){
        this.player = player;
        this.playerView = playerView;

        timer = new Timer(delay, _ -> {
            updatePlayer();
        });

    }

    void updateMoving(int ddx, int ddy){

        dx += ddx;
        dy += ddy;

        if (dx == 0 && dy == 0) {
            timer.stop();
            playerView.update(false, 0);
        } else if (!timer.isRunning()){
            timer.start();
        }
    }

    private void updatePlayer() {
        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = dx / length;
        double normalizedDy = dy / length;
        player.moveUntil(normalizedDx, normalizedDy);

        double angle = Math.atan2(dy, dx);
        playerView.update(true, angle);
    }



}
