package world.position;

public class Position <T> {
    protected T x;
    protected T y;

    public Position(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    public void setPos(T x, T y){
        this.x = x;
        this.y = y;
    }
}
