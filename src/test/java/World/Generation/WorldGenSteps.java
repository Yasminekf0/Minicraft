package World.Generation;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.GamePanel;
import tile.Tile;
import world.generator.WorldMap;

public class WorldGenSteps {

    GamePanel gp = new GamePanel();
    int size;

    Tile[][] mapArray;

    WorldMap map;

    @Given("a world size {int}")
    public void aWorldSize(int arg0) {
        size = arg0;
    }

    @When("World Generation is called")
    public void worldGenerationIsCalled() {
        map = new WorldMap(size);
        map.getWorld();

    }

    @Then("fill the {int}D array with noisy Tiles")
    public void fillTheDArrayWithNoisyTiles(int arg0) {
    }



}
