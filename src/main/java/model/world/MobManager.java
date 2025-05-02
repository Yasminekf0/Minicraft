package model.world;

import model.entity.Enemy;
import model.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public void removeEnemy(Enemy enemy) {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == enemy) {
                enemies[i] = null;
                break;
            }
        }
    }

    public List<Enemy> getActiveEnemies() {
        return Arrays.stream(enemies)
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }
}
