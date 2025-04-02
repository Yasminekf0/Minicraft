package world.position;

public class ChunkPosition extends Position<Integer> {

    public ChunkPosition(Integer x, Integer y) {
        super(x, y);
    }

    public WorldPosition getTopLeftCoords(){
        return new WorldPosition(16*x, 16*y);
    }

    public WorldPosition getWorldPosInChunk(double localX, double localY){
        return new WorldPosition(x*16 + localX, y*16 + localY);
    }

}
