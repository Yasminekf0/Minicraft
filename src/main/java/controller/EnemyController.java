package controller;
import model.entity.npcs.Mob;
import model.entity.npcs.MobManager;
import model.entity.npcs.pathfinding.Node;
import model.entity.npcs.Enemy;
import model.entity.Player;
import model.position.WorldPosition;
import view.game.elements.EnemyView;
import view.game.elements.MobView;

import java.util.List;

import static view.settings.ScreenSettings.tileSize;

@SuppressWarnings("FieldCanBeLocal")
public class EnemyController extends MobController{
    //private final Enemy[] enemies;
    private List<Node> chasePath = null;
    final Player player = Player.getInstance();

    EnemyController(MobView mobView) {
        super(mobView);
        this.mobs = MobManager.getInstance().getEnemies();
    }
    private void chaseOrWander(Mob mob) {
        Enemy e = (Enemy) mob;
        WorldPosition ep = e.getWorldPos();
        WorldPosition pp = player.getWorldPos();

        // decide if we should chase
        double dx = pp.getX() - ep.getX();
        double dy = pp.getY() - ep.getY();
        double dist = Math.hypot(dx, dy);

        if (dist < tileSize * 6) {
            // within chase range
            e.setOnPath(true);
        } else if (dist > tileSize * 15) {
            // out of chase range
            e.setOnPath(false);
        }

        if (e.getOnPath()) {
            // compute path toward the player's tile
            int goalCol = (pp.getXInt()+player.solidArea.x)/tileSize;
            int goalRow = (pp.getYInt()+player.solidArea.y)/tileSize;

            if (chasePath == null || chasePath.isEmpty()) {
                chasePath = e.searchPath(goalCol, goalRow);
            }
            //pathList = e.searchPath(goalCol, goalRow); //if()then (moveuntil) //dx,dy lost
            if (chasePath != null && !chasePath.isEmpty()) {
                Node next = chasePath.getFirst();

                double entityCenterX = e.getWorldPos().getX()
                        + e.solidArea.x
                        + e.solidArea.width  * 0.5;
                double entityCenterY = e.getWorldPos().getY()
                        + e.solidArea.y
                        + e.solidArea.height * 0.5;

                double tileCenterX   = next.col * tileSize + tileSize * 0.5;
                double tileCenterY   = next.row * tileSize + tileSize * 0.5;

                double ux = tileCenterX - entityCenterX;
                double uy = tileCenterY - entityCenterY;

                double distt = Math.hypot(ux, uy);

                double ndx = 0, ndy = 0;
                if (distt > 0) {
                    ndx = ux / distt;
                    ndy = uy / distt;
                }

                if (distt <= e.getSpeed()) {
                    double snapX = next.col*tileSize + tileSize/2.0
                            - (e.solidArea.x + e.solidArea.width/2.0);
                    double snapY = next.row*tileSize + tileSize/2.0
                            - (e.solidArea.y + e.solidArea.height/2.0);
                    e.getWorldPos().set(snapX, snapY);
                    chasePath.removeFirst();
                } else {
                    e.dx = ndx;
                    e.dy = ndy;

                    e.moveUntil(ndx, ndy);
                    if (distt <= e.getSpeed()) {
                        double snapX = tileCenterX - (e.solidArea.x + e.solidArea.width * 0.5);
                        double snapY = tileCenterY - (e.solidArea.y + e.solidArea.height * 0.5);
                        e.getWorldPos().set(snapX, snapY);
                            chasePath.removeFirst();
                        //}
                    }

                    angle = Math.atan2(e.dy, e.dx);
                }
            }




        } else {
            wander(e);
            /*if (--e.wanderSteps <= 0) {
                e.wanderSteps = 50 + rand.nextInt(100);
                e.pathStage = rand.nextInt(8);
            }
            dx = 0;
            dy = 0;
            switch (e.pathStage) {
                case 0 -> {
                    dx = 1;
                    dy = 0;
                }  // right
                case 1 -> {
                    dx = 0;
                    dy = 1;
                }  // down
                case 2 -> {
                    dx = -1;
                    dy = 0;
                }  // left
                case 3 -> {
                    dx = 0;
                    dy = -1;
                }  // up
                case 4 -> {
                    dx = 1;
                    dy = 1;
                }
                case 5 -> {
                    dx = -1;
                    dy = -1;
                }
                case 6 -> {
                    dx = -1;
                    dy = 1;
                }
                case 7 -> {
                    dx = 1;
                    dy = -1;
                }
            } //take out 0.5s

            e.dx = dx;
            e.dy = dy;

            double length = Math.sqrt(dx * dx + dy * dy);
            double normalizedDx = dx / length;
            double normalizedDy = dy / length;

            e.moveUntil(normalizedDx, normalizedDy);
            e.getCollisionChecker().checkPlayer(e, 0, 0);
            angle = Math.atan2(e.dy, e.dx);*/
        }
    }

    @Override
    protected void act(Mob mob) {
        chaseOrWander(mob);
    }
}