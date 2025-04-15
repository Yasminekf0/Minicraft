package world.generation;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.world.Block;
import model.world.Tile;
import model.world.generator.MapGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WorldGenSteps {

    Tile[][] tiles;
    Block[][] blocks;


    MapGenerator map;

    MapGenerator map2;

    int size;

    int seed;


    @Given("a world size {int}")
    public void aWorldSize(int arg0) {
        size = arg0;
    }

    @And("a seed {int}")
    public void aSeed(int arg0) {
        seed = arg0;
    }


    @When("World Generation is called")
    public void worldGenerationIsCalled() {
        map = new MapGenerator(size,seed);
        tiles =  map.getTiles();
        blocks = map.getBlocks();
    }


    @Then("a Tile 2D array is returned")
    public void aTileArrayIsReturned() {
        assertInstanceOf(Tile[][].class, tiles);
    }

    @Then("a Block 2D array is returned")
    public void aBlockArrayIsReturned() {
        assertInstanceOf(Block[][].class, blocks);
    }

    @Then("two square arrays of the correct size is returned")
    public void twoSquareArraysOfTheCorrectSizeIsReturned() {
        assertEquals(tiles.length,size);
        assertEquals(blocks.length,size);
        for (int i = 0; i<size; i++ ){
            assertEquals(tiles[i].length,size);
            assertEquals(blocks[i].length,size);
        }
    }

    @And("a different world object with same seed")
    public void aDifferentWorldObject() {
        map2 = new MapGenerator(size,seed);
    }

    @Then("Both tile arrays are the same")
    public void bothTileArraysAreTheSame() {
        assertTrue(Arrays.deepEquals(tiles, map2.getTiles()));
    }

    @Then("Both block arrays are the same")
    public void bothBlockArraysAreTheSame() {
        assertTrue(Arrays.deepEquals(blocks, map2.getBlocks()));
    }

    @And("a different world object with different seed")
    public void aDifferentWorldObjectWithDifferentSeed() {
        map2 = new MapGenerator(size,seed+23);
    }

    @Then("Both tile arrays are not the same")
    public void bothWorldArraysAreNotTheSame() {
        assertFalse(Arrays.deepEquals(tiles, map2.getTiles()));
    }

    @Then("Both block arrays are not the same")
    public void bothBlockArraysAreNotTheSame() {
        assertFalse(Arrays.deepEquals(blocks, map2.getBlocks()));
    }
}
