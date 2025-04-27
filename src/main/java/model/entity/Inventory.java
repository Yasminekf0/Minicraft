package model.entity;

import model.items.Item;
import model.items.blocks.RockItem;
import model.items.blocks.WoodItem;
import model.items.potions.HealthPotion;
import model.items.potions.SpeedPotion;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;

import java.util.*;

public class Inventory {
    private final Map<String, ArrayList<Item>> inventory;
    private final Map<String,Integer>          selectedIndexMap = new HashMap<>();
    private String                             currentSection;

    public Inventory() {
        inventory = new HashMap<>();
        initializeInventory();

        // seed a 0‐index for each section
        for (String sec : inventory.keySet()) {
            selectedIndexMap.put(sec, 0);
        }
        currentSection = "Tools";
    }

    private void initializeInventory() {
        inventory.put("Blocks",   new ArrayList<>(List.of(new RockItem(), new WoodItem())));
        inventory.put("Potions",  new ArrayList<>(List.of(new HealthPotion(), new SpeedPotion())));
        inventory.put("Tools",    new ArrayList<>(List.of(new Axe(), new Pickaxe(), new Sword())));
    }

    public Map<String, ArrayList<Item>> getInventory() {
        return inventory;
    }

    public ArrayList<Item> getInventorySection(String section) {
        return inventory.get(section);
    }

    /** Rotate which section is active (Tools→Blocks→Potions→Tools…) */
    public void cycleCurrentSection() {
        switch (currentSection) {
            case "Tools"  -> currentSection = "Blocks";
            case "Blocks" -> currentSection = "Potions";
            default       -> currentSection = "Tools";
        }
    }

    /** Bump the selected‐index of the *active* section only */
    public void cycleCurrentItem() {
        cycleItemInSection(currentSection);
    }

    /** Bump the selected‐index of any section by name */
    public void cycleItemInSection(String section) {
        List<Item> list = inventory.get(section);
        if (list == null || list.isEmpty()) return;
        int idx = selectedIndexMap.getOrDefault(section, 0);
        idx = (idx + 1) % list.size();
        selectedIndexMap.put(section, idx);
    }

    public Item getItemFromInventory(Item probe) {
        String sec = probe.getSection();
        List<Item> list = inventory.get(sec);
        if (list == null) return null;
        for (Item existing : list) {
            if (existing.getClass().equals(probe.getClass())) {
                return existing;
            }
        }
        return null;
    }

    public String getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(String section) {
        if (inventory.containsKey(section)) {
            currentSection = section;
        }
    }

    /** Returns the selected item for the *active* section */
    public Item getSelectedItem() {
        return getSelectedItem(currentSection);
    }

    /** Returns the selected item for the named section */
    public Item getSelectedItem(String section) {
        List<Item> list = inventory.get(section);
        int idx = selectedIndexMap.getOrDefault(section, 0);
        return (list != null && idx >= 0 && idx < list.size())
                ? list.get(idx)
                : null;
    }

    public void setSelectedItem(Item probe) {
        String sec = probe.getSection();
        List<Item> list = inventory.get(sec);
        if (list == null) return;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getClass().equals(probe.getClass())) {
                selectedIndexMap.put(sec, i);
                break;
            }
        }
    }

    public void upgradeTool() {
        if (!"Tools".equals(currentSection)) return;
        Item sel = getSelectedItem("Tools");
        if (sel instanceof model.items.tools.Tool t) {
            t.upgrade();
        }
    }

    public int countItem(Item probe) {
        Item existing = getItemFromInventory(probe);
        return existing != null ? existing.getCount() : 0;
    }

    public void addItem(Item i) {
        String section = i.getSection();
        // 1) ignore unknown sections
        List<Item> list = inventory.get(section);
        if (list == null) return;

        // 2) Tools get “upgrade if exists, else add & select”
        if ("Tools".equals(section)) {
            for (Item existing : list) {
                if (existing.getClass().equals(i.getClass())) {
                    // upgrade the existing tool
                    ((model.items.tools.Tool) existing).upgrade();
                    return;
                }
            }
            // new tool type: add it and select it
            list.add(i);
            selectedIndexMap.put(section, list.size() - 1);
            return;
        }

        // 3) Blocks & Potions stack counts if exists
        for (Item existing : list) {
            if (existing.getClass().equals(i.getClass())) {
                existing.setCount(existing.getCount() + 1);
                return;
            }
        }
        // 4) brand-new block/potion: add it and select it
        list.add(i);
        selectedIndexMap.put(section, list.size() - 1);
    }


    public void openChest() {
        // TODO
    }
}
