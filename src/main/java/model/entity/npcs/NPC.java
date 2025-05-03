package model.entity.npcs;

public class NPC extends Mob {

    // private boolean alive = true;
    public NPC(){
        super();

        this.speed = 1;
        this.health = 10;
        this.maxHealth = 10;
        this.skinType = 0;

    }
    public int getSkinType() {
        return skinType;
    }



    /*
    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

     */
}
