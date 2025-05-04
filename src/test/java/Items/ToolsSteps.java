package Items;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.items.Inventory;
import model.entity.Player;
import model.items.tools.*;


import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToolsSteps {

    Tool tool;
    String initialMaterial;

    @Given("that the player has a {string} in {string} section in their inventory")
    public void thatThePlayerHasAInSectionInTheirInventory(String toolName, String section) {
        Player player = Player.getInstance();
        Inventory inventory = player.getInventory();
        inventory.setCurrentSection(section);

        switch (toolName.toLowerCase()) {
            case "axe" -> tool = new Axe();
            case "pickaxe" -> tool = new Pickaxe();
            case "sword" -> tool = new Sword();
            default -> throw new IllegalArgumentException("Unsupported tool: " + toolName);
        }

        inventory.addItem(tool);
        inventory.setSelectedItem(tool);

        initialMaterial = tool.getMaterial(); // Store material as string
    }

    @When("the tool is upgraded")
    public void theToolIsUpgraded() {
        tool.upgrade();
    }

    @Then("the {string} should mine faster")
    public void theShouldMineFaster(String toolName) {
        assertTrue(tool instanceof Pickaxe || tool instanceof BreakingTools);
        assertNotEquals("Tool material should have upgraded", initialMaterial, tool.getMaterial());
    }

    @Then("the {string} should chop faster")
    public void theShouldChopFaster(String toolName) {
        assertTrue(tool instanceof Axe || tool instanceof BreakingTools);
        assertNotEquals("Tool material should have upgraded", initialMaterial, tool.getMaterial());
    }

    @Then("the {string} should do more damage")
    public void theShouldDoMoreDamage(String toolName) {
        assertTrue(tool instanceof Sword);
        assertNotEquals("Tool material should have upgraded", initialMaterial, tool.getMaterial());
    }
}
