package model;

import model.world.World;

import java.util.ArrayList;
import java.util.List;

import static model.world.WorldSettings.worldSize;

public class Pathfinder {
    World world;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    int bestNodeIndex = 0;
    int bestNodefCost  = Integer.MAX_VALUE;
    private List<Node> dirtyNodes = new ArrayList<>();

    public Pathfinder() {
        this.world= World.getInstance();
        getInstanceNode();

    }

    public void getInstanceNode(){
        nodes = new Node[worldSize][worldSize];
        int col = 0;
        int row = 0;

        while(col < worldSize && row < worldSize){
            nodes[col][row] = new Node(col, row);

            col++;
            if (col == worldSize){
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes(){
        int col = 0;
        int row = 0;

        while(col < worldSize && row < worldSize){
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;

            col++;
            if (col == worldSize){
                col = 0;
                row++;
            }

        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }


    public void setNode(int startCol, int startRow, int goalCol, int goalRow){
        /*int R = 20; // chase radius in tiles
        int minC = Math.max(0,  Math.min(startCol, goalCol) - R);
        int maxC = Math.min(worldSize-1, Math.max(startCol, goalCol) + R);
        int minR = Math.max(0,  Math.min(startRow, goalRow) - R);
        int maxR = Math.min(worldSize-1, Math.max(startRow, goalRow) + R);

        int w = maxC - minC + 1;
        int h = maxR - minR + 1;
        Node[][] local = new Node[w][h];

        for (int dc = 0; dc < w; dc++) {
            for (int dr = 0; dr < h; dr++) {
                Node global = nodes[minC + dc][minR + dr];
                local[dc][dr] = new Node(dc, dr, global.walkable);
            }
        }

        startNode = local[startCol - minC][startRow - minR];
        goalNode  = local[goalCol  - minC][goalRow  - minR];
*/
        resetNodes();
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < worldSize && row < worldSize){
            if (world.hasBlock(col, row) || !world.isWalkable(col,row)){
                nodes[col][row].solid = true;
            }

            getCost(nodes[col][row]);
            col++;
            if (col == worldSize){
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node){

        //g cost
        int x = Math.abs(node.col - startNode.col);
        int y = Math.abs(node.row - startNode.row);
        node.gCost = x + y;

        //h cost
        x = Math.abs(node.col - goalNode.col);
        y = Math.abs(node.row - goalNode.row);
        node.hCost = x + y;

        //fcost
        node.fCost = node.gCost+node.hCost;

    }

    public boolean search(){

        while (!goalReached && step <500){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            if (row -1 >= 0) { //up node
                openNode(nodes[col][row-1]);
            }

            if (col -1 >= 0) { //up node
                openNode(nodes[col-1][row]);
            }

            if (row +1 < worldSize) { //up node
                openNode(nodes[col][row+1]);
            }

            if (col +1 < worldSize) { //up node
                openNode(nodes[col+1][row]);
            }

            bestNodeIndex = 0;
            bestNodefCost  = Integer.MAX_VALUE; //************************not og

            for (int i=0; i < openList.size(); i++){
                if (openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            if (openList.size() == 0){
                break;
            }
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode){
                goalReached = true;
                trackPath();
            }
            step++;
        }
        return goalReached;
    }

    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackPath(){
        if (goalNode == startNode) return;
        Node current = goalNode;

        while (current != null && current.parent != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
