package controller.entity;


import model.entity.mobs.Mob;
import model.entity.mobs.MobManager;
import view.game.elements.MobView;

public class NPCController extends MobController {
    //private final NPC[] npcs;


    public NPCController(MobView mobView) {
        super(mobView);
        this.mobs = MobManager.getInstance().getNpcs();
    }


    @Override
    protected void act(Mob mob) {
        wander(mob);
    }
}
