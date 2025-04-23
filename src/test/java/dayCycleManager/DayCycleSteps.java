package dayCycleManager;

import controller.GameSettings;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.DayCycleManager;
import model.saveloadmanager.GameState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayCycleSteps {

    private DayCycleManager dayCycleManager;
    private int initialTime;
    private double initialFilterLevel;

    @Given("a dayCycleManager at time {int}")
    public void aDayCycleManagerAtTime(int time) {
        dayCycleManager = DayCycleManager.getInstance();
        dayCycleManager.setTime(time);
        initialTime = time;
    }

    @When("a tick is called")
    public void aTickIsCalled() {
        dayCycleManager.tick();
    }

    @Then("the time should increase by {int} tick worth of milliseconds")
    public void theTimeShouldIncreaseByTickWorthOfMilliseconds(int dt) {
        assertEquals(dt, (dayCycleManager.getTime() - initialTime) / (1000 / GameSettings.FPS));
    }

    @Given("a dayCycleManager at sunset")
    public void aDayCycleManagerAtSunset() {
        dayCycleManager = DayCycleManager.getInstance();
        dayCycleManager.setTime(dayCycleManager.getCycleDuration() / 2 - 1000);
        initialFilterLevel = dayCycleManager.getNightFilterLevel();
    }

    @Then("night filter level should increase")
    public void nightFilterLevelShouldIncrease() {
        assertTrue(initialFilterLevel < dayCycleManager.getNightFilterLevel());
    }
}
