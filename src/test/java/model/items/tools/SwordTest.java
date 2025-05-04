package model.items.tools;

import model.entity.Player;
import model.entity.mobs.Enemy;
import model.entity.mobs.MobManager;
import model.position.WorldPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

// STill needs coverage
public class SwordTest {

   private Sword sword;
    Enemy enemy;
    Player player;
//
//    @BeforeEach
//    public void setUp() {
//        sword = new Sword();
//    }
//    @Test
//    public void testSwordForceCoverage() {
//        sword.use(); }
@BeforeEach
public void setup() {
    sword = new Sword();
    player = Player.getInstance();
    enemy = new Enemy();


    player.setHealth(10);
    player.setWorldPos(new WorldPosition(10000, 10000));
    player.getWorldPos().updateDirection(1, 0); // Facing right

    enemy.setWorldPos(new WorldPosition(10000 + 5, 10000));
    enemy.setHealth(20);


}

    @Test
    public void testSwordUseDamagesEnemy() {
        int initialHealth = enemy.getHealth();
        sword.use();
        int damage = sword.getDamage();
        assertTrue(enemy.getHealth() == initialHealth - damage || !isEnemyAlive(enemy), "Enemy should be damaged or removed");

    }

    private boolean isEnemyAlive(Enemy e) {
        for (var mob : MobManager.getInstance().getEnemies()) {
            if (mob == e) return true;
        }
        return false;
    }

    public void enemyTooFarAway() {
        int initialHealth = enemy.getHealth();

        enemy.setWorldPos(new WorldPosition(10000 + 1000, 10000));
        sword.use();
        assertFalse(enemy.getHealth() < initialHealth || !isEnemyAlive(enemy), "Enemy should be damaged or removed");

    }
}
