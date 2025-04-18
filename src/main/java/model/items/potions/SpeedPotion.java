package model.items.potions;

import model.entity.Player;

public class SpeedPotion extends Potion {
    private int speedBoost;

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

    public void applyEffect(Player player) {
        player.setSpeed(player.getSpeed() + speedBoost);
    }
}
