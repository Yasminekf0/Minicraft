package model;

public class DayCycleManager {

    private final int delay;
    private final int cycleDuration = 10 * 2 * 1000;  // day-night cycle duration in ms
    private final int transitionDuration = 2 * 1000;  // transition period duration in ms

    private final int sunSetStart = cycleDuration / 2 - transitionDuration;
    private final int sunRiseStart = cycleDuration - transitionDuration;

    private int time = 0;

    public DayCycleManager(int delay) {
        this.delay = delay;
    }

    public void tick() {
        time = (time + delay) % cycleDuration;

        //System.out.println(getNightFilterLevel());

    }

    private double getNightFilterLevel() {
        if (time <= sunSetStart) {
            return 0;
        } else if (time < cycleDuration / 2) {
            return (double) (time - sunSetStart) / transitionDuration;
        } else if (time <= sunRiseStart) {
            return 1;
        } else {
            return (double) (cycleDuration - time) / transitionDuration;
        }
    }

}
