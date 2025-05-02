package model.items.tools;

import model.entity.npcs.Enemy;
import model.entity.Entity;
import model.entity.npcs.NPC;
import model.entity.Player;
import model.entity.npcs.MobManager;
import model.world.World;
import view.SoundManager;

@SuppressWarnings("FieldCanBeLocal")
public class Sword extends Tool {
    private final int damage = 10; //DEPENDS ON MATERIAL
    public Sword() {
        super();
    }

    public int getDamage() {
        return damage;
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
                e.takeDamage(10);

                // TODO: And the same here too
                if (e instanceof Enemy) {
                    if (((Enemy) e).skinType) {
                        SoundManager.getInstance().playSound("zombieDamage");
                    } else {
                        SoundManager.getInstance().playSound("skeletonDamage");
                    }
                }

                if (e instanceof Enemy && e.getHealth() <= 0) {
                    MobManager.getInstance().removeEnemy((Enemy) e);
                    if (((Enemy) e).skinType){
                        SoundManager.getInstance().playSound("zombieDeath");
                    } else {
                        SoundManager.getInstance().playSound("skeletonDeath");
                    }
                    System.out.println("Enemy defeated!");
                } else if (e instanceof NPC && e.getHealth() <=0) {
                    ((NPC) e).kill();
                }
                return;
            }
        }
    }
}
