package model.saveloadmanager;
import model.world.DayCycleManager;
import model.entity.Player;
import model.world.World;


import java.io.Serializable;




public class GameState implements Serializable {
    final World world = World.getInstance();
    final Player player = Player.getInstance();
    final DayCycleManager dayCycleManager = DayCycleManager.getInstance();

    public Player getPlayer() {
        return player;
    }
    public World getWorld() {
        return world;
    }
    public DayCycleManager getDayCycleManager() {
        return dayCycleManager;
    }

}
