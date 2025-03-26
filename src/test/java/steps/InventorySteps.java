package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import entity.Player;
import main.GamePanel;
import main.KeyInputs;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventorySteps {
    Player player;
    GamePanel gp =  new GamePanel();
    KeyInputs keyI =  new KeyInputs();

    @Given("the player has an empty inventory")
    public void thePlayerHasAnEmptyInventory() { // sus
        player = new Player(gp, keyI);
        player.initializeInventory();

    }

    @When("the player picks up a {string}")
    public void thePlayerPicksUpA(String item) {
        player.addItem(item, item);

    }

    @Then("the inventory should contain {string}")
    public void theInventoryShouldContain(String tool) {
    }

    @Given("the player has items in all inventory sections")
    public void thePlayerHasItemsInAllInventorySections() {
    }

    @When("the player switches to the {string} section")
    public void thePlayerSwitchesToTheSection(String arg0) {
    }

    @Then("the player should see tools in the inventory")
    public void thePlayerShouldSeeToolsInTheInventory() {
    }

    @Given("the player has a {string} in their inventory")
    public void thePlayerHasAInTheirInventory(String arg0) {
    }

    @And("the player finds an upgrade chest")
    public void thePlayerFindsAnUpgradeChest() {
    }

    @When("the player upgrades the {string}")
    public void thePlayerUpgradesThe(String arg0) {
    }

    @Then("the inventory should contain an {string} instead")
    public void theInventoryShouldContainAnInstead(String arg0) {
    }

    @Given("the player has {string} and {string} in their inventory")
    public void thePlayerHasAndInTheirInventory(String arg0, String arg1) {
    }

    @When("the player selects the {string}")
    public void thePlayerSelectsThe(String arg0) {
    }

    @Then("the selected item should be {string}")
    public void theSelectedItemShouldBe(String arg0) {
    }

    @Given("the player is in the {string} inventory section")
    public void thePlayerIsInTheInventorySection(String arg0) {
    }

    @Then("the player should see potions in the inventory")
    public void thePlayerShouldSeePotionsInTheInventory() {
    }

    @When("the player tries to pick up another {string}")
    public void thePlayerTriesToPickUpAnother(String arg0) {
    }

    @Then("the inventory should still contain only one {string}")
    public void theInventoryShouldStillContainOnlyOne(String arg0) {
    }
}

//assertEquals(1, 1);

/*
    @When("the player picks up a {string}")
    public void thePlayerPicksUpAnItem(String item) {
        player.addToInventory(item);
    }

    @Then("the inventory should contain {string}")
    public void theInventoryShouldContain(String item) {
        assertTrue(player.inventoryContains(item));
    }

    @When("the player switches to the {string} section")
    public void thePlayerSwitchesInventorySection(String section) {
        player.switchInventorySection(section);
    }

    @Then("the player should see {string} in the inventory")
    public void thePlayerShouldSeeItems(String section) {
        List<String> items = player.getInventorySection(section);
        assertNotNull(items);
        assertFalse(items.isEmpty());
    }

    @Given("the player has a {string} in their inventory")
    public void thePlayerHasAnItem(String item) {
        player.addToInventory(item);
    }

    @Given("the player finds an upgrade chest")
    public void thePlayerFindsAnUpgradeChest() {
        player.openUpgradeChest();
    }

    @When("the player upgrades the {string}")
    public void thePlayerUpgradesTool(String tool) {
        player.upgradeTool(tool);
    }

    @Then("the inventory should contain an {string} instead")
    public void theInventoryShouldContainUpgradedTool(String upgradedTool) {
        assertTrue(player.inventoryContains(upgradedTool));
    }

    @When("the player tries to pick up another {string}")
    public void thePlayerTriesToPickUpDuplicateTool(String tool) {
        player.addToInventory(tool);
    }

    @Then("the inventory should still contain only one {string}")
    public void theInventoryShouldContainOnlyOneTool(String tool) {
        assertEquals(1, player.countTool(tool));
    }
 */
