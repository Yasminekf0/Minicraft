package view;

import model.items.Item;
import model.items.tools.Axe;
import model.items.tools.Pickaxe;
import model.items.tools.Sword;
import model.items.blocks.WoodItem;
import model.items.blocks.RockItem;
import model.items.potions.HealthPotion;
import model.items.potions.SpeedPotion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ItemLoader {
    private static final Map<Class<? extends Item>, BufferedImage> ICONS = new HashMap<>();
    private static boolean initialized = false;

    public static synchronized void init() {
        if (initialized) return;
        ICONS.put(Axe.class, getBufferedImage("/items/wooden_axe.png"));
        ICONS.put(Pickaxe.class, getBufferedImage("/items/wooden_pickaxe.png"));
        ICONS.put(Sword.class, getBufferedImage("/items/wooden_sword.png"));
        ICONS.put(WoodItem.class, getBufferedImage("/tiles/plank.png"));
        ICONS.put(RockItem.class, getBufferedImage("/tiles/rock.png"));
        //ICONS.put(HealthPotion.class, load("/items/health_potion.png"));
        //ICONS.put(SpeedPotion.class, load("/items/speed_potion.png"));
        initialized = true;
    }

    private static BufferedImage getBufferedImage(String path){
        try {
            return ImageIO.read(Objects.requireNonNull(ItemLoader.class.getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage getIcon(Item item) {
        return ICONS.get(item.getClass());
    }
}
