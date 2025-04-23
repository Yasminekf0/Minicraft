package model;

import view.NightFilterView;

public class DayCycleManager {

    private final NightFilterView nightFilterView;
    private final int delay;
    private final int cycleDuration = 10 * 60 * 1000;  // day-night cycle duration in ms
    private final int transitionDuration = 20 * 1000; // transition period duration in ms

    private int time = 0;

    public DayCycleManager(NightFilterView nightFilterView, int delay) {
        this.nightFilterView = nightFilterView;
        this.delay = delay;
    }

    public void tick() {
        time = (time + delay) % cycleDuration;

        nightFilterView.setLevel(getNightFilterLevel());

    }

    private double getNightFilterLevel() {

        int sunRiseStart = cycleDuration - transitionDuration;
        int sunSetStart = cycleDuration / 2 - transitionDuration;

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

    public boolean isNight() {
        return time > cycleDuration / 2;
    }

}
