package controller;

import model.entity.Entity;
import model.entity.Player;
import model.entity.npcs.Enemy;
import model.entity.npcs.Mob;
import model.entity.npcs.MobManager;
import model.position.WorldPosition;
import model.world.World;
import view.game.elements.EnemyView;
import view.game.elements.MobView;
import view.game.elements.NPCView;

import java.util.Random;

import static view.settings.ScreenSettings.tileSize;

public abstract class MobController {
    protected Mob[] mobs;
    private final MobView mobView;
    protected final int innerSpawnRadius = tileSize * 20;
    protected final int outerSpawnRadius = tileSize * 30;
    protected boolean spawned = false;
    protected double angle = 0;
    protected final Random rand = new Random();
    public MobController(MobView mobView) {
        this.mobs = MobManager.getInstance().getMobs();
        this.mobView = mobView;
    }

    public void tick() {
        if (!spawned) {
            spawnAll();
            spawned = true;
        }

        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i] == null) continue;
            act(mobs[i]);
            mobView.update(i, true, angle);
        }

    }

    protected void spawnAll() {
        for (int i = 0; i < mobs.length; ++i) {
            try {
                mobs[i].setWorldPos(getSpawnPoint());
            } catch (NoSpawnpointFoundException e) {
                System.err.println("Failed to find spawn point for Mob " + i);
            }
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
        //e.getCollisionChecker().checkPlayer(e, 0, 0);
        this.angle = Math.atan2(mob.dy, mob.dx);

    }

    protected WorldPosition getSpawnPoint() throws NoSpawnpointFoundException {
        WorldPosition playerPos = Player.getInstance().getWorldPos();
        World world = World.getInstance();

        // Try spawing 10 times, then give up
        for (int i = 0; i < 30; i++) {
            double spawnDistance = Math.random() * (outerSpawnRadius - innerSpawnRadius) + innerSpawnRadius;
            double spawnAngle = Math.random() * 2 * Math.PI;

            double relativeSpawnX = Math.cos(spawnAngle) * spawnDistance;
            double relativeSpawnY = Math.sin(spawnAngle) * spawnDistance;

            WorldPosition spawnPos = new WorldPosition(
                    playerPos.getX() + relativeSpawnX,
                    playerPos.getY() + relativeSpawnY
            );

            int tileX = spawnPos.getTileXPos();
            int tileY = spawnPos.getTileYPos();

            // Check if tile is walkable and doesn't have a block
            if (world.isWalkable(tileX, tileY) && !world.hasBlock(tileX, tileY)) {
                System.out.println("x" + tileX);
                System.out.println("y" + tileY);
                return spawnPos;
            }
        }

        throw new NoSpawnpointFoundException();
    }

    class NoSpawnpointFoundException extends Exception {}
}
