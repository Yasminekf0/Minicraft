package model.items.tools;

import model.entity.mobs.Enemy;
import model.entity.mobs.Mob;
import model.entity.Player;
import model.entity.mobs.MobManager;
import view.audio.SoundManager;

import static view.settings.ScreenSettings.tileSize;

public class Sword extends Tool {
    @SuppressWarnings("FieldCanBeLocal")
    private final int damage = 10; //DEPENDS ON MATERIAL
    public Sword() {
        super();
    }

    private boolean canHit(double mobX, double mobY){
        Player player = Player.getInstance();
        double directionX = player.getWorldPos().getDirectionFacing().getX();
        double directionY = player.getWorldPos().getDirectionFacing().getY();

        double playerX = player.getWorldPos().getX();
        double playerY = player.getWorldPos().getY();

        double diffX = mobX - playerX; // Positive when Player on the right
        double diffY = mobY - playerY; // Positive when Player on bottom

        double crossProduct = diffX*directionX + diffY*directionY;


        double diffVectorMagnitude = Math.sqrt(diffX*diffX + diffY*diffY);

        double directionVectorMagnitude = Math.sqrt(directionX*directionX + directionY*directionY);

        double angle = Math.abs(Math.acos(crossProduct/(diffVectorMagnitude*directionVectorMagnitude)));


        return (angle < Math.PI/3  && diffVectorMagnitude<tileSize*2);


    }

    @Override
    public void use() {

        Mob[] targets = MobManager.getInstance().getMobs();



        for (Mob mob : targets) {

            if (mob == null) continue;
            double mobX = mob.getWorldPos().getX();
            double mobY = mob.getWorldPos().getY();
            if (canHit(mobX,mobY)) {
                mob.takeDamage(damage+material.addedPower);

                if ( mob.getHealth() <=0) {
                    MobManager.getInstance().removeMob(mob);
                }

                // TODO: MATEO, fix these please
                if (mob instanceof Enemy) {
                    if (( mob).skinType == 1) {
                        SoundManager.getInstance().playSound("zombieDamage");
                    } else {
                        SoundManager.getInstance().playSound("skeletonDamage");
                    }
                }

                if (mob instanceof Enemy && mob.getHealth() <= 0) {
                    MobManager.getInstance().removeMob(mob);
                    if ((mob).skinType == 2) {
                        SoundManager.getInstance().playSound("zombieDeath");
                    } else {
                        SoundManager.getInstance().playSound("skeletonDeath");
                    }

                }
            }
        }
    }
}

