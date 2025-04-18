package model;
import model.entity.Player;
import model.items.Item;
import model.items.potions.Potion;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;
import model.items.tools.Tool;

import java.util.*;

public class Inventory {
    private final Map<String, ArrayList<Item>> inventory;
    private Item selectedItem;
    private String currentSection;



    public Inventory() {
        inventory = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        inventory.put("Blocks", new ArrayList<>());
        inventory.put("Potions", new ArrayList<>());
        inventory.put("Tools", new ArrayList<>(Arrays.asList(
                new Sword(),
                new Pickaxe(),
                new Axe())));

        currentSection = "Tools";
        selectedItem = getInventorySection(currentSection).getFirst();
    }

    public void addItem(Item i) {
        String section = i.getSection();
        if (!inventory.containsKey(section)) return;

        ArrayList<Item> items = inventory.get(section);
        if (items == null) return;

        for (Item existing : items) {
            if (existing.getClass().equals(i.getClass())) {
                if (section.equals("Potions") || section.equals("Blocks")) {
                    existing.setCount(existing.getCount() + i.getCount());
                }
                return;
            }
        }

        items.add(i);
        if (selectedItem == null) selectedItem = i;
    }

    public ArrayList<Item> getInventorySection(String section) {
        return inventory.get(section);
    }

    public Item getItemFromInventory(Item i) {
        String section = i.getSection();
        if (!inventory.containsKey(section)) return null;

        ArrayList<Item> items = inventory.get(section);
        if (items == null) return null;

        for (Item existing : items) {
            if (existing.getClass().equals(i.getClass())) {
                return existing;
            }
        }

        return null;
    }

    public int countItem(Item i) {
        return i.getCount();
    }

    public void upgradeTool(Item i) {
        String section = i.getSection();
        if (!inventory.containsKey(section)) return;

        ArrayList<Item> items = inventory.get(section);
        if (items == null) return;

        for (Item existing : items) {
            if (existing instanceof Tool && i instanceof Tool) {
                ((Tool) existing).upgrade();
            }
        }
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item i) {
        String section = i.getSection();
        if (!inventory.containsKey(section)) return;

        ArrayList<Item> items = inventory.get(section);
        if (items == null) return;

        for (Item existing : items) {
            if (existing.getClass().equals(i.getClass())) {
                selectedItem = existing;
                currentSection = section;
                return;
            }
        }
    }

    public String getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(String section) {
        if (inventory.containsKey(section)) {
            currentSection = section;
        }
    }


    public void useSelectedPotion(Player player) {
        if (!(selectedItem instanceof Potion potion)) return;

        potion.applyEffect(player); // ← potion modifies the player

        potion.setCount(potion.getCount() - 1);
        if (potion.getCount() <= 0) {
            inventory.get("Potions").removeIf(item -> item.getClass().equals(potion.getClass()));
            selectedItem = null;
        }
    }

    public void openChest() {
        // To be implemented
    }
}

