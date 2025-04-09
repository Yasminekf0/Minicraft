package model.entity;

import model.items.Item;
import model.items.tools.Tool;
import model.position.WorldPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {


    private Map<String, ArrayList<Item>> inventory;
    private String currentSection;
    private Item selectedItem;

    public Player() {
        this.worldPos = new WorldPosition(8000,8000);
        this.speed = 15;
        this.health = 10;
        this.maxHealth = 10;
        initializeInventory();
    }

    public void move(double dx, double dy) {
        worldPos.increment(dx * speed, dy * speed);
    }


    public void initializeInventory() {
        inventory = new HashMap<>();
        inventory.put("Tools", new ArrayList<Item>());
        inventory.put("Blocks", new ArrayList<Item>());
        inventory.put("Potions", new ArrayList<Item>());
        currentSection = "Tools";
        selectedItem = null;
    }

    public void addItem(Item i) {
        String section = i.getSection();
        if (!inventory.containsKey(section)) return;

        ArrayList<Item> items = inventory.get(section);
        if (items == null) return;

        for (Item existing : items) {
            if (existing.getClass().equals(i.getClass())) {
                if (section.equals("Potions") || section.equals("Blocks")) {
                    existing.setCount(existing.getCount()+i.getCount());
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

    public void openChest() { //*****************************
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

    public Item getSelectedItem(){return selectedItem;}

    public void setSelectedItem(Item i){
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

    public String getCurrentSection(){return currentSection;}

    public void setCurrentSection(String section){
        if (inventory.containsKey(section)) {
            currentSection = section;
        }
    }
}
