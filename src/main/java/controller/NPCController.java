package controller;


import model.entity.npcs.Enemy;
import model.entity.npcs.Mob;
import model.entity.npcs.MobManager;
import model.entity.npcs.NPC;
import model.entity.Player;
import model.position.WorldPosition;
import model.world.World;
import view.game.elements.MobView;
import view.game.elements.NPCView;
import view.settings.ScreenSettings;

import java.util.Random;

import static view.settings.ScreenSettings.tileSize;

@SuppressWarnings("FieldCanBeLocal")
public class NPCController extends MobController {
    private final NPC[] npcs;


    NPCController(MobView mobView) {
        super(mobView);
        this.npcs = MobManager.getInstance().getNpcs();
    }


    @Override
    protected void act(Mob mob) {
        wander(mob);
    }
}
