package entity.player;

import io.cucumber.java.en.Then;

import io.cucumber.java.en.When;
import model.entity.mobs.Mob;
import model.entity.mobs.MobManager;

import static org.junit.jupiter.api.Assertions.*;


public class VillagerSteps {
    @When("world is generated or loaded")
    public void worldIsGeneratedOrLoaded() {
        MobManager.getInstance();
    }

    @Then("Villager Spawn")
    public void villagerSpawn() {
        assertTrue(MobManager.getInstance().getNpcs().length > 0, "Expected Villager count to be greater than 0");
    }

    @When("the villager exists")
    public void theVillagerExists() {
        assertNotNull(MobManager.getInstance().getNpcs(), "Expected Villager count to be greater than 0");
    }

    @Then("change position")
    public void changePosition() {
        Mob villager = MobManager.getInstance().getNpcs()[0];
        double originalX = villager.getWorldPos().getX();
        villager.moveUntil(2,1);
        double newX = villager.getWorldPos().getX();
        assertTrue(originalX != newX);
    }

}

