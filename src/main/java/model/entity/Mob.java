package model.entity;

public abstract class Mob extends Entity {

    protected boolean moving;

    public Mob() {
        super();
        this.moving = false;
    }

    public boolean isMoving() {
        return moving;
    }
}