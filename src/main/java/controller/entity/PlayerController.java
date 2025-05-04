package controller.entity;

import model.entity.Player;
import model.items.Item;
import view.HUD.HUDView;
import view.game.elements.PlayerView;
import view.audio.SoundManager;

import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
public class PlayerController {
    private final Player player;
    private final PlayerView playerView;
    private final HUDView hudView;

    private final int delay = 1000 / 60;

    private int dx = 0, dy = 0;

    private final Timer movementTimer;

    private final Timer actionTimer;

    public PlayerController(PlayerView playerView, HUDView hudView) {
        this.player = Player.getInstance();
        this.playerView = playerView;
        this.hudView = hudView;

        // Defining a loop for updating the position of the player
        movementTimer = new Timer(delay, _ -> updatePlayer());

        // Defining a loop for actions that take time to complete (e.g. breaking blocks)
        actionTimer = new Timer(100, _ -> performAction());

        //TODO: removed sounds completely cos it broke shit. So either make it work with some listener called from breakingTools and then that listener makes to sound call. Or forget completely about this sound
        //TODO: I mean a Listener just like all the other ones in Controller.
    }

    public void updateMoving(int ddx, int ddy){
        // Updates the movement status of the player
        // ddx and ddy are the changes in the movement "vector" (dx, dy)
        // Adding them to the current dx and dy essentially updates the vector correspondingly to the pressed/released key
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

        // Normalizing the diagonal movement
        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = (double) dx / length;
        double normalizedDy = (double) dy / length;

        player.moveUntil(normalizedDx, normalizedDy);

        // Providing playerView with the movement direction
        double angle = Math.atan2(dy, dx);
        playerView.update(true, angle);

    }

    private void performAction() {
        Item selected = player.getInventory().getSelectedItem();

         if (selected != null && selected.getCount() > 0) {
             selected.use();
             SoundManager.getInstance().playUseSound(selected);
         }

        double angle = player.getFacingAngle();
        player.lockDirection(angle);
        playerView.startUse();

        int tx = player.getWorldPos().getFocusedTileX();
        int ty = player.getWorldPos().getFocusedTileY();
        hudView.slashAt(tx, ty);
    }

    public void doAction() {
        // Starts an action
        if (!actionTimer.isRunning()){
            performAction();
            actionTimer.start();
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

}
