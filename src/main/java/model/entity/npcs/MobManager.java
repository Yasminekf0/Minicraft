package model.entity.npcs;

public class MobManager {

    private static MobManager inst = new MobManager();
    private final Enemy[] enemies = { new Enemy(), new Enemy(), new Enemy()};


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

}
