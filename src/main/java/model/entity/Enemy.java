package model.entity;

public class Enemy extends Mob {

    private int direction = 1;
    private int patrolRange = 100;
    private int startX;

    public Enemy() {
        super();
    }
}