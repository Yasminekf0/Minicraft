package SaveLoadManager;

import io.cucumber.java.en.*;
import model.entity.Player;
import model.saveloadmanager.GameState;
import model.saveloadmanager.SaveLoadManager;
import model.world.WorldGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SaveSteps {

    private Player player;
    private WorldGenerator world;
    private GameState gameState;
    private final String savePath = "test_saves/savegame.dat";
    private boolean saveError = false;

    @Given("the game is running")
    public void the_game_is_running() {
        player = new Player();
        world = new WorldGenerator(100, 1234);
        gameState = new GameState(player, world);
    }


    @When("I save the game")
    public void i_save_the_game() {
        try {
            SaveLoadManager.saveGame(gameState, savePath);
        } catch (IOException e) {
            saveError = true;
        }
    }

    @Then("a file should be created at the specified path")
    public void a_file_should_be_created_at_the_specified_path() {
        assertTrue(Files.exists(Path.of(savePath)), "Save file was not created");
    }

    @And("the file should contain the serialized game state")
    public void the_file_should_contain_the_serialized_game_state() throws IOException, ClassNotFoundException {
        GameState loadedState = SaveLoadManager.loadGame(savePath);
        assertNotNull(loadedState, "Loaded state is null");
    }

    @Given("a save file already exists")
    public void a_save_file_already_exists() throws IOException {
        SaveLoadManager.saveGame(gameState, savePath);
        assertTrue(Files.exists(Path.of(savePath)), "Initial save file not created");
    }

    @Then("the previous save file should be overwritten")
    public void the_previous_save_file_should_be_overwritten() throws IOException {
        long beforeSize = Files.size(Path.of(savePath));

        // Change state and save again
        player.addItem("Blocks", "Stone");
        SaveLoadManager.saveGame(gameState, savePath);
        long afterSize = Files.size(Path.of(savePath));

        assertNotEquals(beforeSize, afterSize, "File size did not change, may not have been overwritten");
    }

    @Given("the save directory does not exist or is not writable")
    public void the_save_directory_does_not_exist_or_is_not_writable() {
        File file = new File("readonly_dir/");
        file.setWritable(false); // Simulated restriction
    }

    @When("I attempt to save the game")
    public void i_attempt_to_save_the_game() {
        try {
            SaveLoadManager.saveGame(gameState, "/readonly_dir/savegame.dat");
        } catch (IOException e) {
            saveError = true;
        }
    }

    @Then("I should receive a save error message")
    public void i_should_receive_a_save_error_message() {
        assertTrue(saveError, "Save error was not triggered");
    }
}
