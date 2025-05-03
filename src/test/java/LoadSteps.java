import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.saveloadmanager.*;
import model.entity.Player;
import model.world.World;
import model.world.DayCycleManager;
import view.game.core.MainView;
import controller.StartController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoadSteps {

    private final String savePath = "saves/save1.dat";
    private GameState originalState;
    private GameState loadedState;
    private boolean loadFailed;


    @Given("a saved game file exists at {string}")
    public void aSavedGameFileExistsAt(String path) {
        originalState = new GameState();
        Player.getInstance().setHealth(7); // small change to test
        try {
            SaveLoadManager.saveGame(originalState, path);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while saving the game state", e);
        }

        File file = new File(path);
        assertTrue(file.exists(), "Save file should exist");
    }

    @When("I press the {string} button")
    public void iPressTheButton(String buttonLabel) {
        try {
            if (!buttonLabel.equalsIgnoreCase("Load Game")) {
                throw new UnsupportedOperationException("Only Load Game button is supported in this test");
            }
            loadedState = SaveLoadManager.loadGame(savePath);

            // restore state manually
            Player.setInstance(loadedState.getPlayer());
            World.setInstance(loadedState.getWorld());
            DayCycleManager.setInstance(loadedState.getDayCycleManager());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("An error occurred while loading the game state", e);
        }
    }

    @Then("the game state should be restored from the file")
    public void theGameStateShouldBeRestoredFromTheFile() {
        assertEquals(loadedState.getPlayer().getHealth(),
                Player.getInstance().getHealth(),
                "Player health should match loaded state");

        assertEquals(loadedState.getWorld(),
                World.getInstance(),
                "World instance should match loaded state");

        assertEquals(loadedState.getDayCycleManager().getTime(),
                DayCycleManager.getInstance().getTime(),
                "Day cycle time should match loaded state");

    }


    @Given("the save file is missing or corrupted")
    public void theSaveFileIsMissingOrCorrupted() throws IOException {
        File file = new File(savePath);
        file.getParentFile().mkdirs();

        // Option 1: delete it
        if (file.exists()) {
            file.delete();
        }

        // Option 2: corrupt it by writing garbage
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("corrupted data not a GameState");
        }
    }

    @When("I attempt to load the game")
    public void iAttemptToLoadTheGame() {
        loadFailed = false;
        try {
            SaveLoadManager.loadGame(savePath);
        } catch (Exception e) {
            loadFailed = true;
        }
    }

    @Then("I should receive a load error message")
    public void iShouldReceiveALoadErrorMessage() {
        assertTrue(loadFailed, "Expected load to fail due to missing or corrupted file");
    }
}
