package model.world;

import model.entity.Enemy;
import model.entity.Player;

public class MobManager {

    private static MobManager inst = new MobManager();
    private final Enemy[] enemies = { new Enemy(), new Enemy(), new Enemy()};
    /*this.enemies = new Enemy[3]; //***********************************
            this.enemies[0] = new Enemy();
            this.enemies[1] = new Enemy();
            this.enemies[2] = new Enemy();*/

    public static MobManager getInstance() {
        if (inst == null) {
            inst = new MobManager();
        }
        return inst; }
    public Enemy[] getEnemies() { return enemies; }
}
