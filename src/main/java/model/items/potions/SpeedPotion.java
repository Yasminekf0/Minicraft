package model.items.potions;

import model.entity.Player;

import javax.swing.Timer;

public class SpeedPotion extends Potion {
    private final int speedBoost;

    public SpeedPotion() {
        super();
        this.speedBoost = 5;
    }


    public void use() {
        Player player = Player.getInstance();

        if (count > 0) {
            player.setSpeed(player.getSpeed() + speedBoost);

            count--;

            Timer revert = new Timer(15_000, e -> {
                player.setSpeed(player.getDefaultSpeed());
                ((Timer)e.getSource()).stop();
            });
            revert.setRepeats(false);
            revert.start();
        }
    }
}
