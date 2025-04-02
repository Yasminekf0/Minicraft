package player;

import model.entity.Player;
import model.position.WorldPosition;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import controller.GameController;
import controller.KeyController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagonalMovementSteps {
    Player player;

    @Given("a Player")
    public void aPlayer() {
        player = new Player(new GameController(), new KeyController());
    }

    @And("his WorldPosition is {int} {int}")
    public void hisWorldPositionIs(int arg0, int arg1) {
        player.setWorldPos(new WorldPosition(arg0, arg1));
    }

    @And("a KeyInput of {string}")
    public void aKeyInputOf(String arg0) {
        switch (arg0) {
            case "W" -> player.getKeyI().upPressed = true;
            case "S" -> player.getKeyI().downPressed = true;
            case "A" -> player.getKeyI().leftPressed = true;
            case "D" -> player.getKeyI().rightPressed = true;
        }
    }

    @When("update Player")
    public void updatePlayer() {
        player.update();
    }

    @Then("the player should move both up and right")
    public void thePlayerShouldMoveBothUpAndRight() {
        assertEquals(player.getSpeed() / Math.sqrt(2), player.getWorldPos().getX());
        assertEquals(-player.getSpeed() / Math.sqrt(2), player.getWorldPos().getY());
    }
}
