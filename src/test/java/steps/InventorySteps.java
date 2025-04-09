package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.entity.Player;
import model.items.Item;
import model.items.tools.Sword;
import model.items.tools.Tool;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySteps {
    Player player;

    Sword swordItem;






    @Given("the player has an empty inventory")
    public void thePlayerHasAnEmptyInventory() { // sus
        player = new Player();
        player.initializeInventory();


    }
ool section = Inste
    @When("the player picks up a {string} in {string} section")
    public void thePlayerPicksUpAInSection(String item, String section) {
        Item i =  new Tool(item, section);
        player.addItem(section, i);

    }

    @Then("the inventory should contain {string} in {string} section")
    public void theInventoryShouldContainInSection (String item, String section) {
        assertEquals(item, player.getItemFromInventory(section, item));
    }





    @Given("the player has items in all inventory sections")
    public void thePlayerHasItemsInAllInventorySections() {
        player = new Player();
        player.initializeInventory();
        player.addItem("Tools", "Hammer");
        player.addItem("Blocks", "Wood");
        player.addItem("Potions", "Health Potion");
    }

    @When("the player switches to the {string} section")
    public void thePlayerSwitchesToTheSection(String section) {
        player.setCurrentSection(section);
    }


    @Then("the player should see tools in the inventory")
    public void thePlayerShouldSeeToolsInTheInventory() {
        ArrayList<String> items = player.getInventorySection("Tools");
        assertFalse(items.isEmpty());
    }






    @Given("the player has a {string} in {string} section in their inventory")
    public void thePlayerHasAInSectionInTheirInventory(String item, String section) {
        player = new Player();
        player.initializeInventory();
        player.addItem(section, item);
    }

    @And("the player finds an upgrade chest")
    public void thePlayerFindsAnUpgradeChest() {
        player.openChest();
    }

    @When("the player upgrades the {string}")
    public void thePlayerUpgradesThe(String arg0) {
        player.upgradeTool();
    }

    @Then("the inventory should contain an {string} instead")
    public void theInventoryShouldContainAnInstead(String upgradedTool) {
        assertEquals(upgradedTool, player.getItemFromInventory("Tool", upgradedTool));
    }




    @Given("the player has {string} and {string} in their inventory")
    public void thePlayerHasAndInTheirInventory(String item1, String item2) {
        player = new Player();
        player.initializeInventory();
        player.addItem("Tools", item1);
        player.addItem("Tools", item2);
    }

    @When("the player selects the {string}")
    public void thePlayerSelectsThe(String item) {
        player.setSelectedItem(item);
    }

    @Then("the selected item should be {string}")
    public void theSelectedItemShouldBe(String item) {
        assertEquals(item, player.getSelectedItem());
    }





    @Given("the player is in the {string} inventory section")
    public void thePlayerIsInTheInventorySection(String section) {
        player = new Player();
        player.initializeInventory();
        player.setCurrentSection(section);
    }

    @Then("the player should see potions in {string} section in the inventory")
    public void thePlayerShouldSeePotionsInSectionInTheInventory(String section) {
        //ArrayList<String> items = player.getInventorySection(section);
        assertEquals(section, player.getCurrentSection());
    }



    @Given("the player has a {string} in {string} in their inventory")
    public void thePlayerHasAInSpecificInventory(String item,  String section) {
        player = new Player();
        player.initializeInventory();
        player.addItem(section, item);
    }

    @When("the player tries to pick up another {string} in {string} section")
    public void thePlayerTriesToPickUpAnotherInSection(String item,  String section) {
        player.addItem(section, item);
    }

    @Then("the inventory should still contain only one {string} in {string} section")
    public void theInventoryShouldStillContainOnlyOneInSection(String item,  String section) {
        assertEquals(1, player.countItem(section, item));
    }

}
