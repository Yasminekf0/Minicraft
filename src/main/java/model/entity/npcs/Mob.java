package model.entity.npcs;

import model.entity.Entity;

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