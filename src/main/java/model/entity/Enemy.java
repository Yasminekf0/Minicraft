package model.entity;

public class Enemy extends Mob {

    private int direction = 1;
    private int patrolRange = 100;
    private int startX;

    public Enemy(int startX, int startY) {
        super();//startX, startY);
        this.startX = startX;
    }

    @Override
    public void update() {
        /*x += direction;
        moving = true;

        if (Math.abs(x - startX) >= patrolRange) {
            direction *= -1; // turn around
        }*/
    }
}