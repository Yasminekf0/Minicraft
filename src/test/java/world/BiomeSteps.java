package world;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.world.Biome;
import model.world.WorldBlock;
import model.world.Tile;

import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.*;

public class BiomeSteps {
    Biome biome;
    LinkedHashMap<Tile, Double> tileMap;
    LinkedHashMap<WorldBlock, Double> blockMap;


    @Given("A Biome PLAINS")
    public void aBiomePLAINS() {
        biome = Biome.PLAINS;
    }

    @When("we request the tile HashMap")
    public void weRequestTheTileHashMap() {
        tileMap = biome.getTileWeightMap();
    }

    @When("we request the block HashMap")
    public void weRequestTheBlockHashMap() {
        blockMap = biome.getBlockWeightMap();
    }

    @Then("they correspond to the correct Maps")
    public void theyCorrespondToTheCorrectMaps() {
        assertEquals(tileMap, new LinkedHashMap<>(){{put(Tile.GRASS,0.9); put(Tile.WATER,0.1);}});
        assertEquals(blockMap, new LinkedHashMap<>(){{put(WorldBlock.Tree,0.02); put(WorldBlock.Chest, 0.001);}});
    }
}
