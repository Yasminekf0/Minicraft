package model.items.blocks;

import model.items.Item;

public class Block extends Item {
    private boolean isBreakable;

    public Block(String name, boolean isBreakable) {
        super(name, "Block");
        this.isBreakable = isBreakable;
    }

    public boolean isBreakable() {
        return isBreakable;
    }
}
