package model.items;

import model.entity.Player;
import model.world.World;

public abstract class Item {

    protected Player player;
    protected World world;
    protected final String section;
    protected int count;
    public Item(String section) {
        this.section = section;
        this.count = 0;
        this.player = Player.getInstance();
        this.world = World.getInstance();
    }


    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getSection() {
        return section;
    }


}
