package model.position;
import static view.ScreenSettings.tileSize;
import static org.junit.jupiter.api.Assertions.*;
import static view.settings.ScreenSettings.tileSize;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class WorldPositionTest {
    private WorldPosition wp;

    @BeforeEach
    void setUp() {
        wp = new WorldPosition(0.0, 0.0);
    }

    @Test
    void testInitialState() {
        assertEquals(0.0, wp.getX());
        assertEquals(0.0, wp.getY());
        assertEquals(Direction.DOWN, wp.getDirectionFacing());
    }

    @Test
    void testIncrement() {
        wp.increment(3.5, -1.25);
        assertEquals(3.5, wp.getX(), 1e-9);
        assertEquals(-1.25, wp.getY(), 1e-9);
    }

    @Test
    void testUpdateDirectionAllBranches() {
        wp.updateDirection(0, -5);
        assertEquals(Direction.UP, wp.getDirectionFacing());

        wp.updateDirection(0, 5);
        assertEquals(Direction.DOWN, wp.getDirectionFacing());

        wp.updateDirection(5, 0);
        assertEquals(Direction.RIGHT, wp.getDirectionFacing());

        wp.updateDirection(-5, 0);
        assertEquals(Direction.LEFT, wp.getDirectionFacing());

        wp.updateDirection(5, -5);
        assertEquals(Direction.UPRIGHT, wp.getDirectionFacing());

        wp.updateDirection(-5, -5);
        assertEquals(Direction.UPLEFT, wp.getDirectionFacing());

        wp.updateDirection(5, 5);
        assertEquals(Direction.DOWNRIGHT, wp.getDirectionFacing());

        wp.updateDirection(-5, 5);
        assertEquals(Direction.DOWNLEFT, wp.getDirectionFacing());
    }

    @Test
    void testUpdateDirectionNoChangeWhenZero() {

        wp.updateDirection(0, 0);
        assertEquals(Direction.DOWN, wp.getDirectionFacing());
    }

    @Test
    void testTileAndNextTilePositions() {
        double x = tileSize * 3 + 4.7;
        double y = tileSize * 2 + 8.2;
        wp = new WorldPosition(x, y);

        assertEquals(3, wp.getTileXPos());
        assertEquals(2, wp.getTileYPos());

        assertEquals(4, wp.getNextXTilePos(tileSize));
        assertEquals(3, wp.getNextYTilePos(tileSize));
    }

    @Test
    void testFocusedTileBasedOnDirection() {

        double x = tileSize * 1 + 1.0;
        double y = tileSize * 2 + 1.0;
        wp = new WorldPosition(x, y);

        assertEquals(1, wp.getFocusedTileX());
        assertEquals(3, wp.getFocusedTileY());


        wp.updateDirection(0, -1);
        assertEquals(1, wp.getFocusedTileX());
        assertEquals(1, wp.getFocusedTileY());


        wp.updateDirection(1, 0);
        assertEquals(2, wp.getFocusedTileX());
        assertEquals(2, wp.getFocusedTileY());
    }
}