package model.items.tools;

import model.items.Item;



public abstract class Tool extends Item {
    Material material;

    public Tool() {
        super("Tool");
        this.material = Material.WOOD;
        this.count = 1;
    }

    public String getMaterial() {
        return material.toString();
    }

    public void upgrade() {
        this.material = this.material.upgrade();
    }
}

