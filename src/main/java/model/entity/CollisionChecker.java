package model.entity;

import model.position.WorldPosition;
import model.world.World;

public class CollisionChecker {

    private final World world;
    private final int scale;

    public CollisionChecker(World world, int scale) {
        this.world = world;
        this.scale = scale;
    }

    public boolean canMoveX(WorldPosition pos, double dx) {
        int nextTileX = pos.getNextXTilePos(dx * 4 * scale);
        return !world.hasBlock(nextTileX, pos.getTileYPos()) && world.isWalkable(nextTileX, pos.getTileYPos());
    }

    public boolean canMoveY(WorldPosition pos, double dy) {
        int nextTileY = pos.getNextYTilePos(dy * 4 * scale);
        return !world.hasBlock(pos.getTileXPos(), nextTileY) && world.isWalkable(pos.getTileXPos(), nextTileY);
    }
}
