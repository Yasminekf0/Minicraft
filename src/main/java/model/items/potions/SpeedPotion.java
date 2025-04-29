package model.items.potions;

import model.entity.Player;
import view.SoundManager;

import javax.swing.Timer;

public class SpeedPotion extends Potion {
    private int speedBoost;
    private int originalSpeed;

    public SpeedPotion() {
        super();
        this.speedBoost = 5;
    }


    public int getSpeedBoost() {
        return speedBoost;
    }
    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }

    public void use() {
        Player player = Player.getInstance();

        if (count > 0) {
            originalSpeed = player.getSpeed();

            player.setSpeed(player.getSpeed() + speedBoost);

            SoundManager.getInstance().playSound("potion");

            count--;

            Timer revert = new Timer(15_000, e -> {
                player.setSpeed(originalSpeed);
                ((Timer)e.getSource()).stop();
            });
            revert.setRepeats(false);
            revert.start();
        }
    }
}
