package model.entity;

import model.entity.npcs.Enemy;
import model.entity.npcs.NPC;
import model.entity.npcs.MobManager;
import model.world.World;

import java.awt.*;
import java.io.Serializable;

import static view.settings.ScreenSettings.tileSize;

public class CollisionChecker implements Serializable {

    private final World world;
    private final Player player;

    public CollisionChecker() {
        this.world = World.getInstance();
        this.player = Player.getInstance();
    }

    public void getSpawnPos(Entity entity){
        while (!(world.isWalkable(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())) | world.hasBlock(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())){
            entity.worldPos.increment(tileSize,tileSize);
        }
    }

    public void checkTile(Entity entity, double dx, double dy) {
        int entityLeftWorldX = entity.getWorldPos().getXInt()+ entity.solidArea.x;
        int entityRightWorldX = entity.getWorldPos().getXInt()+ entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldPos().getYInt()+ entity.solidArea.y;
        int entityBottomWorldY = entity.getWorldPos().getYInt()+ entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;


        boolean tile1_OK;
        boolean tile2_OK;



        if (dy < 0) { //up
            entityTopRow = (entityTopWorldY + (int) dy) / tileSize;
            tile1_OK = !world.hasBlock(entityLeftCol, entityTopRow) && world.isWalkable(entityLeftCol, entityTopRow);
            tile2_OK = !world.hasBlock(entityRightCol, entityTopRow) && world.isWalkable(entityRightCol, entityTopRow);
            if (!tile1_OK || !tile2_OK) {
                entity.collisionOn = true;
            }
        } else if (dy>0) { //down
            entityBottomRow = (entityBottomWorldY + (int) dy) / tileSize;
            tile1_OK = !world.hasBlock(entityLeftCol, entityBottomRow) && world.isWalkable(entityLeftCol, entityBottomRow);
            tile2_OK = !world.hasBlock(entityRightCol, entityBottomRow) && world.isWalkable(entityRightCol, entityBottomRow);
            if (!tile1_OK || !tile2_OK) {
                entity.collisionOn = true;
            }
        }

        if (dx > 0) { //right
            entityRightCol = (entityRightWorldX + (int) dx) / tileSize;
            tile1_OK = !world.hasBlock(entityRightCol, entityTopRow) && world.isWalkable(entityRightCol, entityTopRow);
            tile2_OK = !world.hasBlock(entityRightCol, entityBottomRow) && world.isWalkable(entityRightCol, entityBottomRow);
            if (!tile1_OK || !tile2_OK) {
                entity.collisionOn = true;
            }
        } else if (dx < 0) {
            entityLeftCol = (entityLeftWorldX + (int) dx) / tileSize;
            tile1_OK = !world.hasBlock(entityLeftCol, entityTopRow) && world.isWalkable(entityLeftCol, entityTopRow);
            tile2_OK = !world.hasBlock(entityLeftCol, entityBottomRow) && world.isWalkable(entityLeftCol, entityBottomRow);
            if (!tile1_OK || !tile2_OK) {
                entity.collisionOn = true;
            }
        }
    }


    public int checkEntity(double dx, double dy) { //player check if there´s mobs

        Rectangle entityCollisionBox = new Rectangle(
                player.getWorldPos().getXInt() + player.solidArea.x + (int)dx,
                player.getWorldPos().getYInt() + player.solidArea.y + (int)dy,
                player.solidArea.width,
                player.solidArea.height
        );

        Enemy[] enemies = MobManager.getInstance().getEnemies();
        Entity[] targets = new Entity[enemies.length + 1];

        targets[0] = NPC.getInstance();
        System.arraycopy(enemies, 0, targets, 1, enemies.length);

        for (int i = 0; i < targets.length; i++) {
            if (targets[i] != null) {
                Entity t = targets[i];
                Rectangle tBox = new Rectangle(
                        t.getWorldPos().getXInt() + t.solidArea.x,
                        t.getWorldPos().getYInt() + t.solidArea.y,
                        t.solidArea.width,
                        t.solidArea.height
                );

                if (entityCollisionBox.intersects(tBox)) {
                    player.collisionOn = true;
                    return i;
                }
            }
        }

        return -1;
    }



    public void checkPlayer ( Entity entity, double dx, double dy) { //mobs check if theres a player

        Rectangle entityCollisionBox = new Rectangle(
                entity.getWorldPos().getXInt() + entity.solidArea.x,
                entity.getWorldPos().getYInt() + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );

        Rectangle playerCollisionBox = new Rectangle(
                player.getWorldPos().getXInt() + player.solidArea.x,
                player.getWorldPos().getYInt() + player.solidArea.y,
                player.solidArea.width,
                player.solidArea.height
        );

        if (dy < 0){//up
            entityCollisionBox.y -= entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
                if (entity instanceof Enemy) {
                    player.takeDamage(1);
                    System.out.println("Got hit by enemy, health:" + player.health);
                    player.worldPos.increment(
                            entity.getFacingDirection().getX() * tileSize,
                            entity.getFacingDirection().getY() * tileSize
                    );
                }
            }


        } else if (dy > 0) {//down
            entityCollisionBox.y += entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
                if (entity instanceof Enemy) {
                    player.takeDamage(1);
                    System.out.println("Got hit by enemy, health:" + player.health);
                    player.worldPos.increment(
                            entity.getFacingDirection().getX() * tileSize,
                            entity.getFacingDirection().getY() * tileSize
                    );
                }
            }
        }

        if (dx<0) {//left
            entityCollisionBox.x -= entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
                if (entity instanceof Enemy) {
                    player.takeDamage(1);
                    System.out.println("Got hit by enemy, health:" + player.health);
                    player.worldPos.increment(
                            entity.getFacingDirection().getX() * tileSize,
                            entity.getFacingDirection().getY() * tileSize
                    );
                }
            }
        } else if (dx>0) { //right
            entityCollisionBox.x += entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
                if (entity instanceof Enemy) {
                    player.takeDamage(1);
                    System.out.println("Got hit by enemy, health:" + player.health);
                    player.worldPos.increment(
                            entity.getFacingDirection().getX() * tileSize,
                            entity.getFacingDirection().getY() * tileSize
                    );
                }
            }
        }
    }
}
