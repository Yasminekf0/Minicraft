package world.position;

public class WorldPosition {
    private double x;
    private double y;

    public WorldPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void increment(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

}
