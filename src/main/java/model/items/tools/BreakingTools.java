package model.items.tools;

import model.entity.Player;
import model.items.Item;
import model.world.World;
import model.world.WorldBlock;

public abstract class BreakingTools extends Tool {

    private final int breakingPower = 10;

    public BreakingTools() {
        super();
        currentBlockHealth = 500; //Any big value to begin if statements
    }

    private int targetedX;
    private int targetedY;

    private int currentBlockHealth;

    public interface BlockBrokenListener {
        void onBlockBroken(WorldBlock block, Item drop);
    }

    private BlockBrokenListener brokenListener;

    public void setOnBlockBroken(BlockBrokenListener l) {
        this.brokenListener = l;
    }

    @Override
    public void use() {
        Player player = Player.getInstance();
        World world = World.getInstance();
        if (currentBlockHealth > 0) {
            if (player.getWorldPos().getFocusedTileX() == targetedX && player.getWorldPos().getFocusedTileY() == targetedY) {
                if (world.getBlock(targetedX, targetedY) != null && world.getBlock(targetedX, targetedY).isCorrectTool(this)) {
                    currentBlockHealth -= (breakingPower + material.addedPower);
                }
            } else {
                targetedX = player.getWorldPos().getFocusedTileX();
                targetedY = player.getWorldPos().getFocusedTileY();

                if (world.getBlock(targetedX, targetedY) != null) {
                    currentBlockHealth = world.getBlock(targetedX, targetedY).getBlockDurabilty();
                }
            }
        } else {
            WorldBlock block = world.getBlock(targetedX, targetedY);
            if (block != null) {
                Item drop = block.getDrop();
                world.breakBlock(targetedX, targetedY);
                currentBlockHealth = 500;

                brokenListener.onBlockBroken(block, drop);

            }
        }
    }
}