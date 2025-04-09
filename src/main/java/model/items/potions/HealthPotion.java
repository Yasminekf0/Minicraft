package model.items.potions;

public class HealthPotion extends Potion {

    private int healingAmount;
    public HealthPotion() {
        super();
        this.count = 1;
        this.healingAmount = 5;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
    }
}
