package controller.entity;

import model.entity.mobs.Mob;
import view.game.elements.MobView;

import java.util.Random;

public abstract class MobController {
    protected Mob[] mobs;
    private final MobView mobView;
    protected double angle = 0;
    protected final Random rand = new Random();
    public MobController(MobView mobView) {
        this.mobView = mobView;
    }

    public void tick() {
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i] == null) continue;
            act(mobs[i]);
            mobView.update(i, true, angle);
        }

    }

    protected abstract void act(Mob mob);


    protected void wander(Mob mob) {
        if (--mob.wanderSteps <= 0) {
            mob.wanderSteps = 50 + rand.nextInt(100);
            mob.pathStage = rand.nextInt(8);
        }
        double dx = 0, dy = 0;
        switch (mob.pathStage) {
            case 0 -> { dx = 1; dy = 0; }
            case 1 -> { dx = 0; dy = 1; }
            case 2 -> { dx = -1; dy = 0; }
            case 3 -> { dx = 0; dy = -1; }
            case 4 -> { dx = 0.5; dy = 0.5; }
            case 5 -> { dx = -0.5; dy = -0.5; }
            case 6 -> { dx = -0.5; dy = 0.5; }
            case 7 -> { dx = 0.5; dy = -0.5; }
        }

        mob.dx = dx;
        mob.dy = dy;

        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = dx / length;
        double normalizedDy = dy / length;

        mob.moveUntil(normalizedDx, normalizedDy);
        this.angle = Math.atan2(mob.dy, mob.dx);

    }

}
