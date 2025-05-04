package Items;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.entity.Player;
import model.items.potions.HealthPotion;
import model.items.potions.SpeedPotion;


public class PotionsSteps {

    Player player = Player.getInstance();
    HealthPotion healthPotion;
    SpeedPotion speedPotion;
    int initialHealth;
    int initialSpeed;


    @Given("that the player has health potions in his inventory")
    public void thatThePlayerHasHealthPotionsInHisInventory() {
        healthPotion = new HealthPotion();
        player.getInventory().addItem(healthPotion);
        player.getInventory().setCurrentSection("Potions");
        player.getInventory().setSelectedItem(healthPotion);
    }

    @Given("the player is at {int} health")
    public void thePlayerIsAtHealth(int health) {
        player.setHealth(health);
        initialHealth = health;
    }

    @When("the player uses a health potion")
    public void thePlayerUsesAHealthPotion() {
        player.use();
    }

    @Then("the player health should be {int}")
    public void thePlayerHealthShouldBe(int expectedHealth) {
        assert player.getHealth() == expectedHealth :
                "Expected health: " + expectedHealth + ", but was: " + player.getHealth();

    }

    @Given("that the player has speed potions in his inventory")
    public void thatThePlayerHasSpeedPotionsInHisInventory() {
        speedPotion = new SpeedPotion();
        player.getInventory().addItem(speedPotion);
        player.getInventory().setCurrentSection("Potions");
        player.getInventory().setSelectedItem(speedPotion);
    }

    @When("the player uses a speed potion")
    public void thePlayerUsesASpeedPotion() {
        initialSpeed = player.getSpeed();
        player.use();
    }

    @Then("the player should increase the player speed")
    public void thePlayerShouldIncreaseThePlayerSpeed() {
        assert player.getSpeed() > initialSpeed :
                "Expected increased speed, but was: " + player.getSpeed();
    }
}
