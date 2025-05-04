package view.audio;

import model.items.Item;
import model.items.blocks.RockItem;
import model.items.blocks.WoodItem;
import model.items.potions.HealthPotion;
import model.items.potions.SpeedPotion;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;

    private final Map<String, Clip> clips = new HashMap<>();

    private static final Map<String, String> SOUND_REGISTRY = new HashMap<>();
    static {
        SOUND_REGISTRY.put("villager1", "/sounds/villager1.wav");
        SOUND_REGISTRY.put("villager2", "/sounds/villager2.wav");
        SOUND_REGISTRY.put("villager3", "/sounds/villager3.wav");
        SOUND_REGISTRY.put("background", "/sounds/background.wav");
        SOUND_REGISTRY.put("footstep", "/sounds/footstep.wav");
        SOUND_REGISTRY.put("button", "/sounds/button.wav");
        SOUND_REGISTRY.put("wood", "/sounds/wood.wav");
        SOUND_REGISTRY.put("stone", "/sounds/stone.wav");
        SOUND_REGISTRY.put("pickup", "/sounds/chest.wav");
        SOUND_REGISTRY.put("dead", "/sounds/dead.wav");
        SOUND_REGISTRY.put("potion", "/sounds/potion.wav");
        SOUND_REGISTRY.put("damage", "/sounds/damage.wav");
        SOUND_REGISTRY.put("bomboclat", "/sounds/bomboclat.wav");
        SOUND_REGISTRY.put("zombie", "/sounds/zombie.wav");
        SOUND_REGISTRY.put("zombieDamage", "/sounds/zombieDamage.wav");
        SOUND_REGISTRY.put("zombieDeath", "/sounds/zombieDeath.wav");
        SOUND_REGISTRY.put("skeleton", "/sounds/skeleton.wav");
        SOUND_REGISTRY.put("skeletonDamage", "/sounds/skeletonDamage.wav");
        SOUND_REGISTRY.put("skeletonDeath", "/sounds/skeletonDeath.wav");
    }

    private SoundManager() {}

    public static synchronized SoundManager getInstance() {
        if (instance == null) {instance = new SoundManager();}
        return instance;
    }

    public void loadAllSounds() {
        SOUND_REGISTRY.forEach(this::loadSound);
    }

    public void loadSound(String key, String resourcePath) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            assert is != null;
            try (BufferedInputStream bis = new BufferedInputStream(is);
                 AudioInputStream ais = AudioSystem.getAudioInputStream(bis)) {

                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clips.put(key, clip);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound '" + key + "': " + e.getMessage());
        }
    }

    public void playUseSound(Item selected){

        switch (selected) {
            case RockItem _ -> playSound("stone");
            case WoodItem _ -> playSound("wood");
            case HealthPotion _, SpeedPotion _  -> playSound("potion");
            default -> playSound("generic_use");
        }
    }

    public void playSound(String key) {
        Clip clip = clips.get(key);
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void loopSoundContinuously(String key) {
        Clip clip = clips.get(key);
        if (clip == null) return;
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);    }

}
