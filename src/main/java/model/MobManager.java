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

    private ArrayList<MobController> mobControllers;

    public MobManager(int delay) {
        spawnProbability = delay * 0.001 * mobsPerSecond;
    }

    public void tick() {
        // spawn new mobs
        // despawn mobs that are too far
        // update all existing mobs
    }

    private void spawnMobs() {
        if (Math.random() < spawnProbability) {
            WorldPosition spawnPos = getSpawnPoint();
            if (spawnPos != null) { // TODO: Do proper exception handling!!!
                MobController mobController = new MobController(spawnPos);
                mobControllers.add(mobController);
            }
        }
    }

    private WorldPosition getSpawnPoint() {
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
                return spawnPos;
            }
        }

        // TODO: Throw an exception instead!!!
        return null;
    }

}
