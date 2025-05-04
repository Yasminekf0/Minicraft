package model.entity.mobs.pathfinding;

import model.world.World;

import java.util.*;

import static model.world.WorldSettings.worldSize;


@SuppressWarnings("FieldCanBeLocal")
public class Pathfinder { //Used A* algorithm to make a nodes map calculating different costs (fcost, gcost and hcost) to find and back-trace a path
    private final World world = World.getInstance();

    // 2D grid of nodes representing the map for pathfinding
    private final Node[][] nodes;

    // Priority queue for open nodes, ordered by fCost, and then hCost
    private final PriorityQueue<Node> openList;

    // Final computed path after a successful search
    public final List<Node> pathList = new ArrayList<>();

    // List of nodes modified during a search (to be reset later)
    private final List<Node> dirtyNodes = new ArrayList<>();

    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;
    private final int maxSteps = 500;

    public Pathfinder() {
        // Builds the 1000×1000 grid once
        nodes = new Node[worldSize][worldSize];
        for (int c = 0; c < worldSize; c++) {
            for (int r = 0; r < worldSize; r++) {
                boolean isSolid = !world.isWalkable(c, r) || world.hasBlock(c, r);
                nodes[c][r] = new Node(c, r, isSolid);
            }
        }

        // Used a min-heap keyed on fCost, then hCost
        openList = new PriorityQueue<>(
                Comparator.comparingInt((Node n) -> n.fCost)
                        .thenComparingInt(n -> n.hCost)
        );
    }

    // Resets only the nodes used in the last pathfinding call
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

    // Initializes the pathfinding algorithm with start and goal positions
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

    // Executes A* algorithm and returns true if a path to goalNode was found
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

    // Explores a neighboring node, updating its costs and parent if it's a better path
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

    // Reconstructs the path from goalNode to startNode by following parent links
    private void trackPath() {
        if (startNode == goalNode) return;
        Node cur = goalNode;
        while (cur != null && cur != startNode) {
            pathList.addFirst(cur);
            cur = cur.parent;
        }
    }
}
