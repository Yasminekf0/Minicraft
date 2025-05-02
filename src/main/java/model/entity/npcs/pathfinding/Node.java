package model.entity.npcs.pathfinding;

public class Node {
    Node parent;
    public final int col;
    public final int row;
    int gCost, hCost, fCost;
    final boolean solid;   // true = blocked
    boolean open, checked;

    // existing one stays if you need it...
    public Node(int col, int row) {
        this(col, row, false);  // default to solid until set
    }

    // new constructor:
    public Node(int col, int row, boolean solid) {
        this.col   = col;
        this.row   = row;
        this.solid = solid;
        this.gCost = this.hCost = this.fCost = Integer.MAX_VALUE;
    }
}
