package model.entity.npcs;

import model.entity.Entity;

import java.util.Arrays;

public class MobManager {

    private static MobManager inst = new MobManager();
    private final Enemy[] enemies ;
    private final NPC[] npcs ;

    private final Mob[] mobs;

    private MobManager(){
        inst = this;
        enemies = new Enemy[] { new Enemy(), new Enemy(), new Enemy()};
        npcs = new NPC[] {new NPC()};
        mobs = new Mob[enemies.length + npcs.length];
        System.arraycopy(enemies, 0, mobs, 1, enemies.length);
    }

    public static MobManager getInstance() {
        if (inst == null) {
            inst = new MobManager();
        }
        return inst; }
    public Enemy[] getEnemies() { return enemies; }

    public NPC[] getNpcs() {
        return npcs; }

    public Mob[] getMobs() {
        return mobs;
    }


    public void removeMob(Mob mob) {
        Mob[] array;

        if (mob instanceof Enemy){
            array =  enemies;
        }
        else if (mob instanceof NPC){
            array = npcs;
        }
        else return;


        for (int i = 0; i < array.length; i++) {
            if (array[i] == mob) {
                array[i] = null;
                break;
            }
        }

        System.arraycopy(enemies, 0, mobs, 1, enemies.length);
    }

}
