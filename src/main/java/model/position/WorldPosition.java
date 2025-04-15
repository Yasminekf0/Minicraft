package model.position;

import static view.ScreenSettings.tileSize;

public class WorldPosition extends Position<Double>{

    private Direction directionFacing;

    public WorldPosition(double x, double y) {
        super(x, y);
    }

    public void increment(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void updateDirection(double dx, double dy){
        if ((dx == 0) & (dy < 0)) directionFacing = Direction.UP;
        else if ((dx == 0) & (dy > 0)) directionFacing = Direction.DOWN;
        else if ((dx > 0) & (dy == 0)) directionFacing = Direction.RIGHT;
        else if ((dx < 0) & (dy == 0)) directionFacing = Direction.LEFT;
        else if ((dx > 0) & (dy < 0)) directionFacing = Direction.UPRIGHT;
        else if ((dx < 0) & (dy < 0)) directionFacing = Direction.UPLEFT;
        else if ((dx > 0) & (dy > 0)) directionFacing = Direction.DOWNRIGHT;
        else if ((dx < 0) & (dy > 0)) directionFacing = Direction.DOWNLEFT;
    }

    public int getTileXPos(){
        return (int) (x/tileSize);
    }

    public int getTileYPos(){
        return (int) (y/tileSize);
    }

    public int getNextXTilePos(double dx){
        return (int) ((x+dx)/tileSize);
    }

    public int getNextYTilePos(double dy){
        return (int) ((y+dy)/tileSize);
    }

    public int getFocusedTileX(){
        return (int) ((x+tileSize*directionFacing.getX())/tileSize);
    }
    public int getFocusedTileY(){
        return (int) ((y+tileSize*directionFacing.getY())/tileSize);
    }
}
