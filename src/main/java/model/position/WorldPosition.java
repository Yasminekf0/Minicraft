package model.position;

public class WorldPosition extends Position<Double>{

    public WorldPosition(double x, double y) {
        super(x, y);
    }

    public void increment(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

}
