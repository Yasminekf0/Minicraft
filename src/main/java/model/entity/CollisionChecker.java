package model.entity;

import model.entity.npcs.Enemy;
import model.entity.npcs.Mob;
import model.entity.npcs.NPC;
import model.entity.npcs.MobManager;
import model.world.World;

import java.awt.*;

import static view.settings.ScreenSettings.scale;
import static view.settings.ScreenSettings.tileSize;

public class CollisionChecker {

    private final World world;
    private final Player player;

    private final MobManager mobManager;

    public CollisionChecker() {
        this.world = World.getInstance();
        this.player = Player.getInstance();
        this.mobManager = MobManager.getInstance();
    }

    public void getSpawnPos(Entity entity){
        while (!(world.isWalkable(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())) | world.hasBlock(entity.worldPos.getTileXPos(),entity.worldPos.getTileYPos())){
            entity.worldPos.increment(tileSize,tileSize);
        }
    }

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



    public boolean checkEntity(double dx, double dy) { //player check if there´s mobs

        Mob[] targets = mobManager.getMobs();

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



    public boolean checkPlayer ( Mob mob, double dx, double dy) { //mobs check if theres a player

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
