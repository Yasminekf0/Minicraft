package entity.player;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.entity.Player;
import model.items.Item;
import model.items.blocks.WoodItem;
import model.items.potions.HealthPotion;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySteps {
    Player player;

    Sword swordItem;

    @Given("the player has an empty inventory")
    public void thePlayerHasAnEmptyInventory() { // sus
        player = Player.getInstance();


    }
    @When("the player picks up a {string} in {string} section")
    public void thePlayerPicksUpAInSection(Item i) {
        player.getInventory().addItem(i);

    }

    @Then("the inventory should contain {string} in {string} section")
    public void theInventoryShouldContainInSection (Item i) {
        assertEquals(i, player.getInventory().getItemFromInventory(i));
    }





    @Given("the player has items in all inventory sections")
    public void thePlayerHasItemsInAllInventorySections() {
        player = Player.getInstance();
        Item a = new Sword();
        Item b = new WoodItem();
        Item c = new HealthPotion();
        player.getInventory().addItem(a);
        player.getInventory().addItem(b);
        player.getInventory().addItem(c);
    }

    @When("the player switches to the {string} section")
    public void thePlayerSwitchesToTheSection(String section) {
        player.getInventory().setCurrentSection(section);
    }


    @Then("the player should see tools in the inventory")
    public void thePlayerShouldSeeToolsInTheInventory() {
        ArrayList<Item> items = player.getInventory().getInventorySection("Tools");
        assertFalse(items.isEmpty());
    }






    @Given("the player has a {string} in {string} section in their inventory")
    public void thePlayerHasAInSectionInTheirInventory(Item i) {
        player = Player.getInstance();
        player.getInventory().addItem(i);
    }

    @And("the player finds an upgrade chest")
    public void thePlayerFindsAnUpgradeChest() {
        player.getInventory().openChest();
    }

    @When("the player upgrades the {string}")
    public void thePlayerUpgradesThe(Item i) {
        player.getInventory().upgradeTool(i);
    }

    @Then("the inventory should contain an {string} instead")
    public void theInventoryShouldContainAnInstead(Item i) {
        assertEquals(i, player.getInventory().getItemFromInventory(i));
    }




    @Given("the player has {string} and {string} in their inventory")
    public void thePlayerHasAndInTheirInventory() {
        player = Player.getInstance();
        Item a = new Sword();
        Item b = new Pickaxe();
        player.getInventory().addItem(a);
        player.getInventory().addItem(b);
    }

    @When("the player selects the {string}")
    public void thePlayerSelectsThe(Item item) {
        player.getInventory().setSelectedItem(item);
    }

    @Then("the selected item should be {string}")
    public void theSelectedItemShouldBe(Item item) {
        assertEquals(item, player.getInventory().getSelectedItem());
    }





    @Given("the player is in the {string} inventory section")
    public void thePlayerIsInTheInventorySection(String section) {
        player = Player.getInstance();
        player.getInventory().setCurrentSection(section);
    }

    @Then("the player should see potions in {string} section in the inventory")
    public void thePlayerShouldSeePotionsInSectionInTheInventory(String section) {
        //ArrayList<String> items = player.getInventorySection(section);
        assertEquals(section, player.getInventory().getCurrentSection());
    }



    @Given("the player has a {string} in {string} in their inventory")
    public void thePlayerHasAInSpecificInventory(Sword a) {
        player = Player.getInstance();
        player.getInventory().addItem(a);
    }

    @When("the player tries to pick up another {string} in {string} section")
    public void thePlayerTriesToPickUpAnotherInSection(Sword b) {
        player.getInventory().addItem(b);
    }

    @Then("the inventory should still contain only one {string} in {string} section")
    public void theInventoryShouldStillContainOnlyOneInSection(Item i) {
        assertEquals(1, player.getInventory().countItem(i));
    }

}
