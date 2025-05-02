package controller;

import model.entity.Player;
import view.HUDView;
import view.PlayerView;
import view.SoundManager;

import javax.swing.*;

public class PlayerController {
    private final Player player;
    private final PlayerView playerView;
    private final HUDView hudView;

    private final int delay = 1000 / 60;

    private int dx = 0, dy = 0;

    private final Timer movementTimer;

    private final Timer actionTimer;

    PlayerController(PlayerView playerView, HUDView hudView) {
        this.player = Player.getInstance();
        this.playerView = playerView;
        this.hudView = hudView;

        movementTimer = new Timer(delay, _ -> {
            updatePlayer();
        });

        actionTimer = new Timer(100, _ -> {
            player.use();
        });

    }

    void updateMoving(int ddx, int ddy){

        if (dx + ddx <= 1 && dx + ddx >= -1) dx += ddx;
        if (dy + ddy <= 1 && dy + ddy >= -1) dy += ddy;

        if (dx == 0 && dy == 0) {
            movementTimer.stop();
            playerView.update(false, 0);
        } else if (!movementTimer.isRunning()){
            movementTimer.start();
        }
    }

    private void updatePlayer() {
        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = (double) dx / length;
        double normalizedDy = (double) dy / length;
        player.moveUntil(normalizedDx, normalizedDy);

        double angle = Math.atan2(dy, dx);
        playerView.update(true, angle);

    }


    public void doAction() {
        if (!actionTimer.isRunning()){
            player.use();
            actionTimer.start();
            double currentAngle = player.getFacingAngle();
            player.lockDirection(currentAngle);
            playerView.startUse();

            int tx = player.getWorldPos().getFocusedTileX();
            int ty = player.getWorldPos().getFocusedTileY();
            hudView.slashAt(tx, ty);
        }
    }

    public void stopAction(){
        actionTimer.stop();
        player.unlockDirection();
        playerView.stopUse();
    }

    public void switchInventorySection() {
        player.getInventory().cycleCurrentSection();
    }

    public void switchSelectedItem() {
        player.getInventory().cycleCurrentItem();
    }

    public void upgradeSelectedTool(){
        player.getInventory().upgradeTool();
    }

    public void takeDamage(int damage) {
        player.takeDamage(damage);
        SoundManager.getInstance().playSound("damage");
    }
}
