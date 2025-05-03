package model.items.tools;

import model.entity.npcs.Enemy;
import model.entity.Entity;
import model.entity.npcs.Mob;
import model.entity.npcs.NPC;
import model.entity.Player;
import model.entity.npcs.MobManager;
import model.world.World;
import view.audio.SoundManager;

import static view.settings.ScreenSettings.tileSize;

@SuppressWarnings("FieldCanBeLocal")
public class Sword extends Tool {
    private final int damage = 10; //DEPENDS ON MATERIAL
    public Sword() {
        super();
    }

    public int getDamage() {
        return damage;
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


        System.out.print("Cross Product: ");
        System.out.println(crossProduct);
        double diffVectorMagnitude = Math.sqrt(diffX*diffX + diffY*diffY);
        System.out.print("diffVectorMagnitude: ");
        System.out.println(diffVectorMagnitude);

        double directionVectorMagnitude = Math.sqrt(directionX*directionX + directionY*directionY);
        System.out.print("directionVectorMagnitude: ");
        System.out.println(directionVectorMagnitude);


        double angle = Math.abs(Math.acos(crossProduct/(diffVectorMagnitude*directionVectorMagnitude)));
        System.out.print("angle: ");
        System.out.println(Math.toDegrees(angle));
        System.out.println(" ");
        System.out.println(" ");System.out.println(" ");System.out.println(" ");





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
                    System.out.println("Enemy defeated!");

                }
            }
        }
    }
}
