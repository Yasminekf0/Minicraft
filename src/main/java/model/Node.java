package model;

public class Node {
    Node parent;
    public int col, row;
    int gCost, hCost, fCost;
    boolean solid;   // true = blocked
    boolean open, checked;

    // existing one stays if you need it...
    public Node(int col, int row) {
        this(col, row, true);  // default to solid until set
    }

    // new constructor:
    public Node(int col, int row, boolean solid) {
        this.col   = col;
        this.row   = row;
        this.solid = solid;
        this.gCost = this.hCost = this.fCost = Integer.MAX_VALUE;
    }
}
