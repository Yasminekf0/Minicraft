package model.entity;

import model.position.WorldPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {


    private Map<String, ArrayList<String>> inventory;
    private String currentSection;
    private String selectedItem;

    public Player() {
        this.worldPos = new WorldPosition(0,0);
        this.speed = 5;
        this.health = 10;
        this.maxHealth = 10;
        initializeInventory();
    }

    /**
     * Move the player by (dx, dy), scaled by speed.
     */
    public void move(double dx, double dy) {
        worldPos.increment(dx * speed, dy * speed);
    }


    public void initializeInventory() {
        inventory = new HashMap<>();
        inventory.put("Tools", new ArrayList<>());
        inventory.put("Blocks", new ArrayList<>());
        inventory.put("Potions", new ArrayList<>());
        currentSection = "Tools";
        selectedItem = null;
    }

    public void addItem(String section, String item) {
        if (!inventory.containsKey(section)) return;

        ArrayList<String> items = inventory.get(section);

        if (section.equals("Tools") && items.contains(item)) {
            System.err.println("You already have this tool");
            return;
        }

        items.add(item);
        if (selectedItem == null) selectedItem = item;
    }


    public ArrayList<String> getInventorySection(String section) {
        return inventory.get(section);
    }

    public String getItemFromInventory(String section, String item) {
        if (inventory.containsKey(section)) {
            ArrayList<String> items = inventory.get(section);

            if (items.contains(item)) {
                return item;
            }
        }

        return "Item not found";
    }

    public int countItem(String section, String item) {
        return 1;
    }

    public void openChest() { //*****************************
    }

    public void upgradeTool() {} //***************************

    public String getSelectedItem(){return selectedItem;}

    public void setSelectedItem(String item){selectedItem = item;}

    public String getCurrentSection(){return currentSection;}

    public void setCurrentSection(String section){
        if (inventory.containsKey(section)) {
            currentSection = section;
        }}
    /**
     * If you want the player to do any autonomous updates, place them here.
     */
    public void update() {
        // For autonomous updates if needed
    }
}
