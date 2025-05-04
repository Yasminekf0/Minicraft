package model.entity.mobs;

import model.entity.Player;
import model.position.WorldPosition;
import model.world.World;

import java.io.Serializable;

import static view.settings.ScreenSettings.tileSize;

public class MobManager implements Serializable {

    private static MobManager inst = new MobManager();
    private final Enemy[] enemies ;
    private final NPC[] npcs ;

    private final Mob[] mobs;

    protected final int innerSpawnRadius = tileSize * 20;
    protected final int outerSpawnRadius = tileSize * 30;

    private MobManager(){
        inst = this;
        enemies = new Enemy[] { new Enemy(), new Enemy(), new Enemy()};
        npcs = new NPC[] {new NPC()};
        mobs = new Mob[enemies.length + npcs.length];

        System.arraycopy(enemies, 0, mobs, 0, enemies.length);
        System.arraycopy(npcs,    0, mobs, enemies.length, npcs.length);
        spawnMobs();
    }

    public static MobManager getInstance() {
        if (inst == null) {
            inst = new MobManager();
        }
        return inst; }

    private void spawnMobs(){
        for (int i = 0; i < mobs.length; ++i) {
            try {
                mobs[i].setWorldPos(getSpawnPoint());
            } catch (NoSpawnpointFoundException e) {
                System.err.println("Failed to find spawn point for Mob " + i);
            }
        }

    }


    private WorldPosition getSpawnPoint() throws NoSpawnpointFoundException {
        WorldPosition playerPos = Player.getInstance().getWorldPos();
        World world = World.getInstance();

        // Try spawing 30 times, then give up
        for (int i = 0; i < 30; i++) {
            double spawnDistance = Math.random() * (outerSpawnRadius - innerSpawnRadius) + innerSpawnRadius;
            double spawnAngle = Math.random() * 2 * Math.PI;

            // Calculate the spawn coordinates relative to the player's coordinates
            double relativeSpawnX = Math.cos(spawnAngle) * spawnDistance;
            double relativeSpawnY = Math.sin(spawnAngle) * spawnDistance;

            WorldPosition spawnPos = new WorldPosition(
                    playerPos.getX() + relativeSpawnX,
                    playerPos.getY() + relativeSpawnY
            );

            int tileX = spawnPos.getTileXPos();
            int tileY = spawnPos.getTileYPos();

            // Check if tile is walkable and doesn't have a block
            if (world.isWalkable(tileX, tileY) && !world.hasBlock(tileX, tileY)) {
                return spawnPos;
            }
        }

        throw new NoSpawnpointFoundException();
    }
    public Enemy[] getEnemies() { return enemies; }

    public NPC[] getNpcs() {
        return npcs; }

    public Mob[] getMobs() {
        return mobs;
    }


    public void removeMob(Mob mob) {
        Mob[] array;

        if (mob instanceof Enemy){
            array =  enemies;
        }
        else if (mob instanceof NPC){
            array = npcs;
        }
        else return;


        for (int i = 0; i < array.length; i++) {
            if (array[i] == mob) {
                array[i] = null;
                break;
            }
        }

        System.arraycopy(enemies, 0, mobs, 0, enemies.length);
        System.arraycopy(npcs,    0, mobs, enemies.length, npcs.length);
    }


}
class NoSpawnpointFoundException extends Exception {}

