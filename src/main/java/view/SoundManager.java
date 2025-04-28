package view;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        // TODO: add additional entries here
    }

    private SoundManager() {}

    public static synchronized SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void loadAllSounds() {
        SOUND_REGISTRY.forEach(this::loadSound);
    }

    public void loadSound(String key, String resourcePath) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath);
             BufferedInputStream bis = new BufferedInputStream(is);
             AudioInputStream ais = AudioSystem.getAudioInputStream(bis)) {

            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clips.put(key, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound '" + key + "': " + e.getMessage());
        }
    }

    public void playSound(String key) {
        Clip clip = clips.get(key);
        if (clip == null) return;
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public void playRandomSound(String... keys) {
        if (keys == null || keys.length == 0) return;
        Random rand = new Random();
        String selectedKey = keys[rand.nextInt(keys.length)];
        playSound(selectedKey);
    }

    public void loopSoundContinuously(String key) {
        Clip clip = clips.get(key);
        if (clip == null) return;
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);    }

    public void stopSound(String key) {
        Clip clip = clips.get(key);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void unloadAll() {
        for (Clip clip : clips.values()) {
            clip.close();
        }
        clips.clear();
    }
}
