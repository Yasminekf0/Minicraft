package model.items.potions;

import model.entity.Player;
import model.items.Item;


public abstract class Potion extends Item {
    public Potion() {
        super("Potion");
        this.count = 1;
    }

    public abstract void applyEffect(Player player); //use
}
