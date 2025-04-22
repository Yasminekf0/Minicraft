package model.entity;
import model.items.Item;
import model.items.blocks.BlockItem;
import model.items.blocks.RockItem;
import model.items.blocks.WoodItem;
import model.items.potions.HealthPotion;
import model.items.potions.Potion;
import model.items.potions.SpeedPotion;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;
import model.items.tools.Tool;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable {
    private final Map<String, ArrayList<Item>> inventory;
    private Item selectedItem;
    private int selectedIndex;
    private String currentSection;

    public Inventory() {
        inventory = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        inventory.put("Blocks", new ArrayList<>(Arrays.asList(
                new RockItem(),
                new WoodItem())));
        inventory.put("Potions", new ArrayList<>(Arrays.asList(
                new HealthPotion(),
                new SpeedPotion())));
        inventory.put("Tools", new ArrayList<>(Arrays.asList(
                new Axe(),
                new Pickaxe(),
                new Sword())));

        currentSection = "Tools";
        selectedItem = getInventorySection(currentSection).getFirst();
    }

    public void addItem(Item i) {
        String section = i.getSection();
        if (!inventory.containsKey(section)) return;

        ArrayList<Item> items = inventory.get(section);
        if (items == null) return;
        if (section.equals("Tool")){
            upgradeTool(i);
            return;
        }

        for (Item existing : items) {
            if (existing.getClass().equals(i.getClass())) {
                if (section.equals("Potions") || section.equals("Blocks")) {
                    existing.setCount(existing.getCount() + 1);
                }
                return;
            }
        }
        if (selectedItem == null) cycleCurrentItem();
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
            selectedIndex = -1;
            cycleCurrentItem();
        }
    }

    public void cycleCurrentSection(){
        switch (currentSection) {
            case "Tools" -> setCurrentSection("Blocks");
            case "Blocks" -> setCurrentSection("Potions");
            case "Potions" -> setCurrentSection("Tools");
            default -> setCurrentSection("Tools");
        };
    }

    public void cycleCurrentItem(){
        ArrayList<Item> currentArray = inventory.get(currentSection);
        selectedIndex += 1;
        if (selectedIndex >= currentArray.size()){
            selectedIndex = 0;
        }
        selectedItem = currentArray.get(selectedIndex);
        if (selectedItem.getCount() == 0 && currentArray.stream().anyMatch(i -> i.getCount() > 0)){
            cycleCurrentItem();
        } else if (selectedItem.getCount() != 0) {
            return;

        } else selectedItem = null;
    }

    public void openChest() {
        // To be implemented
    }

    public Map<String, ArrayList<Item>> getInventory() {
        return inventory;
    }
}

