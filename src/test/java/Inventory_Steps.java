import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.entity.Player;
import model.items.Inventory;
import model.items.Item;
import model.items.tools.*;
import model.items.potions.*;
import model.items.blocks.*;

import static org.junit.jupiter.api.Assertions.*;


public class Inventory_Steps {

    private final Player player = Player.getInstance();
    private final Inventory inventory = player.getInventory();
    private Item initialSelectedItem;
    private int initialCount;


    @Given("the player is viewing the {string} section")
    public void thePlayerIsViewingTheSection(String section) {
        inventory.setCurrentSection(section);
        assertEquals(section, inventory.getCurrentSection());
    }

    @When("the player switches section")
    public void thePlayerSwitchesSection() {
        inventory.cycleCurrentSection();

    }

    @Then("the current section should be {string}")
    public void theCurrentSectionShouldBe(String expected) {

        assertEquals(expected, inventory.getCurrentSection());
    }

    @Given("the player has a {string} in the {string} section with count {int}")
    public void thePlayerHasAInTheSectionWithCount(String itemName, String section, int count) {
        Item item = switch (itemName.toLowerCase()) {
            case "sword" -> new Sword();
            case "axe" -> new Axe();
            case "pickaxe" -> new Pickaxe();
            case "potion" -> new HealthPotion() {

            };
            case "rockitem" -> new RockItem();
            default -> throw new IllegalArgumentException("Unknown item: " + itemName);
        };

        inventory.setCurrentSection(section);
        item.setCount(count);
        inventory.addItem(item);
        inventory.setSelectedItem(item);

    }

    @When("the player uses the selected item")
    public void thePlayerUsesTheSelectedItem() {
        Item item = inventory.getSelectedItem();
        initialCount = item.getCount();
        player.use();



    }

    @Then("the {string} count should be {int}")
    public void theCountShouldBe(String itemName, int expectedCount) {
        Item item = inventory.getSelectedItem();
        assertEquals(expectedCount, item.getCount());

    }

    @Given("the player has one {string}")
    public void thePlayerHasOne(String toolName) {
        Tool tool = switch (toolName.toLowerCase()) {
            case "sword" -> new Sword();
            case "axe" -> new Axe();
            case "pickaxe" -> new Pickaxe();
            default -> throw new IllegalArgumentException("Unsupported tool: " + toolName);
        };
        tool.setCount(1);
        inventory.setCurrentSection("Tools");
        inventory.addItem(tool);
        inventory.setSelectedItem(tool);
        initialSelectedItem = tool;
    }


    @When("the player picks up another {string}")
    public void thePlayerPicksUpAnother(String toolName) {

        Tool tool = switch (toolName.toLowerCase()) {
            case "sword" -> new Sword();
            case "axe" -> new Axe();
            case "pickaxe" -> new Pickaxe();
            default -> throw new IllegalArgumentException("Unsupported tool: " + toolName);
        };
        inventory.addItem(tool);
    }

    @Then("the sword should be upgraded and count should still be {int}")
    public void theSwordShouldBeUpgradedAndCountShouldStillBe(int arg0) {
        Tool tool = (Tool) inventory.getSelectedItem();
        assertEquals(1, tool.getCount());
        assertNotEquals("WOOD", tool.getMaterial());
    }

    @Given("the {string} section has more than one item")
    public void theSectionHasMoreThanOneItem(String section) {
        inventory.setCurrentSection(section);
        inventory.addItem(new Sword());
        inventory.addItem(new Pickaxe());
        assertTrue(inventory.getInventorySection(section).size() > 1);
        initialSelectedItem = inventory.getSelectedItem();
    }

    @When("the player cycles to the next item")
    public void thePlayerCyclesToTheNextItem() {
        inventory.cycleCurrentItem();

    }

    @Then("the selected item should be different")
    public void theSelectedItemShouldBeDifferent() {
        assertNotEquals(initialSelectedItem, inventory.getSelectedItem());
    }
}
