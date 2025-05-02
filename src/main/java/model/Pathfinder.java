package model;

import model.world.World;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

import static model.world.WorldSettings.worldSize;


public class Pathfinder {
    private final World world = World.getInstance();
    private final Node[][] nodes;
    private final PriorityQueue<Node> openList;
    public final List<Node> pathList = new ArrayList<>();
    private final List<Node> dirtyNodes = new ArrayList<>();

    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;
    private final int maxSteps = 500;

    public Pathfinder() {
        // 1) Build your 1000×1000 grid ONCE
        nodes = new Node[worldSize][worldSize];
        for (int c = 0; c < worldSize; c++) {
            for (int r = 0; r < worldSize; r++) {
                boolean isSolid = !world.isWalkable(c, r) || world.hasBlock(c, r);
                nodes[c][r] = new Node(c, r, isSolid);
            }
        }

        // 2) Use a min-heap keyed on fCost, then hCost
        openList = new PriorityQueue<>(
                Comparator.comparingInt((Node n) -> n.fCost)
                        .thenComparingInt(n -> n.hCost)
        );
    }

    private void resetNodes() {
        // Only clear the nodes we've actually opened/checked
        for (Node n : dirtyNodes) {
            n.gCost = n.hCost = n.fCost = Integer.MAX_VALUE;
            n.parent = null;
            n.open = n.checked = false;
        }
        dirtyNodes.clear();
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    /** Called each time you want to (re)start a search. */
    public void setNode(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        startNode = nodes[startCol][startRow];
        goalNode  = nodes[goalCol][goalRow];

        // Initialize the start node
        startNode.gCost = 0;
        startNode.hCost = Math.abs(startCol - goalCol) + Math.abs(startRow - goalRow);
        startNode.fCost = startNode.hCost;
        startNode.open  = true;

        dirtyNodes.add(startNode);
        openList.add(startNode);
    }

    /** Runs A*; returns true if a path to goalNode was found. */
    public boolean search() {
        while (!openList.isEmpty() && !goalReached && step++ < maxSteps) {
            currentNode = openList.poll();
            currentNode.checked = true;

            // If we've reached the goal, build the path and return
            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
                return true;
            }

            // Explore four neighbors
            exploreNeighbor(currentNode.col,     currentNode.row - 1);
            exploreNeighbor(currentNode.col - 1, currentNode.row);
            exploreNeighbor(currentNode.col + 1, currentNode.row);
            exploreNeighbor(currentNode.col,     currentNode.row + 1);
        }
        return goalReached;
    }

    private void exploreNeighbor(int c, int r) {
        // Bounds check
        if (c < 0 || c >= worldSize || r < 0 || r >= worldSize) return;

        Node nbr = nodes[c][r];
        if (nbr.checked || nbr.solid) return;

        int tentativeG = currentNode.gCost + 1;
        if (tentativeG < nbr.gCost) {
            nbr.parent = currentNode;
            nbr.gCost  = tentativeG;
            nbr.hCost  = Math.abs(c - goalNode.col) + Math.abs(r - goalNode.row);
            nbr.fCost  = nbr.gCost + nbr.hCost;

            if (!nbr.open) {
                nbr.open = true;
                dirtyNodes.add(nbr);
                openList.add(nbr);
            } else {
                // “Decrease key” by re-adding
                openList.remove(nbr);
                openList.add(nbr);
            }
        }
    }

    /** Back-traces from goalNode up to startNode into pathList. */
    public void trackPath() {
        if (startNode == goalNode) return;
        Node cur = goalNode;
        while (cur != null && cur != startNode) {
            pathList.add(0, cur);
            cur = cur.parent;
        }
    }
}
