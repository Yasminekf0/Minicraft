package model.position;

public enum Direction {
    UP(0,-1),
    DOWN(0,1),
    LEFT(-1,0),
    RIGHT(1,0),
    UPRIGHT(0.5,-0.5),
    UPLEFT(-0.5,-0.5),
    DOWNRIGHT(0.5,0.5),
    DOWNLEFT(-0.5,0.5)
    ;

    private final double x;
    private final double y;
    Direction(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
