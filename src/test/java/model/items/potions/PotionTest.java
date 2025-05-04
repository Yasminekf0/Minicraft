package model.items.potions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.entity.Player;


class PotionTests {
    private HealthPotion healthPotion;
    private SpeedPotion speedPotion;
    private Player player;

    @BeforeEach
    void setUp() {
        healthPotion = new HealthPotion();
        speedPotion = new SpeedPotion();
        player = Player.getInstance();
        player.setHealth(10);
        player.setSpeed(10);
    }



//    @Test
//    void testHealthPotionDefaultHealingAmount() {
//        assertEquals(5, healthPotion.healingAmount, "Default healing amount should be 5");
//    }
//
//    @Test
//    void testHealthPotionSetHealingAmount() {
//        healthPotion.setHealingAmount(10);
//        assertEquals(10, healthPotion.getHealingAmount(), "Healing amount should be updated to 10");
//    }

    @Test
    void testHealthPotionUseHealsPlayer() {
        player.setHealth(5); // took damage
        int initialHealth = player.getHealth();
        healthPotion.setCount(1);

        //  System.out.println(initialHealth);
        healthPotion.use();
        // System.out.println(player.getHealth());
        assertEquals(initialHealth + 5, player.getHealth(), "Player's health should increase by 5 after using HealthPotion");
    }


//    @Test
//    void testSpeedPotionDefaultSpeedBoost() {
//        assertEquals(5, speedPotion.getSpeedBoost(), "Default speed boost should be 5");
//    }
//
//    @Test
//    void testSpeedPotionSetSpeedBoost() {
//        speedPotion.setSpeedBoost(7);
//        assertEquals(7, speedPotion.getSpeedBoost(), "Speed boost should be updated to 7");
//    }

    @Test
    void testSpeedPotionUseIncreasesPlayerSpeed() {
        int initialSpeed = player.getSpeed();
        speedPotion.setCount(1);
        speedPotion.use();
        assertEquals(initialSpeed + 5, player.getSpeed(), "Player's speed should increase by 5 after using SpeedPotion");
    }

}