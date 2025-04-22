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

    private int dx = 0, dy = 0;

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
        Random rand = new Random();
        int i = rand.nextInt(100)+1; //random num 1 to 100
        int ddx = 0, ddy = 0;

        if (i<51) {
            ddx = 0;
            ddy = 1;
        } else {
            ddx = 0;
            ddy = -1;
        }

        /*if (dx + ddx <= 1 && dx + ddx >= -1) dx += ddx;
        if (dy + ddy <= 1 && dy + ddy >= -1) dy += ddy;

        if (dx == 0 && dy == 0) {
            movementTimer.stop();
            npcView.update(false, 0);
        } else if (!movementTimer.isRunning()){
            movementTimer.start();
        }*/


        if (Math.abs(dx + ddx) <= 1) dx += ddx;
        if (Math.abs(dy + ddy) <= 1) dy += ddy;

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
            npcView.update(true, 0); // Moving, update direction
        }

    }

    private void updateNPC() {
        double length = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = (double) dx / length;
        double normalizedDy = (double) dy / length;
        npc.moveUntil(normalizedDx, normalizedDy);

        double angle = Math.atan2(dy, dx);
        npcView.update(true, 0);
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

