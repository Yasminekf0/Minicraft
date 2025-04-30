package model.entity;
import controller.GameController;
import model.Pathfinder;
import model.position.WorldPosition;

import java.awt.*;

import static view.ScreenSettings.tileSize;

public abstract class Entity {
    protected WorldPosition worldPos;

    protected int speed;
    protected int health;
    protected int maxHealth;
    protected CollisionChecker collisionChecker;
    public Rectangle solidArea;
    public Rectangle solidAreaDefault;// = new Rectangle(8,8,8,8);
    public boolean collisionOn = false;
    public boolean onPath = false;
    public Pathfinder pFinder =  new Pathfinder();

    public WorldPosition getWorldPos() {
        return worldPos;
    }

    public void setWorldPos(WorldPosition worldPos) {
        this.worldPos = worldPos;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void setSpeed(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public void heal(int healAmount) {
        this.health = Math.min(maxHealth, this.health + healAmount);
    }

    private void up()    { }
    private void down()  { }
    private void left()  { }
    private void right() {}

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (this.getWorldPos().getX().intValue()+ this.solidArea.x)/tileSize;
        int startRow = (this.getWorldPos().getY().intValue()+ this.solidArea.y)/tileSize;
        pFinder.setNode(startCol, startRow, goalCol, goalRow);
        if (pFinder.search()){
            int nextX = pFinder.pathList.get(0).col*tileSize;
            int nextY = pFinder.pathList.get(0).row*tileSize;


            int entityLeftWorldX = this.getWorldPos().getX().intValue()+ this.solidArea.x;
            int entityRightWorldX = this.getWorldPos().getX().intValue()+ this.solidArea.x + this.solidArea.width;
            int entityTopWorldY = this.getWorldPos().getY().intValue()+ this.solidArea.y;
            int entityBottomWorldY = this.getWorldPos().getY().intValue()+ this.solidArea.y + this.solidArea.height;

            if (entityTopWorldY>nextY && entityLeftWorldX >= nextX && entityRightWorldX < nextX+tileSize) {
                up();
            } else if (entityTopWorldY<nextY && entityLeftWorldX >= nextX && entityRightWorldX < nextX+tileSize) {
                down();
                
            } else if (entityTopWorldY>=nextY && entityBottomWorldY<nextY+tileSize) {
                if (entityLeftWorldX >nextX) {
                    left();
                } else if (entityLeftWorldX <nextX) {
                    right();
                }

            } else if (entityTopWorldY >nextY && entityLeftWorldX>nextX) {
                up();
                //collisionChecker();
                if (collisionOn) {
                    left();
                }
            } else if (entityTopWorldY >nextY && entityLeftWorldX<nextX) {
                up();
                //collisionChecker();
                if (collisionOn) {
                    right();
                }
            } else if (entityTopWorldY <nextY && entityLeftWorldX>nextX) {
                down();
                //collisionChecker();
                if (collisionOn) {
                    left();
                }
            } else if (entityTopWorldY <nextY && entityLeftWorldX<nextX) {
                down();
                //collisionChecker();
                if (collisionOn) {
                    right();
                }
            }


            //this is disabled when the goal is a player
            /*int nextCol = pFinder.pathList.get(0).col;
            int nextRow = pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }*/

        }
    }

}
