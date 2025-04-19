package model.world;

import model.items.Item;
import model.items.blocks.BlockItem;
import model.items.blocks.RockItem;
import model.items.blocks.WoodItem;
import model.items.potions.HealthPotion;
import model.items.potions.SpeedPotion;
import model.items.tools.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public enum WorldBlock {

    Wood(new Tile[]{},25,
            new LinkedHashMap<>(){{put(new WoodItem(), 1.0);}},
            new Axe()
            ),
    Rock(new Tile[]{Tile.STONE}, 50,
            new LinkedHashMap<>(){{put(new RockItem(), 1.0);}},
            new Pickaxe()
            ),
    Tree(new Tile[]{Tile.GRASS}, 30,
            new LinkedHashMap<>(){{put(new WoodItem(), 1.0);}},
            new Axe()
            ),
    Chest(new Tile[]{Tile.STONE,Tile.SAND,Tile.SNOW,Tile.DIRT,Tile.GRASS}, 50,
            new LinkedHashMap<>(){{put(new HealthPotion(), 0.2); put(new SpeedPotion(), 0.2); put(new Axe(), 0.2); put(new Pickaxe(), 0.2); put(new Sword(), 0.2);}},
            new Axe()
            );

    private final Tile[] spawnableTiles;

    private final int blockDurability;
    private final Tool requiredTool;
    private final HashMap<Item, Double> drops;
    private final Random dropRandomizer = new Random();


    WorldBlock(Tile[] spawnableTiles, int blockDurabilty, HashMap<Item, Double> drops, Tool requiredTool){
        this.spawnableTiles = spawnableTiles;
        this.blockDurability = blockDurabilty;
        this.drops = drops;
        this.requiredTool = requiredTool;
    }

    public boolean isSpawnableTile(Tile tile){
        return Arrays.asList(spawnableTiles).contains(tile);
    }

    public int getBlockDurabilty(){
        return blockDurability;
    }

    public Item getDrop(){
        double randomDouble = dropRandomizer.nextDouble(1);
        double accumulator = 0;
        for (Item i : drops.keySet()){
            if (drops.get(i)+accumulator >= randomDouble) return i;
            else accumulator += drops.get(i);
        }
        return null;
    }

    public boolean isCorrectTool(Item i){
        return i.getClass().isInstance(requiredTool);
    }

}
