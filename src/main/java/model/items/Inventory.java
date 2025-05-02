package model.items;

import model.items.blocks.RockItem;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable {
    private final Map<String, ArrayList<Item>> inventory;
    private final Map<String,Integer> selectedIndexMap = new HashMap<>();
    private String currentSection;

    public Inventory() {
        inventory = new HashMap<>();
        initializeInventory();

        for (String sec : inventory.keySet()) {
            selectedIndexMap.put(sec, 0);
        }
        currentSection = "Tools";
    }

    private void initializeInventory() {
        inventory.put("Blocks", new ArrayList<>(List.of(new RockItem())));
        inventory.put("Potions", new ArrayList<>());
        inventory.put("Tools", new ArrayList<>(List.of(new Sword(), new Pickaxe(), new Axe())));
    }

    public ArrayList<Item> getInventorySection(String section) {
        return inventory.get(section);
    }

    public void cycleCurrentSection() {
        switch (currentSection) {
            case "Tools" -> currentSection = "Blocks";
            case "Blocks" -> currentSection = "Potions";
            default -> currentSection = "Tools";
        }
    }

    public void cycleCurrentItem() {
        List<Item> list = inventory.get(currentSection);
        if (list == null || list.isEmpty()) return;
        int idx = selectedIndexMap.getOrDefault(currentSection, 0);
        idx = (idx + 1) % list.size();
        selectedIndexMap.put(currentSection, idx);
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

    public Item getSelectedItem() {
        return getSelectedItem(currentSection);
    }

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

        List<Item> list = inventory.get(section);
        if (list == null) return;

        if ("Tools".equals(section)) {
            for (Item existing : list) {
                if (existing.getClass().equals(i.getClass())) {
                    ((model.items.tools.Tool) existing).upgrade();
                    return;
                }
            }
        }

        for (Item existing : list) {
            if (existing.getClass().equals(i.getClass())) {
                existing.setCount(existing.getCount() + 1);
                return;
            }
        }

        i.setCount(1);
        list.add(i);
        selectedIndexMap.put(section, list.size() - 1);
    }

    public void openChest() {
    }
}
