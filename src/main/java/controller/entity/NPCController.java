package controller.entity;


import model.entity.mobs.Mob;
import model.entity.mobs.MobManager;
import view.audio.SoundManager;
import view.game.elements.MobView;

public class NPCController extends MobController {


    public NPCController(MobView mobView) {
        super(mobView);
        this.mobs = MobManager.getInstance().getNpcs();

        for (Mob m : this.mobs) {
            // Playing corresponding sounds from damage and death callbacks
            m.onDamage(v -> {
                SoundManager.getInstance().playSound("villager3");
            });
            m.onDeath(v -> {
                SoundManager.getInstance().playSound("villager1");
            });
        }
    }


    @Override
    protected void act(Mob mob) {
        wander(mob);
    }
}
