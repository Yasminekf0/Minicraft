package model.entity;

import model.position.WorldPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {
    Player player;


    @BeforeEach
    void setUp() {
        Player.setInstance(null);
        player = Player.getInstance();
    }

    @Test
    void getHealth_setHealth() {
        player.setHealth(8);
        assertEquals(8, player.getHealth());
    }

    @Test
    void setSpeed_getSpeed() {
            player.setSpeed(10);
            assertEquals(10, player.getSpeed(), "default speed should be 10");
    }

    @Test
    void getMaxHealth() {
        assertEquals(10, player.getMaxHealth(), "default max health should be 10");
    }

    @Test
    void takeDamage() {
        player.setHealth(10);
        player.takeDamage(3);
        assertEquals(7, player.getHealth(), "takeDamage should reduce health by a specified int");
    }
    @Test
    void heal() {
        player.setHealth(5);
        player.heal(3);
        assertEquals(8, player.getHealth(), "heal should increase health by heal amount");

    }

    @Test
    void getInstance() {
        Player another = Player.getInstance();
        assertSame(player, another, "getInstance should return the same singleton instance");
    }

    @Test
    void setInstance() {
    }

    @Test
    void getInventory() {
        assertNotNull(player.getInventory(), "Player should have a non-null inventory");
    }

    @Test
    void moveUntil() {
        WorldPosition initial = new WorldPosition(player.getWorldPos().getX(), player.getWorldPos().getY());
        player.moveUntil(0, 0);
        assertEquals(initial.getX(), player.getWorldPos().getX(), "moveUntil with zero dx should not change X");
        assertEquals(initial.getY(), player.getWorldPos().getY(), "moveUntil with zero dy should not change Y");
    }


    @Test
    void use_whenSelectedItemIsNotNull() {
        //  dummy item that can track if 'use()' was called
        class DummyItem extends model.items.Item {
            boolean used = false;

            DummyItem() {
                super("Tools");
            }

            @Override
            public void use() {
                used = true;
            }
        }

        DummyItem dummy = new DummyItem();
        dummy.setCount(1);

        player.getInventory().getInventorySection("Tools").add(dummy);
        player.getInventory().setSelectedItem(dummy);

        assertDoesNotThrow(() -> player.use(), "Calling use() with a selected item should not throw");


        assertTrue(dummy.used, "use() method of selected item should be called");
    }




}
