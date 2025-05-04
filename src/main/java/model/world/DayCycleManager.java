package model.world;

import controller.GameSettings;

import java.io.Serializable;

@SuppressWarnings("FieldCanBeLocal")
public class DayCycleManager implements Serializable {

    private static DayCycleManager instance;
    private final int delay;
    private final int cycleDuration = 300 * 1000;  // day-night cycle duration in ms
    private final int transitionDuration = 20 * 1000; // sunrise/sunset duration in ms

    private int time = 0;

    private DayCycleManager() {
        this.delay = 1000/ GameSettings.FPS;
    }

    public static DayCycleManager getInstance() {
        if (instance == null) {
            instance = new DayCycleManager();
        }
        return instance;
    }
    public static void setInstance(DayCycleManager instance) {
        DayCycleManager.instance = instance;
    }

    public void tick() {
        // Increments current time by the delay between the game loop iterations
        // When it finishes the cycle, the modulus makes it start from 0 again.
        time = (time + delay) % cycleDuration;

    }

    public double getNightFilterLevel() {

        int sunRiseStart = cycleDuration - transitionDuration;
        int sunSetStart = cycleDuration / 2 - transitionDuration;

        if (time <= sunSetStart) { // during the day
            return 0;
        } else if (time < cycleDuration / 2) { // during sunset
            // linearly increasing the night filter opacity from 0 to 1 over time
            return (double) (time - sunSetStart) / transitionDuration;
        } else if (time <= sunRiseStart) { // during night
            return 1;
        } else { // during sunrise
            // linearly decreasing the night filter opacity from 1 to 0 over time
            return (double) (cycleDuration - time) / transitionDuration;
        }
    }

    public boolean isNight() {
        return time > cycleDuration / 2;
    }

    public void setTime(int time) {
        if (time >= 0 && time < cycleDuration) {
            this.time = time;
        }
    }

    public int getTime() {
        return time;
    }

    public int getCycleDuration() {
        return cycleDuration;
    }

}
