package view;

import model.items.Item;

import model.items.potions.HealthPotion;
import model.items.potions.SpeedPotion;
import model.items.tools.Tool;
import model.items.tools.Material;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;

import model.items.blocks.WoodItem;
import model.items.blocks.RockItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;


public class ItemLoader {
    private static final Map<Class<? extends Tool>, EnumMap<Material, BufferedImage>> TOOL_ICONS = new HashMap<>();
    private static final Map<Class<? extends Item>, BufferedImage> OTHER_ICONS  = new HashMap<>();

    public static synchronized void init() {

        // Tools with Materials
        EnumMap<Material, BufferedImage> axeMap = new EnumMap<>(Material.class);
        axeMap.put(Material.WOOD, getBufferedImage("/tools/wood_axe.png"));
        axeMap.put(Material.STONE, getBufferedImage("/tools/stone_axe.png"));
        axeMap.put(Material.IRON, getBufferedImage("/tools/iron_axe.png"));
        axeMap.put(Material.DIAMOND, getBufferedImage("/tools/diamond_axe.png"));
        TOOL_ICONS.put(Axe.class, axeMap);

        EnumMap<Material, BufferedImage> pickMap = new EnumMap<>(Material.class);
        pickMap.put(Material.WOOD, getBufferedImage("/tools/wood_pickaxe.png"));
        pickMap.put(Material.STONE, getBufferedImage("/tools/stone_pickaxe.png"));
        pickMap.put(Material.IRON, getBufferedImage("/tools/iron_pickaxe.png"));
        pickMap.put(Material.DIAMOND, getBufferedImage("/tools/diamond_pickaxe.png"));
        TOOL_ICONS.put(Pickaxe.class, pickMap);

        EnumMap<Material, BufferedImage> swordMap = new EnumMap<>(Material.class);
        swordMap.put(Material.WOOD, getBufferedImage("/tools/wood_sword.png"));
        swordMap.put(Material.STONE, getBufferedImage("/tools/stone_sword.png"));
        swordMap.put(Material.IRON, getBufferedImage("/tools/iron_sword.png"));
        swordMap.put(Material.DIAMOND, getBufferedImage("/tools/diamond_sword.png"));
        TOOL_ICONS.put(Sword.class, swordMap);

        // Blocks
        OTHER_ICONS.put(WoodItem.class, getBufferedImage("/tiles/plank.png"));
        OTHER_ICONS.put(RockItem.class, getBufferedImage("/tiles/stone_brick.png"));

        // Potions
        OTHER_ICONS.put(HealthPotion.class, getBufferedImage("/potions/health_potion.png"));
        OTHER_ICONS.put(SpeedPotion.class, getBufferedImage("/potions/speed_potion.png"));
    }

    private static BufferedImage getBufferedImage(String path){
        try {
            return ImageIO.read(Objects.requireNonNull(ItemLoader.class.getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Icons Method
    public static BufferedImage getIcon(Item item) {
        if (item instanceof Tool t) {
            var tierMap = TOOL_ICONS.get(t.getClass());
            if (tierMap != null) {
                Material mat = Material.valueOf(t.getMaterial().toUpperCase());
                return tierMap.get(mat);
            }
        }

        return OTHER_ICONS.get(item.getClass());
    }
}
