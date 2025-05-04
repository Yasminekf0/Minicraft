package model.items.potions;

import model.items.Item;


public abstract class Potion extends Item {
    public Potion() {
        super("Potions");
        this.count = 0;
    }
}
