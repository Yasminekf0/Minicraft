package model.entity;

import model.position.WorldPosition;
import model.world.World;

import java.awt.*;

import static view.ScreenSettings.scale;
import static view.ScreenSettings.tileSize;

public class CollisionChecker {

    private final World world;

    public CollisionChecker() {
        this.world = World.getInstance();
    }

    public void checkTile(Entity entity, double dx, double dy) {
        int entityLeftWorldX = entity.getWorldPos().getX().intValue() + entity.solidArea.x;
        int entityRightWorldX = entity.getWorldPos().getX().intValue() + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldPos().getY().intValue() + entity.solidArea.y;
        int entityBottomWorldY = entity.getWorldPos().getY().intValue() + entity.solidArea.y + entity.solidArea.height;

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
}
