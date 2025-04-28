package model.entity;

import model.position.WorldPosition;
import model.world.World;

import java.awt.*;

import static view.ScreenSettings.scale;
import static view.ScreenSettings.tileSize;

public class CollisionChecker {

    private final World world;
    private final NPC npc;
    private final Enemy enemy;
    private final Player player;

    public CollisionChecker() {
        this.world = World.getInstance();
        this.npc = NPC.getInstance();
        this.enemy = Enemy.getInstance();
        this.player = Player.getInstance();
    }

    public void getSpawnPos(Entity entity){
        while (!(world.isWalkable(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())) | world.hasBlock(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())){
            entity.worldPos.increment(tileSize,tileSize);
        }
    }

    public void checkTile(Entity entity, double dx, double dy) {
        int entityLeftWorldX = entity.getWorldPos().getX().intValue()+ entity.solidArea.x;
        int entityRightWorldX = entity.getWorldPos().getX().intValue()+ entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldPos().getY().intValue()+ entity.solidArea.y;
        int entityBottomWorldY = entity.getWorldPos().getY().intValue()+ entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;

        //System.out.printf("[%d , %d]", entity.solidArea.x, entity.solidArea.y);

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


    public int checkEntity(Entity entity, double dx, double dy) {
        int index = 999;

        //for(int i = 0; i < target.length; ++i) {
            //if (target[i] != null) {
                //entity.solidArea.x += entity.getWorldPos().getX().intValue();
                //entity.solidArea.y += entity.getWorldPos().getY().intValue();

                //npc.solidArea.x += npc.getWorldPos().getX().intValue();
                //npc.solidArea.y += npc.getWorldPos().getY().intValue();

        Rectangle entityCollisionBox = new Rectangle(
                entity.getWorldPos().getX().intValue() + entity.solidArea.x,
                entity.getWorldPos().getY().intValue() + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );

        Rectangle npcCollisionBox = new Rectangle(
                npc.getWorldPos().getX().intValue() + npc.solidArea.x,
                npc.getWorldPos().getY().intValue() + npc.solidArea.y,
                npc.solidArea.width,
                npc.solidArea.height
        );

        Rectangle enemyCollisionBox = new Rectangle(
                enemy.getWorldPos().getX().intValue() + enemy.solidArea.x,
                enemy.getWorldPos().getY().intValue() + enemy.solidArea.y,
                enemy.solidArea.width,
                enemy.solidArea.height
        );

                if (dy < 0){//up
                        entityCollisionBox.y -= entity.speed;
                        if ((entityCollisionBox.intersects(npcCollisionBox)) || (entityCollisionBox.intersects(enemyCollisionBox))) {
                            entity.collisionOn = true;
                            index = 1;
                        }

                } else if (dy > 0) {//down
                    entityCollisionBox.y += entity.speed;
                    if ((entityCollisionBox.intersects(npcCollisionBox)) || (entityCollisionBox.intersects(enemyCollisionBox))) {
                        entity.collisionOn = true;
                        index = 1;
                    }
                }

                if (dx<0) {//left
                    entityCollisionBox.x -= entity.speed;
                    if ((entityCollisionBox.intersects(npcCollisionBox)) || (entityCollisionBox.intersects(enemyCollisionBox))) {
                        entity.collisionOn = true;
                        index = 1;
                    }
                } else if (dx>0) { //right
                    entityCollisionBox.x += entity.speed;
                    if ((entityCollisionBox.intersects(npcCollisionBox)) || (entityCollisionBox.intersects(enemyCollisionBox))) {
                        entity.collisionOn = true;
                        index = 1;
                    }
                }

                //entity.solidArea.x = entity.solidAreaDefault.x;
                //entity.solidArea.y = entity.solidAreaDefault.y;
                //npc.solidArea.x = npc.solidAreaDefault.x;
                //npc.solidArea.y = npc.solidAreaDefault.y;

        return index;

    }



    public void checkPlayer ( Entity entity, double dx, double dy) {
        //entity.solidArea.x += entity.getWorldPos().getX().intValue();
        //entity.solidArea.y += entity.getWorldPos().getY().intValue();

        //player.solidArea.x += player.getWorldPos().getX().intValue();
        //player.solidArea.y += player.getWorldPos().getY().intValue();

        Rectangle entityCollisionBox = new Rectangle(
                entity.getWorldPos().getX().intValue() + entity.solidArea.x,
                entity.getWorldPos().getY().intValue() + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );

        Rectangle playerCollisionBox = new Rectangle(
                player.getWorldPos().getX().intValue() + player.solidArea.x,
                player.getWorldPos().getY().intValue() + player.solidArea.y,
                player.solidArea.width,
                player.solidArea.height
        );

        if (dy < 0){//up
            entityCollisionBox.y -= entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
            }


        } else if (dy > 0) {//down
            entityCollisionBox.y += entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
            }
        }

        if (dx<0) {//left
            entityCollisionBox.x -= entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
            }
        } else if (dx>0) { //right
            entityCollisionBox.x += entity.speed;
            if (entityCollisionBox.intersects(playerCollisionBox)) {
                entity.collisionOn = true;
            }
        }

        //entity.solidArea.x = entity.solidAreaDefault.x;
        //entity.solidArea.y = entity.solidAreaDefault.y;
        //npc.solidArea.x = npc.solidAreaDefault.x;
        //npc.solidArea.y = npc.solidAreaDefault.y;
    }
}
