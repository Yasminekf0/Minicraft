package model.items.tools;

import model.items.Item;

public class Tool extends Item {
    private String material; // Wood,Stone, Iron, Diamond



    public Tool() {
        super("Tool");
        this.count = 1;
        this.material = "Wood";
    }

    public String getMaterial() {
        return material;
    }
    public void upgrade() {
        switch (material) {
            case "Wood":
                material = "Stone";
                break;
            case "Stone":
                material = "Iron";
                break;
            case "Iron":
                material = "Diamond";
                break;
            case "Diamond":
                break;
        }
    }
}
