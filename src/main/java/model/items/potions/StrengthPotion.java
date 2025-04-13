package model.items.potions;

public class StrengthPotion extends Potion {
    private int strengthBoost;

    public StrengthPotion() {
        super();
        this.count = 1;
        this.strengthBoost = 5;
    }


    public int getStrengthBoost() {
        return strengthBoost;
    }
    public void setStrengthBoost(int strengthBoost) {
        this.strengthBoost = strengthBoost;
    }
}
