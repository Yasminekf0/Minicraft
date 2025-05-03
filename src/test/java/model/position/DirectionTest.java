package model.position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {
    @Test
    void testDirectionCoordinates() {
        assertAll("Direction coords",
                () -> assertEquals( 0.0, Direction.UP.getX()),
                () -> assertEquals(-1.0, Direction.UP.getY()),

                () -> assertEquals( 0.0, Direction.DOWN.getX()),
                () -> assertEquals( 1.0, Direction.DOWN.getY()),

                () -> assertEquals(-1.0, Direction.LEFT.getX()),
                () -> assertEquals( 0.0, Direction.LEFT.getY()),

                () -> assertEquals( 1.0, Direction.RIGHT.getX()),
                () -> assertEquals( 0.0, Direction.RIGHT.getY()),

                () -> assertEquals( 0.5, Direction.UPRIGHT.getX()),
                () -> assertEquals(-0.5, Direction.UPRIGHT.getY()),

                () -> assertEquals(-0.5, Direction.UPLEFT.getX()),
                () -> assertEquals(-0.5, Direction.UPLEFT.getY()),

                () -> assertEquals( 0.5, Direction.DOWNRIGHT.getX()),
                () -> assertEquals( 0.5, Direction.DOWNRIGHT.getY()),

                () -> assertEquals(-0.5, Direction.DOWNLEFT.getX()),
                () -> assertEquals( 0.5, Direction.DOWNLEFT.getY())
        );
    }

}