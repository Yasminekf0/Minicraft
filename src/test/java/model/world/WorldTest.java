package model.world;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
class WorldTest {
    World world;

    @BeforeEach
    void setUp() {
        World.setInstance(null); // Reset singleton
        world = World.getInstance();
    }

    @Test
    void worldCreatesNonNullTileAndBlockMaps() {
        assertNotNull(world.getTileMap(), "Tile map should not be null");
        assertNotNull(world.getBlockMap(), "World block map should not be null");
    }

    @Test
    void tileMapContainsValidTiles() {
        Tile[][] tileMap = world.getTileMap();
        for (Tile[] row : tileMap) {
            for (Tile tile : row) {
                assertNotNull(tile, "Tile should not be null");
                assertTrue(tile instanceof Tile, "Tile must be an instance of Tile enum");
            }
        }
    }

    @Test
    void blockMapContainsValidOrNullBlocks() {
        WorldBlock[][] blockMap = world.getBlockMap();
        for (WorldBlock[] row : blockMap) {
            for (WorldBlock block : row) {
                assertTrue(block == null || block instanceof WorldBlock, "Block must be null or a valid WorldBlock");
            }
        }
    }

    @Test
    void walkableTilesReturnCorrectly() {
        Tile[][] tileMap = world.getTileMap();
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                assertEquals(tileMap[i][j].isTileWalkable(), world.isWalkable(i, j), "Walkability must match tile definition");
            }
        }
    }

    @Test
    void hasBlockReturnsCorrectly() {
        WorldBlock[][] blockMap = world.getBlockMap();
        for (int i = 0; i < blockMap.length; i++) {
            for (int j = 0; j < blockMap[i].length; j++) {
                assertEquals(blockMap[i][j] != null, world.hasBlock(i, j), "Block presence must match block map data");
            }
        }
    }

    @Test
    void breakBlockRemovesBlock() {
        // Find a tile that has a block
        for (int i = 0; i < world.getBlockMap().length; i++) {
            for (int j = 0; j < world.getBlockMap()[i].length; j++) {
                if (world.hasBlock(i, j)) {
                    world.breakBlock(i, j);
                    assertFalse(world.hasBlock(i, j), "Block should be removed after breaking");
                    return;
                }
            }
        }
    }

    @Test
    void placeBlockAddsBlock() {
        // Find a tile that doesn't have a block
        for (int i = 0; i < world.getBlockMap().length; i++) {
            for (int j = 0; j < world.getBlockMap()[i].length; j++) {
                if (!world.hasBlock(i, j)) {
                    world.placeBlock(i, j, WorldBlock.Tree);
                    assertTrue(world.hasBlock(i, j), "Block should exist after placing one");
                    assertEquals(WorldBlock.Tree, world.getBlock(i, j), "Placed block should match expected block");
                    return;
                }
            }
        }
    }

}