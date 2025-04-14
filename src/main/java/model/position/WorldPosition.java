package model.position;

import static view.ScreenSettings.tileSize;

public class WorldPosition extends Position<Double>{

    public WorldPosition(double x, double y) {
        super(x, y);
    }

    public void increment(double dx, double dy) {
        this.x += dx;
        this.y += dy;
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
}
