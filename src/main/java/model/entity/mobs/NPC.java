package model.entity.mobs;

public class NPC extends Mob {

    // private boolean alive = true;
    public NPC(){
        super();

        this.speed = 1;
        this.health = 25;
        this.maxHealth = 25;
        this.skinType = 0;

    }
    public int getSkinType() {
        return skinType;
    }

}
