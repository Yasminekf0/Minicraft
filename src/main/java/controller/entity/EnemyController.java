package controller.entity;
import model.entity.mobs.Mob;
import model.entity.mobs.MobManager;
import model.entity.mobs.pathfinding.Node;
import model.entity.mobs.Enemy;
import model.entity.Player;
import model.position.WorldPosition;
import view.game.elements.MobView;

import java.util.List;

import static view.settings.ScreenSettings.tileSize;


public class EnemyController extends MobController{
    //private final Enemy[] enemies;
    private List<Node> chasePath = null;
    final Player player = Player.getInstance();

    public EnemyController(MobView mobView) {
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

        }
    }

    @Override
    protected void act(Mob mob) {
        chaseOrWander(mob);
    }
}