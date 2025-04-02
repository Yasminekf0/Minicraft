package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.entity.Player;
import controller.GameController;
import controller.KeyController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthSteps {
    Player player;
    GameController gp = new GameController();
    KeyController keyI = new KeyController();

    @Given("the player starts with {int} health")
    public void thePlayerStartsWithHealth(int health) {
        player = new Player(gp, keyI);
        player.setHealth(health);

    }

    @When("the player takes {int} damage")
    public void thePlayerTakesDamage(int damage) {
        player.takeDamage(damage);
    }

    @Then("the player's health should be {int}")
    public void thePlayerSHealthShouldBe(int expectedHealth) {
        assertEquals(expectedHealth, player.getHealth());
    }

    @Given("the player has {int} health")
    public void thePlayerHasHealth(int health) {
        player = new Player(gp, keyI);
        player.setHealth(health);
    }

    @When("the player heals by {int}")
    public void thePlayerHealsBy(int healAmount) {
        player.heal(healAmount);

    }
}
