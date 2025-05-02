package controller;

import model.entity.NPC;
import model.entity.Player;
import model.position.WorldPosition;
import model.world.World;
import view.NPCView;
import view.PlayerView;
import view.ScreenSettings;

import javax.swing.*;
import java.util.Random;

public class NPCController {
    private final NPC npc;
    private final NPCView npcView;

    private final int delay = 1000 / 60;
    private final double speed = 0.1;

    private int pathStage = 0; // 0 = right, 1 = down, 2 = left, 3 = up
    private int stepsRemaining = 100;

    private double dx = 1, dy = 0;

    private final int innerSpawnRadius = ScreenSettings.tileSize * 20;
    private final int outerSpawnRadius = ScreenSettings.tileSize * 30;



    private final int despawnRadius = ScreenSettings.tileSize * 40;
    private final double mobsPerSecond = 0.1;
    private final double spawnProbability = 0;
    private boolean spawned = false;

    NPCController(NPCView npcView) {
        this.npc = NPC.getInstance();
        this.npcView = npcView;


    }

    public void tick() {
        if (!spawned) {
            try {
                WorldPosition spawnPos = getSpawnPoint();
                npc.setWorldPos(spawnPos);
                spawned = true;
            } catch (NoSpawnpointFoundException e) {
                System.err.println("Failed to find spawn point for NPC.");
                return;
            }
        }
        updateNPC();
        // despawn mobs that are too far
        // update all existing mobs

    }

    void updateNPC() {
        Random rand = new Random();
        if (stepsRemaining <= 0) {
            pathStage = rand.nextInt(8);//(pathStage + 1) % 4; // loop through 0 -> 1 -> 2 -> 3 -> 0

            stepsRemaining = 300; // reset steps for new side
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
        npc.moveUntil(speed * normalizedDx, speed * normalizedDy);

        double angle = Math.atan2(dy, dx);
        npcView.update(true, angle);
        stepsRemaining--;
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



    /*public void doAction() {
        if (!actionTimer.isRunning()){
            npc.setAction();
            actionTimer.start();
        }
    }

    public void stopAction(){
        actionTimer.stop();
    }*/




//Random rand = new Random();
//int x = 0;
/*while (x<100) {
            int i = rand.nextInt(100) + 1; //random num 1 to 100

            if (i < 51) {
                ddx = 0;
                ddy = 1;
            } else {
                ddx = 0;
                ddy = -1;
            }
            x++;
        }*/
