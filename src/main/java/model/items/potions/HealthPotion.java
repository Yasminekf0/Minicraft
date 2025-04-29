package model.items.potions;

import model.entity.Player;

public class HealthPotion extends Potion {

    private int healingAmount;
    public HealthPotion() {
        super();
        this.healingAmount = 5;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
    }


    public void use() {
        Player player = Player.getInstance();

        if (count > 0) {
            player.heal(healingAmount);
            count--;
        }

    }
}
