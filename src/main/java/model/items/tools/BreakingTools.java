package model.items.tools;

import model.entity.Player;
import model.items.Item;
import model.items.blocks.RockItem;
import model.items.blocks.WoodItem;
import model.world.World;
import model.world.WorldBlock;
import view.SoundManager;

public abstract class BreakingTools extends Tool{

    private final int breakingPower = 10;

    public BreakingTools(){
        super();
        currentBlockHealth = 500; //Any big value to begin if statements
    }

    private int targetedX;
    private int targetedY;

    private int currentBlockHealth ;

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
        }
        else {
            WorldBlock block = world.getBlock(targetedX, targetedY);
            Item drop = block.getDrop();
            player.getInventory().addItem(drop);
            world.breakBlock(targetedX,targetedY);

            switch (drop) {
                case WoodItem woodItem -> SoundManager.getInstance().playSound("wood");
                case RockItem rockItem -> SoundManager.getInstance().playSound("stone");
                default -> SoundManager.getInstance().playSound("pickup");
            }

            currentBlockHealth = 500;
        }
    }

}
