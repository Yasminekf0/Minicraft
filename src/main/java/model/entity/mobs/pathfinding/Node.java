package model.entity.mobs.pathfinding;

public class Node {
    Node parent;
    public final int col;
    public final int row;
    int gCost, hCost, fCost;
    final boolean solid;   // true = blocked
    boolean open, checked;

    // new constructor:
    public Node(int col, int row, boolean solid) {
        this.col   = col;
        this.row   = row;
        this.solid = solid;
        this.gCost = this.hCost = this.fCost = Integer.MAX_VALUE;
    }
}
