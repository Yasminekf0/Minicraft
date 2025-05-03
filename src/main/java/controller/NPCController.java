package controller;

import model.entity.npcs.NPC;
import model.entity.Player;
import model.position.WorldPosition;
import model.world.World;
import view.game.elements.NPCView;
import view.settings.ScreenSettings;

import java.util.Random;

import static view.settings.ScreenSettings.tileSize;

@SuppressWarnings("FieldCanBeLocal")
public class NPCController {
    private final NPC npc;
    private final NPCView npcView;
    private int pathStage = 0; // 0 = right, 1 = down, 2 = left, 3 = up
    private int wanderSteps = 100;

    private double dx = 1, dy = 0;

    private final int innerSpawnRadius = tileSize * 20;
    private final int outerSpawnRadius = tileSize * 30;
    private boolean spawned = false;
    private double angle = 0;
    private final Random rand = new Random();

    NPCController(NPCView npcView) {
        this.npc = NPC.getInstance();
        this.npcView = npcView;


    }

    public void tick() {
        if (!spawned) {
            try {
                WorldPosition spawnPos = getSpawnPoint();
                // npc.setWorldPos(spawnPos);
                spawned = true;
            } catch (NoSpawnpointFoundException e) {
                System.err.println("Failed to find spawn point for NPC.");
                return;
            }
        }
        moveNPC();
        npcView.update(true, angle);

    }

    private void moveNPC() {
        if (--wanderSteps <= 0) {
            pathStage = rand.nextInt(8);//(pathStage + 1) % 4; // loop through 0 -> 1 -> 2 -> 3 -> 0
            wanderSteps = 50 + rand.nextInt(100); // reset steps for new side
        }

        switch (pathStage) {
            case 0: // Move right
                dx = 1;
                dy = 0;
                break;
            case 1: // Move down
                dx = 0;
                dy = 1;
                break;
            case 2: // Move left
                dx = -1;
                dy = 0;
                break;
            case 3: // Move up
                dx = 0;
                dy = -1;
                break;
            case 4: // Move right
                dx = 0.5;
                dy = 0.5;
                break;
            case 5: // Move down
                dx = -0.5;
                dy = -0.5;
                break;
            case 6: // Move left
                dx = -0.5;
                dy = 0.5;
                break;
            case 7: // Move up
                dx = 0.5;
                dy = -0.5;
                break;
        }

        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = dx / length;
        double normalizedDy = dy / length;
        npc.moveUntil(normalizedDx, normalizedDy);
        angle = Math.atan2(dy, dx);
    }

    private WorldPosition getSpawnPoint() throws NoSpawnpointFoundException {
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
}
class NoSpawnpointFoundException extends Exception {}
