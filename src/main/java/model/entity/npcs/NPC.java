package model.entity.npcs;

public class NPC extends Mob {
    private static NPC instance;
    private boolean alive = true;
    public NPC(){
        super();
        instance = this;
        this.speed = 1;
        this.health = 10;
        this.maxHealth = 10;

    }


    public static NPC getInstance() {
        if (instance == null) {
            instance = new NPC();
        }
        return instance;
    }


    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }
}
