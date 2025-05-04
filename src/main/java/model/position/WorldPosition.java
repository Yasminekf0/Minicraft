package model.position;

import java.io.Serializable;

import static view.settings.ScreenSettings.tileSize;

public class WorldPosition implements Serializable {

    private Direction directionFacing;
    private double x;
    private double y;

    public WorldPosition(double x, double y) {
        this.directionFacing = Direction.DOWN;
        this.x = x;
        this.y = y;
    }

    public void increment(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
    public void set(double dx, double dy) {
        this.x = dx;
        this.y = dy;
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


    public double getX() {
        return x;
    }

    public int getXInt(){
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public int getYInt(){
        return (int) y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public int getTileXPos(){
        return (int) (x/tileSize);
    }

    public int getTileYPos(){
        return (int) (y/tileSize);
    }

    public int getFocusedTileX(){
        return (int) ((x+tileSize*directionFacing.getX())/tileSize);
    }
    public int getFocusedTileY(){
        return (int) ((y+tileSize*directionFacing.getY())/tileSize);
    }

    public Direction getDirectionFacing() {return directionFacing;}
}
