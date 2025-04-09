package model.entity;

import model.items.Item;
import model.items.potions.HealthPotion;
import model.items.potions.Potion;
import model.items.potions.SpeedPotion;
import model.items.potions.StrengthPotion;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;
import model.items.tools.Tool;
import model.position.WorldPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {


    private Map<String, ArrayList<Item>> inventory;
    private String currentSection;
    private Item selectedItem;

    public Player() {
        this.worldPos = new WorldPosition(8000,8000);
        this.speed = 15;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        initializeInventory();
    }

    public void move(double dx, double dy) {
        worldPos.increment(dx * speed, dy * speed);
    }


    public void initializeInventory() {
        inventory = new HashMap<>();
        inventory.put("Blocks", new ArrayList<Item>());
        inventory.put("Potions", new ArrayList<Item>());
        inventory.put("Tools", new ArrayList<Item>(Arrays.asList(new Sword(), new Pickaxe(), new Axe())));
        currentSection = "Tools";
        selectedItem = (getInventorySection(currentSection)).get(0);;
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

    public void usePotion() {
        if (!(selectedItem instanceof Potion)) return;

        Potion potion = (Potion) selectedItem;

        if (potion instanceof HealthPotion) {
            int heal = ((HealthPotion) potion).getHealingAmount();
            health = Math.min(health + heal, maxHealth);
        } else if (potion instanceof SpeedPotion) {
            int boost = ((SpeedPotion) potion).getSpeedBoost();
            speed += boost; // maybe set a timer to revert speed back?
        } else if (potion instanceof StrengthPotion) {
            int boost = ((StrengthPotion) potion).getStrengthBoost();
            // to do. maybe make CurrentStrength?
        }

        potion.setCount(potion.getCount() - 1);
        if (potion.getCount() <= 0) {
            ArrayList<Item> potions = inventory.get("Potions");
            potions.removeIf(item -> item.getClass().equals(potion.getClass()));
            selectedItem = null;
        }
    }
}
