package controller;

import model.entity.NPC;
import view.NPCView;
import view.PlayerView;

import javax.swing.*;
import java.util.Random;

public class NPCController {
    private final NPC npc;
    private final NPCView npcView;

    private final int delay = 1000 / 60;
    private final double speed = 0.1;

    private int pathStage = 0; // 0 = right, 1 = down, 2 = left, 3 = up
    private int stepsRemaining = 5;

    private int dx = 0, dy = 0;
    private int ddx = 0, ddy = 1;

    private final Timer movementTimer;

    //private final Timer actionTimer;

    NPCController(NPCView npcView){
        this.npc = NPC.getInstance();
        this.npcView = npcView;

        movementTimer = new Timer(delay, _ -> {
            updateNPC();
        });

        /*actionTimer = new Timer(100, _ -> {
            npc.setAction();
        });*/

    }

    void updateMoving(){

        if (stepsRemaining <= 0) {
            pathStage = (pathStage + 1) % 4; // loop through 0 -> 1 -> 2 -> 3 -> 0
            stepsRemaining = 5; // reset steps for new side
        }

        switch (pathStage) {
            case 0: // Move right
                ddx = 1;
                ddy = 0;
                break;
            case 1: // Move down
                ddx = 0;
                ddy = 1;
                break;
            case 2: // Move left
                ddx = -1;
                ddy = 0;
                break;
            case 3: // Move up
                ddx = 0;
                ddy = -1;
                break;
        }


        if (dx + ddx <= 1 && dx + ddx >= -1) dx += ddx;
        if (dy + ddy <= 1 && dy + ddy >= -1) dy += ddy;

        if (dx == 0 && dy == 0) {
            if (movementTimer.isRunning()) {
                movementTimer.stop();
            }
            npcView.update(false, 0); // Not moving
        } else {
            if (!movementTimer.isRunning()) {
                movementTimer.start();
            }
            double newAngle = Math.atan2(dy, dx);
            npcView.update(true, newAngle);
            // Moving, update direction
        }

        stepsRemaining--;

    }

    private void updateNPC() {
        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = (double) dx / length;
        double normalizedDy = (double) dy / length;
        npc.moveUntil(speed * normalizedDx, speed * normalizedDy);

        double angle = Math.atan2(dy, dx);
        npcView.update(true, angle);
    }


    /*public void doAction() {
        if (!actionTimer.isRunning()){
            npc.setAction();
            actionTimer.start();
        }
    }

    public void stopAction(){
        actionTimer.stop();
    }*/

}


//Random rand = new Random();
//int x = 0;
/*while (x<100) {
            int i = rand.nextInt(100) + 1; //random num 1 to 100

            if (i < 51) {
                ddx = 0;
                ddy = 1;
            } else {
                ddx = 0;
                ddy = -1;
            }
            x++;
        }*/



        /*if (dx == 0 && dy == 0) {
            movementTimer.stop();
            npcView.update(false, 0);
        } else if (!movementTimer.isRunning()){
            movementTimer.start();
        }*/


//if (Math.abs(dx + ddx) <= 1) dx += ddx;
//if (Math.abs(dy + ddy) <= 1) dy += ddy;
