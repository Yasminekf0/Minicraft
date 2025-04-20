package world;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.world.WorldBlock;
import model.world.Tile;
import model.world.World;

import static org.junit.jupiter.api.Assertions.*;

public class WorldClassSteps {

    World world;
    WorldBlock worldBlock;

    Tile[][] tiles;

    WorldBlock[][] worldBlocks;

    int x;

    int y;

    @Given("a world with seed {int} and size {int}")
    public void aWorldWithSeedAndSize(int seed, int size) {
        world = World.getInstance();
    }

    @When("We request block at coords {int} {int}")
    public void weRequestBlockAtCoords(int x, int y) {
        worldBlock = world.getBlock(x,y);
    }

    @Then("a block is returned if it exists")
    public void aBlockIsReturnedIfItExists() {
        assertTrue(worldBlock instanceof WorldBlock | worldBlock == null);
    }

    @And("the Tile at coords {int} {int} is walkable")
    public void theTileAtCoordsIsWalkable(int x, int y) {
        this.x = x;
        this.y = y;
        world.getTileMap()[x][y] = Tile.GRASS;
    }

    @And("there is no Block")
    public void thereIsNoBlock() {
        world.getBlockMap()[x][y] = null;
    }

    @Then("The tile should be walkable")
    public void theTileShouldBeWalkable() {
        assertTrue(world.isWalkable(x,y));
    }


    @And("there is a Block at {int} {int}")
    public void thereIsABlockAt(int x, int y) {
        this.x = x;
        this.y = y;
        world.getBlockMap()[x][y] = WorldBlock.Wood;
        System.out.println(world.getBlockMap()[x][y]);
    }

    @When("we break the block")
    public void weBreakTheBlock() {
        world.breakBlock(x,y);
    }

    @Then("there should be no block anymore")
    public void thereShouldBeNoBlockAnymore() {
        assertNull(world.getBlock(x,y));
    }

    @When("We request the tiles")
    public void weRequestTheTiles() {
        tiles = world.getTileMap();
    }

    @Then("the array of tiles is returned")
    public void theArrayOfTilesIsReturned() {
        assertInstanceOf(Tile[][].class, tiles);
    }

    @When("We request the blocks")
    public void weRequestTheBlocks() {
        worldBlocks = world.getBlockMap();
    }

    @Then("the array of blocks is returned")
    public void theArrayOfBlocksIsReturned() {
        assertInstanceOf(WorldBlock[][].class, worldBlocks);
    }


}
