package model;
import model.items.Item;
import model.items.tools.Sword;
import model.items.tools.Pickaxe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }


    @Test
    void inventoryInitializesCorrectly() {
        assertNotNull(inventory.getInventorySection("Tools"), "Tools section should exist");
        assertNotNull(inventory.getInventorySection("Blocks"), "Blocks section should exist");
        assertNotNull(inventory.getInventorySection("Potions"), "Potions section should exist");
        assertEquals("Tools", inventory.getCurrentSection(), "Initial section should be Tools");
    }
    @Test
    void cycleCurrentSectionWorks() {
        assertEquals("Tools", inventory.getCurrentSection());

        inventory.cycleCurrentSection();
        assertEquals("Blocks", inventory.getCurrentSection());

        inventory.cycleCurrentSection();
        assertEquals("Potions", inventory.getCurrentSection());

        inventory.cycleCurrentSection();
        assertEquals("Tools", inventory.getCurrentSection());
    }

    @Test
    void cycleCurrentItemWorks() {
        Item firstItem = inventory.getSelectedItem();
        inventory.cycleCurrentItem();
        Item secondItem = inventory.getSelectedItem();

        assertNotSame(firstItem, secondItem, "After cycling, selected item should change");
    }

    @Test
    void getSelectedItemAndSetSelectedItem() {
        Sword sword = new Sword();
        inventory.setSelectedItem(sword);
        assertTrue(inventory.getSelectedItem() instanceof Sword, "Selected item should be Sword");

        Pickaxe pickaxe = new Pickaxe();
        inventory.setSelectedItem(pickaxe);
        assertTrue(inventory.getSelectedItem() instanceof Pickaxe, "Selected item should be Pickaxe");
    }

    @Test
    void addItemIncreasesCount() {
        // Initial RockItem exists in "Blocks" section
        Item existingRock = inventory.getInventorySection("Blocks").get(0);
        assertEquals(0, existingRock.getCount());

        inventory.addItem(existingRock);

        assertEquals(1, existingRock.getCount(), "Adding an existing item should increase its count");
    }

    @Test
    void addItemNewItem() {
        // Create a new HealthPotion (assuming it's empty initially)
        class DummyPotion extends Item {
            DummyPotion() {
                super("Potions");
            }

            @Override
            public void use() {}
        }

        DummyPotion potion = new DummyPotion();
        inventory.addItem(potion);

        assertEquals(1, inventory.countItem(potion), "Adding a new item should set its count to 1");
    }

    @Test
    void upgradeToolUpgradesMaterial() {
        Sword sword = new Sword();
        inventory.setSelectedItem(sword);

        assertEquals("WOOD", sword.getMaterial());


        inventory.setSelectedItem(sword);
        inventory.upgradeTool();

        assertEquals("STONE", sword.getMaterial(), "Tool should upgrade from WOOD to STONE");
    }


    @Test
    void setCurrentSectionValidSection() {
        inventory.setCurrentSection("Blocks");
        assertEquals("Blocks", inventory.getCurrentSection(), "Should switch to Blocks section");

        inventory.setCurrentSection("Potions");
        assertEquals("Potions", inventory.getCurrentSection(), "Should switch to Potions section");
    }

    @Test
    void setCurrentSectionInvalidSection() {
        String before = inventory.getCurrentSection();

        inventory.setCurrentSection("NonExistentSection");

        assertEquals(before, inventory.getCurrentSection(), "Section should not change if section name does not exist");
    }
    // branch coverage in the case of empty lists or null objects

    @Test
    void cycleCurrentItemOnEmptySection() {
        inventory.setCurrentSection("Potions"); // Potions is empty at start
        assertDoesNotThrow(() -> inventory.cycleCurrentItem(), "Cycling item in empty section should not crash");
        assertNull(inventory.getSelectedItem(), "Selected item should remain null in empty section");
    }
    @Test
    void getItemFromInventoryReturnsNullIfNotFound() {
        class DummyItem extends Item {
            DummyItem() { super("Potions"); }
            @Override
            public void use() {}
        }

        DummyItem dummy = new DummyItem();
        assertNull(inventory.getItemFromInventory(dummy), "Should return null if item not found in inventory");
    }
    @Test
    void upgradeToolDoesNothingOutsideToolsSection() {
        inventory.setCurrentSection("Blocks"); // not Tools
        assertDoesNotThrow(() -> inventory.upgradeTool(), "Upgrade tool in wrong section should not crash");
    }
    @Test
    void countItemReturnsZeroIfNotFound() {
        class DummyItem extends Item {
            DummyItem() { super("Potions"); }
            @Override
            public void use() {}
        }

        DummyItem dummy = new DummyItem();
        assertEquals(0, inventory.countItem(dummy), "Count should be zero if item not in inventory");
    }

    @Test
    void addExistingToolUpgradesIt() {
        Sword sword = new Sword();
        sword.setCount(1);

        inventory.setSelectedItem(sword);
        String materialBefore = ((Sword) inventory.getSelectedItem()).getMaterial();

        inventory.addItem(new Sword()); // Add another sword → should cause upgrade

        String materialAfter = ((Sword) inventory.getSelectedItem()).getMaterial();

        assertNotEquals(materialBefore, materialAfter, "Adding a duplicate tool should upgrade material");
    }
    @Test
    void addItemWithInvalidSectionDoesNothing() {
        class FakeItem extends Item {
            FakeItem() {
                super("FakeSection"); // section not initialized
            }
            @Override
            public void use() {}
        }

        FakeItem fake = new FakeItem();
        assertDoesNotThrow(() -> inventory.addItem(fake), "Adding item to invalid section should not crash");
    }

    @Test
    void addExistingBlockIncreasesCount() {
        Item rock = inventory.getInventorySection("Blocks").get(0);
        assertEquals(0, rock.getCount(), "Initial rock count should be 0");

        inventory.addItem(rock);
        assertEquals(1, rock.getCount(), "Rock count should increase to 1");

        inventory.addItem(rock);
        assertEquals(2, rock.getCount(), "Rock count should increase to 2 after adding again");
    }

    @Test
    void addNewItemAddsWithCountOne() {
        class DummyPotion extends Item {
            DummyPotion() { super("Potions"); }
            @Override
            public void use() {}
        }

        DummyPotion potion = new DummyPotion();
        inventory.addItem(potion);

        Item found = inventory.getItemFromInventory(potion);
        assertNotNull(found, "New potion should be added to inventory");
        assertEquals(1, found.getCount(), "Newly added item should have count of 1");
    }


}
