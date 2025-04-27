package controller;

import model.entity.Player;
import model.items.tools.Axe;
import model.items.tools.Tool;
import view.PlayerView;

import model.items.Item;
import model.items.tools.Tool;

import javax.swing.*;

public class PlayerController {
    private final Player player;
    private final PlayerView playerView;

    private final int delay = 1000 / 60;

    private int dx = 0, dy = 0;

    private final Timer movementTimer;

    private final Timer actionTimer;

    PlayerController(PlayerView playerView){
        this.player = Player.getInstance();
        this.playerView = playerView;

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
        }
    }

    public void stopAction(){
        actionTimer.stop();
    }

    public void switchInventorySection() {
        player.getInventory().cycleCurrentSection();

        String section = player.getInventory().getCurrentSection();
        System.out.println("Section → " + section);

        Item selected = player.getInventory().getSelectedItem();
        System.out.println("Selected Item → " + selected);

        if (selected instanceof Tool) {
            // cast to Tool and print its material
            String mat = ((Tool) selected).getMaterial();
            System.out.println("Tool material → " + mat);
        }
    }

    public void switchSelectedItem() {
        player.getInventory().cycleCurrentItem();

        Item selected = player.getInventory().getSelectedItem();
        System.out.println("Selected Item → " + selected);

        if (selected instanceof Tool) {
            // cast to Tool and print its material
            String mat = ((Tool) selected).getMaterial();
            System.out.println("Tool material → " + mat);
        }
    }

    public void upgradeSelectedTool(){
        player.getInventory().upgradeTool();
    }
}
