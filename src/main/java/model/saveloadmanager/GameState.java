package model.saveloadmanager;
import model.entity.Player;
import model.world.World;


import java.io.Serializable;




public class GameState implements Serializable {
    // TODO: Add support for items
    World world = World.getInstance();
    Player player = Player.getInstance();

    public Player getPlayer() {
        return player;
    }
    public World getWorld() {
        return world;
    }
}
