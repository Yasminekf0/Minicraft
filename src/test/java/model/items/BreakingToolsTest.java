package model.items;

import model.entity.Player;
import model.items.Item;
import model.items.Inventory;
import model.position.WorldPosition;
import model.world.World;
import model.world.WorldBlock;
import model.items.blocks.WoodItem;
import model.items.tools.BreakingTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static view.settings.ScreenSettings.tileSize;

class BreakingToolsTest {

    private BreakingTools tool;
    private World         world;
    private Player        player;
    private Inventory     inv;

    @BeforeEach
    void setUp() {
        // get the real singletons
        world  = World.getInstance();
        player = Player.getInstance();
        inv     = player.getInventory();


        tool = new BreakingTools() {};
    }

    @Test
    void blockBreaking() {
        //  placing a block
        world.placeBlock(2, 3, WorldBlock.Wood);

        // player to block
        double px = 2 * tileSize + tileSize*0.5;
        double py = 3 * tileSize + tileSize*0.5;
        player.setWorldPos(new WorldPosition(px, py));

        // how many imputs needed

        int durability     = WorldBlock.Wood.getBlockDurabilty(); // 25
        int damagePerHit   = 10 + tool.getMaterialEnum().getAddedPower(); // 10 initially
        int usesNeeded     = (durability + damagePerHit - 1) / damagePerHit;

        // break block
        for (int i = 0; i <= usesNeeded; i++) {
            tool.use();
        }

    }

    @Test
    void useOnEmptyTile() {
        // no block tile
        player.setWorldPos(new WorldPosition(10 * tileSize + 0.5, 10 * tileSize + 0.5));

        assertDoesNotThrow(() -> tool.use());
    }
}
