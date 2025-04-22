package model;

import model.entity.MobController;
import model.entity.Player;
import model.position.WorldPosition;
import model.world.World;
import view.ScreenSettings;

import java.util.ArrayList;

public class MobManager {

    private final int innerSpawnRadius = ScreenSettings.tileSize * 20;
    private final int outerSpawnRadius = ScreenSettings.tileSize * 30;
    private final int despawnRadius = ScreenSettings.tileSize * 40;
    private final double mobsPerSecond = 0.1;
    private final double spawnProbability;

    private final DayCycleManager dayCycleManager;
    private final ArrayList<MobController> mobControllers;

    public MobManager(DayCycleManager dayCycleManager, int delay) {
        this.dayCycleManager = dayCycleManager;
        spawnProbability = delay * 0.001 * mobsPerSecond;
        mobControllers = new ArrayList<>();
    }

    public void tick() {
        spawnMobs();
        // despawn mobs that are too far
        // update all existing mobs
    }

    private void spawnMobs() {
        if (dayCycleManager.isNight() && Math.random() < spawnProbability) {

            try {
                MobController mobController = new MobController(getSpawnPoint());
                mobControllers.add(mobController);
            } catch (NoSpawnpointFoundException _) {}
        }
    }

    private WorldPosition getSpawnPoint() throws NoSpawnpointFoundException {
        WorldPosition playerPos = Player.getInstance().getWorldPos();

        // Try spawing 10 times, then give up
        for (int i = 0; i < 10; i++) {
            double spawnDistance = Math.random() * (outerSpawnRadius - innerSpawnRadius) + innerSpawnRadius;
            double spawnAngle = Math.random() * 2*Math.PI;
            double relativeSpawnX = Math.cos(spawnAngle) * spawnDistance;
            double relativeSpawnY = Math.sin(spawnAngle) * spawnDistance;
            WorldPosition spawnPos = new WorldPosition(playerPos.getX() + relativeSpawnX, playerPos.getY() + relativeSpawnY);
            if (!(World.getInstance().isWalkable(spawnPos.getTileXPos(),spawnPos.getTileYPos())) |
                    World.getInstance().hasBlock(spawnPos.getTileXPos(),spawnPos.getTileYPos())) {
                System.out.println(relativeSpawnX);
                System.out.println(relativeSpawnY);
                System.out.println();
                return spawnPos;
            }
        }

        throw new NoSpawnpointFoundException();
    }

}

class NoSpawnpointFoundException extends Exception {}
