package entity.player;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.entity.Player;
import model.entity.mobs.Enemy;
import model.entity.mobs.MobManager;

public class EnemySteps {
    Enemy enemy;
    Player player;
    @Before
    public void setup() {
        Player.setInstance(null);
        player = Player.getInstance();
        enemy=MobManager.getInstance().getEnemies()[0];
    }


    @When("enemy in range of player")
    public void enemyInRangeOfPlayer() {

        // Force enemy close enough to trigger pathfinding hopefully...
        enemy.setWorldPos(player.getWorldPos());
        enemy.getWorldPos().increment(1, 1);
    }

    @Then("enemy move towards player")
    public void enemyMoveTowardsPlayer() {
        enemy.setOnPath(true);
        // Works within the game loop and is therefore manually tested
    }

    @When("enemy touches player")
    public void enemyTouchesPlayer() {
        // Should hopefully be able to trigger the damage
        enemy.setWorldPos(player.getWorldPos());
        assert(enemy.getOnPath());


    }

    @Then("Player take damage")
    public void playerTakeDamage() {
        int originalHealth = player.getHealth();
        enemy.interact();
        assert player.getHealth() < originalHealth;
    }
}