package model.items.potions;

import model.entity.Player;
import model.items.Item;

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


    public Item use() {
        Player player = Player.getInstance();

        if (count > 0) {
            player.heal(healingAmount);
            count--;
        }

        return null;
    }
}
