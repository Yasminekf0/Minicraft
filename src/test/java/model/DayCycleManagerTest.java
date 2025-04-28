package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DayCycleManagerTest {
    DayCycleManager mgr;
    private final int CYCLE = 30_000;
    private final int TRANS = 10_000;
    private final int SUN_SET_START = CYCLE/2 - TRANS;   // 15_000 - 10_000 = 5_000
    private final int SUN_RISE_START = CYCLE - TRANS;    // 30_000 - 10_000 =20_000

    @BeforeEach
    void setUp() {
        DayCycleManager.setInstance(null);
        mgr = DayCycleManager.getInstance();
    }

    @Test
    void singletonBehavior() {
        DayCycleManager again = DayCycleManager.getInstance();
        assertSame(mgr, again);
    }

    @Test
    void defaultTimeAndCycleDuration() {
        assertEquals(0, mgr.getTime(), "time starts at 0");
        assertEquals(CYCLE, mgr.getCycleDuration());
    }

    @Test
    void setTimeValidAndInvalid() {
        mgr.setTime(1234);
        assertEquals(1234, mgr.getTime());

        mgr.setTime(-1);
        assertEquals(1234, mgr.getTime(), "negative time should be ignored");

        mgr.setTime(CYCLE);
        assertEquals(1234, mgr.getTime(), "time ≥ cycleDuration should be ignored");
    }

    @Test
    void isNightTransitionsAtHalfCycle() {
        mgr.setTime(SUN_SET_START);       // 5000 → day
        assertFalse(mgr.isNight());

        mgr.setTime(CYCLE/2);             // 15000 → exactly halfway → still day
        assertFalse(mgr.isNight());

        mgr.setTime(CYCLE/2 + 1);         // 15001 → night
        assertTrue(mgr.isNight());
    }

    @Test
    void getNightFilterLevel_dayBeforeSunset() {
        mgr.setTime(0);
        assertEquals(0.0, mgr.getNightFilterLevel(), 1e-9);

        mgr.setTime(SUN_SET_START);       // boundary
        assertEquals(0.0, mgr.getNightFilterLevel(), 1e-9);
    }

    @Test
    void getNightFilterLevel_sunsetTransition() {
        // just after sunset starts
        mgr.setTime(SUN_SET_START + 1);
        assertEquals(1.0/TRANS, mgr.getNightFilterLevel(), 1e-9);

        // mid-transition
        mgr.setTime(SUN_SET_START + TRANS/2);
        assertEquals((double)(SUN_SET_START + TRANS/2 - SUN_SET_START)/TRANS,
                mgr.getNightFilterLevel(), 1e-9);

        // just before fully dark
        mgr.setTime(CYCLE/2 - 1);
        assertEquals((double)((CYCLE/2 - 1) - SUN_SET_START)/TRANS,
                mgr.getNightFilterLevel(), 1e-9);
    }

    @Test
    void getNightFilterLevel_fullNight() {
        mgr.setTime(CYCLE/2);             // 15000
        assertEquals(1.0, mgr.getNightFilterLevel(), 1e-9);

        mgr.setTime(SUN_RISE_START);      // 20000
        assertEquals(1.0, mgr.getNightFilterLevel(), 1e-9);
    }

    @Test
    void getNightFilterLevel_sunriseTransition() {
        // just after sunrise transition begins
        mgr.setTime(SUN_RISE_START + 1);  // 20001
        assertEquals((double)(CYCLE - (SUN_RISE_START + 1))/TRANS,
                mgr.getNightFilterLevel(), 1e-9);

        // near end of cycle
        mgr.setTime(CYCLE - 1);
        assertEquals((double)(CYCLE - (CYCLE - 1))/TRANS,
                mgr.getNightFilterLevel(), 1e-9);
    }

    @Test
    void tickAdvancesTimeModuloCycle() {
        // we don't know exact delay, but we can assert wrap behavior
        mgr.setTime(CYCLE - 1);
        mgr.tick();
        int after = mgr.getTime();
        assertTrue(after < CYCLE - 1, "after wrapping, time must be less than previous");
        assertTrue(after >= 0,      "time remains non-negative");
    }
}