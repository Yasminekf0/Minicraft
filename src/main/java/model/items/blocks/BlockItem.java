package model.items.blocks;

import model.entity.Player;
import model.items.Item;
import model.world.World;
import model.world.WorldBlock;

public abstract class BlockItem extends Item {

    protected WorldBlock worldBlock;
    public BlockItem() {
        super("Block");
    }

    @Override
    public void use() {
        Player player = Player.getInstance();
        World world = World.getInstance();

        int focusedX = player.getWorldPos().getFocusedTileX();
        int focusedY = player.getWorldPos().getFocusedTileY();

        if (world.isPlaceable(focusedX,focusedY)) world.placeBlock(focusedX,focusedY,worldBlock);
    }
}
