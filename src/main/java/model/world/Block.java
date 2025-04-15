package model.world;

import java.util.Arrays;

public enum Block {

    Wood(new Tile[]{},25),
    Rock(new Tile[]{Tile.STONE}, 50),
    Tree(new Tile[]{Tile.GRASS}, 30),
    Chest(new Tile[]{Tile.STONE,Tile.SAND,Tile.SNOW,Tile.DIRT,Tile.GRASS}, 50);

    private final Tile[] spawnableTiles;

    private final int blockDurability;


    Block(Tile[] spawnableTiles, int blockDurabilty){
        this.spawnableTiles = spawnableTiles;
        this.blockDurability = blockDurabilty;
    }

    public boolean isSpawnableTile(Tile tile){
        return Arrays.asList(spawnableTiles).contains(tile);
    }

    public int getBlockDurabilty(){
        return blockDurability;
    }

}
