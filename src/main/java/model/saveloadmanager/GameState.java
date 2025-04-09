package model.saveloadmanager;
import model.entity.Player;
import model.world.WorldGenerator;


import java.io.Serializable;


public class GameState implements Serializable {
    private Player player;
    // private WorldGenerator map;
    // TODO: Add support for items

    public GameState(Player player, WorldGenerator map) {
        this.player = player;
        // this.map = map;
    }
}
