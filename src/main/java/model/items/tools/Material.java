package model.items.tools;

enum Material {
    WOOD(0),
    STONE(10),
    IRON(15),
    DIAMOND(25);

    final int addedPower;

    Material(int addedPower){
        this.addedPower = addedPower;
    }



    public Material upgrade() {
        return switch (this) {
            case WOOD -> STONE;
            case STONE -> IRON;
            case IRON -> DIAMOND;
            case DIAMOND -> DIAMOND;
        };
    }
}
