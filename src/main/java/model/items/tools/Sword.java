package model.items.tools;

import model.entity.Enemy;
import model.entity.Entity;
import model.entity.NPC;
import model.entity.Player;
import model.world.MobManager;
import model.world.World;

public class Sword extends Tool {
    private final int breakingPower = 10; //DEPENDS ON MATERIAL
    public Sword() {
        super();
    }

    @Override
    public void use() {
        Player player = Player.getInstance();
        World world = World.getInstance();
        int targetX = player.getWorldPos().getFocusedTileX();
        int targetY = player.getWorldPos().getFocusedTileY();

        Enemy[] enemies = MobManager.getInstance().getEnemies();

        Entity[] targets = new Entity[enemies.length + 1];

        targets[0] = NPC.getInstance();
        System.arraycopy(enemies, 0, targets, 1, enemies.length);


        for (Entity e : targets) {

            if (e == null) continue;
            int ex = e.getWorldPos().getTileXPos();
            int ey = e.getWorldPos().getTileYPos();
            if (ex == targetX && ey == targetY) {
                e.takeDamage(this.breakingPower);
                if (e instanceof Enemy && e.getHealth() <= 0) {
                    MobManager.getInstance().removeEnemy((Enemy) e);
                    System.out.println("Enemy defeated!");
                } else if (e instanceof NPC && e.getHealth() <=0) {
                    ((NPC) e).kill();
                }
                return;
            }
        }
    }
}
