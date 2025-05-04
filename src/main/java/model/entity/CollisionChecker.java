package model.entity;

import model.entity.mobs.Mob;
import model.entity.mobs.MobManager;
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

    // Sets a valid spawn position for an entity by moving it until it's on a walkable tile without a block.
    public void getSpawnPos(Entity entity){
        while (!(world.isWalkable(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())) | world.hasBlock(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())){
            entity.worldPos.increment(tileSize,tileSize);
        }
    }

    // Checks if the entity collides with any tiles in the direction of movement by using the entity's position and solid area and world map
    public boolean checkTile(Entity entity, double dx, double dy) {
        int entityLeftWorldX = entity.getWorldPos().getXInt()+ entity.solidArea.x;
        int entityRightWorldX = entity.getWorldPos().getXInt()+ entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getWorldPos().getYInt()+ entity.solidArea.y;
        int entityBottomWorldY = entity.getWorldPos().getYInt()+ entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;


        boolean collisionY = false;
        boolean collisionX = false;



        if (dy < 0) { //up
            collisionY = checkTileY( (int) dy, entityTopWorldY, entityLeftCol, entityRightCol);
        } else if (dy>0) { //down
            collisionY = checkTileY( (int) dy, entityBottomWorldY, entityLeftCol, entityRightCol);
        }

        if (dx > 0) { //right
            collisionX = checkTileX( (int) dx, entityRightWorldX, entityTopRow, entityBottomRow);
        } else if (dx < 0) {
            collisionX = checkTileX( (int) dx, entityLeftWorldX, entityTopRow, entityBottomRow);
        }

        return collisionY || collisionX;
    }

    private boolean checkTileX( int dx, int entityWorldX, int entityTopRow, int entityBottomRow) {
        int entityCol;
        boolean tile1_OK;
        boolean tile2_OK;
        entityCol = (entityWorldX + dx) / tileSize;
        tile1_OK = !world.hasBlock(entityCol, entityTopRow) && world.isWalkable(entityCol, entityTopRow);
        tile2_OK = !world.hasBlock(entityCol, entityBottomRow) && world.isWalkable(entityCol, entityBottomRow);
        return !tile1_OK || !tile2_OK;
    }

    private boolean checkTileY( int dy, int entityWorldY, int entityLeftCol, int entityRightCol) {
        int entityRow;
        boolean tile1_OK;
        boolean tile2_OK;
        entityRow = (entityWorldY + dy) / tileSize;
        tile1_OK = !world.hasBlock(entityLeftCol, entityRow) && world.isWalkable(entityLeftCol, entityRow);
        tile2_OK = !world.hasBlock(entityRightCol, entityRow) && world.isWalkable(entityRightCol, entityRow);

        return !tile1_OK || !tile2_OK;
    }



    // Checks if the player will collide with any mobs after moving (used by player)
    public boolean checkEntity(double dx, double dy) {

        Mob[] targets = MobManager.getInstance().getMobs();
        // Makes a future temporary player collision box where the player wants to move and checks if they'll intersect with the mob's collision box
        Rectangle entityCollisionBox = new Rectangle(
                player.getWorldPos().getXInt() + player.solidArea.x + (int)dx,
                player.getWorldPos().getYInt() + player.solidArea.y + (int)dy,
                player.solidArea.width,
                player.solidArea.height
        );


        for (Mob target : targets) {
            if (target != null) {
                Rectangle tBox = new Rectangle(
                        target.getWorldPos().getXInt() + target.solidArea.x,
                        target.getWorldPos().getYInt() + target.solidArea.y,
                        target.solidArea.width,
                        target.solidArea.height
                );

                if (entityCollisionBox.intersects(tBox)) {
                    return true;
                }
            }
        }

        return false;
    }


    // Checks if the mob will collide with the player after moving (used by mobs)
    public boolean checkPlayer ( Mob mob, double dx, double dy) {
        // Similar logic than checkEntity()

        Rectangle entityCollisionBox = new Rectangle(
                mob.getWorldPos().getXInt() + mob.solidArea.x,
                mob.getWorldPos().getYInt() + mob.solidArea.y,
                mob.solidArea.width,
                mob.solidArea.height
        );

        Rectangle playerCollisionBox = new Rectangle(
                player.getWorldPos().getXInt() + player.solidArea.x,
                player.getWorldPos().getYInt() + player.solidArea.y,
                player.solidArea.width,
                player.solidArea.height
        );

        boolean isCollision = false;

        if (dy < 0){//up
            entityCollisionBox.y -= mob.speed;
            isCollision = checkIntersect(mob, entityCollisionBox, playerCollisionBox);


        } else if (dy > 0) {//down
            entityCollisionBox.y += mob.speed;
            isCollision = checkIntersect(mob, entityCollisionBox, playerCollisionBox);
        }

        if (dx<0) {//left
            entityCollisionBox.x -= mob.speed;
            isCollision = isCollision || checkIntersect(mob, entityCollisionBox, playerCollisionBox);
        } else if (dx>0) { //right
            entityCollisionBox.x += mob.speed;
            isCollision = isCollision || checkIntersect(mob, entityCollisionBox, playerCollisionBox);
        }

        return isCollision;
    }

    private boolean checkIntersect(Mob mob, Rectangle entityCollisionBox, Rectangle playerCollisionBox) {
        if (entityCollisionBox.intersects(playerCollisionBox)) {
            mob.interact();
            return true;
        }
        return false;
    }
}
