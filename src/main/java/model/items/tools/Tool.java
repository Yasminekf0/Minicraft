package model.items.tools;

import model.items.Item;


enum Material {
    WOOD, STONE, IRON, DIAMOND;

    public Material upgrade() {
        return switch (this) {
            case WOOD -> STONE;
            case STONE -> IRON;
            case IRON -> DIAMOND;
            case DIAMOND -> DIAMOND;
        };
    }
}

public abstract class Tool extends Item {
    protected Material material;

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

    public abstract void use();
};

