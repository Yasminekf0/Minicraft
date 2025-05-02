package model.items.blocks;

import model.entity.Player;
import model.items.Item;
import model.world.World;
import model.world.WorldBlock;
import view.SoundManager;

public abstract class BlockItem extends Item {

    protected WorldBlock worldBlock;
    public BlockItem() {
        super("Blocks");
    }

    @Override
    public void use() {
        Player player = Player.getInstance();
        World world = World.getInstance();

        int focusedX = player.getWorldPos().getFocusedTileX();
        int focusedY = player.getWorldPos().getFocusedTileY();

        if (world.isPlaceable(focusedX,focusedY) & count > 0) {
            world.placeBlock(focusedX,focusedY,worldBlock);
            // TODO: this should be in view, Model should not play sounds
            switch (this) {
                case WoodItem woodItem   -> SoundManager.getInstance().playSound("wood");
                case RockItem rockItem   -> SoundManager.getInstance().playSound("stone");      // or "stone_place"
                default             -> SoundManager.getInstance().playSound("pickup");
            }
            count--;
        }
    }
}
