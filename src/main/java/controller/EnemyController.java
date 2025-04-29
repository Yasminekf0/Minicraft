package controller;
import model.entity.Enemy;
import model.entity.Player;
import model.position.WorldPosition;
import model.world.World;
import view.EnemyView;
import view.PlayerView;
import view.ScreenSettings;

import javax.swing.*;
import java.util.Random;

public class EnemyController {
    //private final Enemy enemy;
    private final Enemy[] enemies;
    private final EnemyView enemyView;

    private final int delay = 1000 / 60;
    private final double speed = 0.1;

    //private int pathStage; // 0 = right, 1 = down, 2 = left, 3 = up
    //private int stepsRemaining = 100;
    private final int[] pathStage;
    private final int[] stepsRemaining;

    private double dx = 1, dy = 0;

    private final int innerSpawnRadius = ScreenSettings.tileSize * 20;
    private final int outerSpawnRadius = ScreenSettings.tileSize * 30;

    private final Random rand = new Random();

    private final int despawnRadius = ScreenSettings.tileSize * 40;
    private final double mobsPerSecond = 0.1;
    private final double spawnProbability = 0;
    private boolean spawned = false;

    EnemyController(EnemyView enemyView) {
        this.enemyView = enemyView;
        this.enemies = enemyView.getEnemies();
        pathStage      = new int[enemies.length];
        stepsRemaining = new int[enemies.length];

        for (int i = 0; i < enemies.length; i++) {
            pathStage[i]      = rand.nextInt(8);
            stepsRemaining[i] = rand.nextInt(200) + 100; // between 100–300 steps
        }


    }

    public void tick() {
        if (!spawned) {
            for (int i = 0; i < enemies.length; ++i) {
                try {
                    enemies[i].setWorldPos(getSpawnPoint());
                } catch (NoSpawnpointFoundException e) {
                    System.err.println("Failed to find spawn point for Enemy " + i);
                }
            }
            spawned = true;
        }



        for (int i = 0; i < enemies.length; ++i) {
            //updateEnemy(enemies[i], i);
            if (--stepsRemaining[i] <= 0) {
                pathStage[i]      = rand.nextInt(8);
                stepsRemaining[i] = rand.nextInt(200) + 100;
            }

            double dx = 0, dy = 0;
            switch (pathStage[i]) {
                case 0 -> { dx =  1; dy =  0; }  // right
                case 1 -> { dx =  0; dy =  1; }  // down
                case 2 -> { dx = -1; dy =  0; }  // left
                case 3 -> { dx =  0; dy = -1; }  // up
                case 4 -> { dx =  0.5; dy =  0.5; }
                case 5 -> { dx = -0.5; dy = -0.5; }
                case 6 -> { dx = -0.5; dy =  0.5; }
                case 7 -> { dx =  0.5; dy = -0.5; }
            }

            double length = Math.sqrt(dx * dx + dy * dy);
            double normalizedDx = (double) dx / length;
            double normalizedDy = (double) dy / length;
            enemies[i].moveUntil(speed * normalizedDx, speed * normalizedDy);

            double angle = Math.atan2(dy, dx);
            enemyView.update(i,true, angle); //*****************
            //stepsRemaining--;
        }

        //updateEnemy(enemies[0]);
        //updateEnemy(enemies[1]);
        //updateEnemy(enemies[2]);
                // despawn mobs that are too far
                // update all existing mobs

    }

    /*void updateEnemy(Enemy enemy, int index) {
        if (stepsRemaining <= 0) {
            pathStage = new Random().nextInt(8);;//(pathStage + 1) % 4; // loop through 0 -> 1 -> 2 -> 3 -> 0

            stepsRemaining = 300; // reset steps for new side ************************************************************
        }
        // movement
        double dx = 0;
        double dy = 0;
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
        double normalizedDx = (double) dx / length;
        double normalizedDy = (double) dy / length;
        enemy.moveUntil(speed * normalizedDx, speed * normalizedDy);

        double angle = Math.atan2(dy, dx);
        enemyView.update(index,true, angle); //*****************
        stepsRemaining--;
    }*/

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