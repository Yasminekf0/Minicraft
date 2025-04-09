package model.world;

import java.util.Arrays;

public enum Block {

    Wood(new Tile[]{}),
    Rock(new Tile[]{Tile.STONE}),
    Tree(new Tile[]{Tile.GRASS}),
    Chest(new Tile[]{Tile.STONE,Tile.SAND,Tile.SNOW,Tile.DIRT,Tile.GRASS});

    private final Tile[] spawnableTiles;

    Block(Tile[] spawnableTiles){
        this.spawnableTiles = spawnableTiles;
    }

    public boolean isSpawnableTile(Tile tile){
        return Arrays.asList(spawnableTiles).contains(tile);
    }

}
