package model.items.potions;

public class SpeedPotion extends Potion {
    private int speedBoost;

    public SpeedPotion() {
        super();
        this.count = 1;
        this.speedBoost = 5;
    }


    public int getSpeedBoost() {
        return speedBoost;
    }
    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }
}
