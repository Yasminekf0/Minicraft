package World.Generation;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tile.Tile;
import world.WorldGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WorldGenSteps {

    Tile[][] mapArray;

    WorldGenerator map;

    WorldGenerator map2;

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
        map = new WorldGenerator(size,seed);
        mapArray =  map.getWorld();
    }


    @Then("a Tile 2D array is returned")
    public void aTileDArrayIsReturned() {
        assertInstanceOf(Tile[][].class, mapArray);
    }


    @Then("a square array of the correct size is returned")
    public void aSquareArrayOfTheCorrectSizeIsReturned() {
        assertEquals(mapArray.length,size);
        for (int i = 0; i<size; i++ ){
            assertEquals(mapArray[i].length,size);
        }
    }

    @And("a different world object with same seed")
    public void aDifferentWorldObject() {
        map2 = new WorldGenerator(size,seed);
    }

    @Then("Both world arrays are the same")
    public void bothWorldArraysAreTheSame() {
        assertTrue(Arrays.deepEquals(mapArray, map2.getWorld()));
    }

    @And("a different world object with different seed")
    public void aDifferentWorldObjectWithDifferentSeed() {
        map2 = new WorldGenerator(size,seed+23);
    }

    @Then("Both world arrays are not the same")
    public void bothWorldArraysAreNotTheSame() {
        assertFalse(Arrays.deepEquals(mapArray, map2.getWorld()));
    }
}
