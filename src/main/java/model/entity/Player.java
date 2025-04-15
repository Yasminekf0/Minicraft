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
import model.world.Block;
import model.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static model.world.WorldSettings.worldSize;
import static view.ScreenSettings.tileSize;

public class Player extends Entity {


    private Map<String, ArrayList<Item>> inventory;
    private String currentSection;
    private Item selectedItem;

    public Player() {
        this.worldPos = new WorldPosition((worldSize*tileSize) /2.0,(worldSize*tileSize) /2.0);
        this.speed = 10;
        //maybe max speed?
        this.health = 10;
        this.maxHealth = 10;
        initializeInventory();
    }

    public void moveUntil(double dx, double dy, World world) {
        worldPos.updateDirection(dx,dy);
        for (int x = 0; x<speed; x++) {
            if (!(world.isWalkable(worldPos.getTileXPos(),worldPos.getNextYTilePos(dy)))) dy = 0;
            if (!(world.isWalkable(worldPos.getNextXTilePos(dx),worldPos.getTileYPos()))) dx = 0;
            worldPos.increment(dx, dy);
        }
    }



    private int tempBlockDurability;
    public void startBreakingBlock(World world) {
        Block block = world.getBlock(worldPos.getFocusedTileX(), worldPos.getFocusedTileY());
        tempBlockDurability = block.getBlockDurabilty();
    }

    public void keepBreakingBlock(World world){
        if (tempBlockDurability <= 0) world.breakBlock(worldPos.getFocusedTileX(), worldPos.getFocusedTileY());
        else tempBlockDurability -= 10;
    }



    public void initializeInventory() {
        inventory = new HashMap<>();
        inventory.put("Blocks", new ArrayList<>());
        inventory.put("Potions", new ArrayList<>());
        inventory.put("Tools", new ArrayList<>(Arrays.asList(new Sword(), new Pickaxe(), new Axe())));
        currentSection = "Tools";
        selectedItem = (getInventorySection(currentSection)).getFirst();
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
        if (!(selectedItem instanceof Potion potion)) return;

        switch (potion) {
            case HealthPotion healthPotion -> {
                int heal = healthPotion.getHealingAmount();
                health = Math.min(health + heal, maxHealth);
            }
            case SpeedPotion speedPotion -> {
                int boost = speedPotion.getSpeedBoost();
                speed += boost; // maybe set a timer to revert speed back?

            }
            case StrengthPotion strengthPotion -> {
                int boost = strengthPotion.getStrengthBoost();
                // to do. maybe make CurrentStrength?
            }
            default -> {
            }
        }

        potion.setCount(potion.getCount() - 1);
        if (potion.getCount() <= 0) {
            ArrayList<Item> potions = inventory.get("Potions");
            potions.removeIf(item -> item.getClass().equals(potion.getClass()));
            selectedItem = null;
        }
    }
}
