package controller;
import model.Pathfinder;
import model.entity.Enemy;
import model.entity.Player;
import model.position.WorldPosition;
import model.world.World;
import view.EnemyView;
import view.PlayerView;
import view.ScreenSettings;

import javax.swing.*;
import java.util.Random;

import static view.ScreenSettings.tileSize;

public class EnemyController {
    //private final Enemy enemy;
    private final Enemy[] enemies;
    private final EnemyView enemyView;

    private final int delay = 1000 / 60;
    private final double speed = 0.5;

    //private int pathStage; // 0 = right, 1 = down, 2 = left, 3 = up
    //private int stepsRemaining = 100;

    private double dx = 1, dy = 0;

    private final int innerSpawnRadius = tileSize * 20;
    private final int outerSpawnRadius = tileSize * 30;

    private final Random rand = new Random();

    private final int despawnRadius = tileSize * 40;
    private final double mobsPerSecond = 0.1;
    private final double spawnProbability = 0;
    private boolean spawned = false;
    public Pathfinder pFinder =  new Pathfinder();
    Player player = Player.getInstance();
    World world = World.getInstance();

    EnemyController(EnemyView enemyView) {
        this.enemyView = enemyView;
        this.enemies = enemyView.getEnemies();
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

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == null) continue;
            enemyView.update(i, true, chaseOrWander(enemies[i]));
        }

    }


    private double chaseOrWander(Enemy e) {
        WorldPosition ep = e.getWorldPos();
        WorldPosition pp = player.getWorldPos();

        // decide if we should chase
        double dx = pp.getX() - ep.getX();
        double dy = pp.getY() - ep.getY();
        double dist = Math.hypot(dx, dy);

        if (dist < tileSize * 3) {
            // within chase range
            e.onPath = true;
        } else if (dist > tileSize * 15) {
            // out of chase range
            e.onPath = false;
        }

        if (e.onPath) {
            System.out.println("pathfinding");
            // compute path toward the player's tile
            int goalCol = (pp.getX().intValue()+player.solidArea.x)/tileSize;
            int goalRow = (pp.getY().intValue()+player.solidArea.y)/tileSize;
            //; //if()then (moveuntil) //dx,dy lost
            return e.searchPath(goalCol, goalRow);
            // if path exists, step to the next node
            /*if (!e.pFinder.pathList.isEmpty()) {
                int nextCol = e.pFinder.pathList.get(0).col;
                int nextRow = e.pFinder.pathList.get(0).row;

                double targetX = nextCol * tileSize + tileSize / 2.0;
                double targetY = nextRow * tileSize + tileSize / 2.0;

                double vx = targetX - ep.getX();
                double vy = targetY - ep.getY();
                double len = Math.hypot(vx, vy);

                if (len > 0) {
                    e.moveUntil(vx / len, vy / len);
                    e.collisionChecker.checkPlayer(e, 0, 0);

                    double angle = Math.atan2(vy, vx);
                    int dir8 = (int) Math.round(angle / (Math.PI / 4)) + 4;
                    dir8 = dir8 % 8;

                    switch (dir8) {
                        case 0: // Move right
                            dx = 1;
                            dy = 0;
                            break;
                        case 1: // Move down - upright
                            dx = 1;
                            dy = -1;
                            break;
                        case 2: // Move  up
                            dx = 0;
                            dy = -1;
                            break;
                        case 3: // Move up - upleft
                            dx = -1;
                            dy = -1;
                            break;
                        case 4: // Move left
                            dx = -1;
                            dy = 0;
                            break;
                        case 5: // Move down -downleft
                            dx = -1;
                            dy = 1;
                            break;
                        case 6: // Move down
                            dx = 0;
                            dy = 1;
                            break;
                        case 7: // Move up - downright
                            dx = 1;
                            dy = 1;
                            break;
                    }

                    return Math.atan2(dy, dx);
                }
            }*/

        } else {
            if (--e.wanderSteps <= 0) {
                e.wanderSteps = 50 + rand.nextInt(100);;
                e.pathStage = rand.nextInt(8);
            }
            dx = 0;
            dy = 0;
            switch (e.pathStage) {
                case 0 -> {
                    dx = 1;
                    dy = 0;
                }  // right
                case 1 -> {
                    dx = 0;
                    dy = 1;
                }  // down
                case 2 -> {
                    dx = -1;
                    dy = 0;
                }  // left
                case 3 -> {
                    dx = 0;
                    dy = -1;
                }  // up
                case 4 -> {
                    dx = 1;
                    dy = 1;
                }
                case 5 -> {
                    dx = -1;
                    dy = -1;
                }
                case 6 -> {
                    dx = -1;
                    dy = 1;
                }
                case 7 -> {
                    dx = 1;
                    dy = -1;
                }
            } //take out 0.5s

            double length = Math.sqrt(dx * dx + dy * dy);
            double normalizedDx = dx / length;
            double normalizedDy = dy / length;
            e.moveUntil(normalizedDx, normalizedDy);
            e.collisionChecker.checkPlayer(e, 0, 0);

            return Math.atan2(dy, dx);

        }
    }


    /*void updateEnemy(Enemy enemy, int index) {
        if (stepsRemaining <= 0) {
            pathStage = new Random().nextInt(8);;//(pathStage + 1) % 4; // loop through 0 -> 1 -> 2 -> 3 -> 0

            stepsRemaining = 300; // reset steps for new side ************************************************************
        }
        //switvh/case
        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = (double) dx / length;
        double normalizedDy = (double) dy / length;
        enemy.moveUntil(speed * normalizedDx, speed * normalizedDy);

        double angle = Math.atan2(dy, dx);
        enemyView.update(index,true, angle); //*****************
        stepsRemaining--;
    }*/

    private WorldPosition getSpawnPoint() throws NoSpawnpointFoundException {


        // Try spawing 10 times, then give up
        for (int i = 0; i < 30; i++) {
            double spawnDistance = Math.random() * (outerSpawnRadius - innerSpawnRadius) + innerSpawnRadius;
            double spawnAngle = Math.random() * 2 * Math.PI;

            double relativeSpawnX = Math.cos(spawnAngle) * spawnDistance;
            double relativeSpawnY = Math.sin(spawnAngle) * spawnDistance;

            WorldPosition spawnPos = new WorldPosition(
                    player.getWorldPos().getX() + relativeSpawnX,
                    player.getWorldPos().getY() + relativeSpawnY
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


/*for (int i = 0; i < enemies.length; ++i) {
            //updateEnemy(enemies[i], i);
            if (enemies[i] == null) continue;+++++++++++++++++++++++++++++++++++++++++++++
            if (enemies[i].onPath){
                int goalCol = (playerPos.getX().intValue()+player.solidArea.x)/tileSize;
                int goalRow = (playerPos.getY().intValue()+player.solidArea.y)/tileSize;

                enemies[i].searchPath(goalCol, goalRow);
            } else {
                if (--stepsRemaining[i] <= 0) {
                    pathStage[i] = rand.nextInt(8);
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

                //************************************************************************************************+
                enemies[i].moveUntil(speed * normalizedDx, speed * normalizedDy);

                double angle = Math.atan2(dy, dx);
                enemyView.update(i, true, angle);
            }
        }*/

    //updateEnemy(enemies[0]);
    //updateEnemy(enemies[1]);
    //updateEnemy(enemies[2]);
    // despawn mobs that are too far
    // update all existing mobs

