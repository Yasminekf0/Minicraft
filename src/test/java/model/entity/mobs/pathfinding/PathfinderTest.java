package model.entity.mobs.pathfinding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PathfinderTest {
    private Pathfinder pathfinder;

    @BeforeEach
    void setUp() {
        pathfinder = new Pathfinder();
    }

    @Test
    void testStartEqualsGoal() {

        pathfinder.setNode(5, 5, 5, 5);
        assertTrue(pathfinder.search(), "Search should return true when start equals goal");
        assertTrue(pathfinder.pathList.isEmpty(), "Path list should be empty when start equals goal");
    }

    @Test
    void testStraightLinePathVertical() {
        int startCol = 0, startRow = 0;
        int goalCol = 0, goalRow = 4;
        pathfinder.setNode(startCol, startRow, goalCol, goalRow);
        assertTrue(pathfinder.search(), "Search should find a path in an unobstructed grid");
        assertEquals(4, pathfinder.pathList.size(), "Path length should equal Manhattan distance");

        for (int i = 0; i < pathfinder.pathList.size(); i++) {
            Node n = pathfinder.pathList.get(i);
            assertEquals(goalCol, n.col, "Column should remain constant for vertical path");
            assertEquals(startRow + i + 1, n.row, "Row should increment by 1 at each step");
        }
    }

    @Test
    void testStraightLinePathHorizontal() {
        int startCol = 2, startRow = 3;
        int goalCol = 6, goalRow = 3;
        pathfinder.setNode(startCol, startRow, goalCol, goalRow);
        assertTrue(pathfinder.search(), "Search should find a path in an unobstructed grid");
        assertEquals(4, pathfinder.pathList.size(), "Path length should equal Manhattan distance");

        for (int i = 0; i < pathfinder.pathList.size(); i++) {
            Node n = pathfinder.pathList.get(i);
            assertEquals(startRow, n.row, "Row should remain constant for horizontal path");
            assertEquals(startCol + i + 1, n.col, "Column should increment by 1 at each step");
        }
    }

    @Test
    void testMaxStepsLimit() {

        pathfinder.setNode(0, 0, 0, 600);
        assertFalse(pathfinder.search(), "Search should return false when path exceeds maxSteps limit");
    }
}