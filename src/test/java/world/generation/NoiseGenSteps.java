package world.generation;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.world.generator.Noise;
import model.world.generator.NoiseGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class NoiseGenSteps {

    Noise[][] noiseArray;

    NoiseGenerator noiseGenerator1;

    NoiseGenerator noiseGenerator2;

    int size;

    int seed;

    @Given("a noise size {int}")
    public void aNoiseSize(int arg0) {
        size = arg0;
    }

    @And("a noise seed {int}")
    public void aNoiseSeed(int arg0) {
        seed = arg0;
    }

    @When("Noise Generation is called")
    public void noiseGenerationIsCalled() {
        noiseGenerator1 = new NoiseGenerator(size,seed);
        noiseArray =  noiseGenerator1.getNoiseArray();
    }

    @Then("a Noise 2D array is returned")
    public void aNoiseArrayIsReturned() {
        assertInstanceOf(Noise[][].class, noiseArray);
    }
    @Then("a square array of the correct size is returned")
    public void aSquareArrayOfTheCorrectSizeIsReturned() {
        assertEquals(noiseArray.length,size);
        for (int i = 0; i<size; i++ ){
            assertEquals(noiseArray[i].length,size);
        }
    }

    @And("a different noise object with different seed")
    public void aDifferentNoiseObjectWithDifferentSeed() {
        noiseGenerator2 = new NoiseGenerator(size, seed+69);
    }

    @Then("Both noise arrays are not the same")
    public void bothNoiseArraysAreNotTheSame() {
        assertFalse(Arrays.deepEquals(noiseArray, noiseGenerator2.getNoiseArray()));
    }
    @And("a different noise object with the same seed")
    public void aDifferentNoiseObjectWithTheSameSeed() {
        noiseGenerator2 = new NoiseGenerator(size, seed);
    }

    @Then("Both noise arrays are the same")
    public void bothNoiseArraysAreTheSame() {
        assertTrue(Arrays.deepEquals(noiseArray, noiseGenerator2.getNoiseArray()));
    }


}
