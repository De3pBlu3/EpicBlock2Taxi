 package time;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimelineTest {
    private Timeline timeline;

    @Before
    public void setUp() {
        timeline = new Timeline();
        // test error
        assertThrows(IllegalArgumentException.class, () -> {
            timeline.setCurrentTick(1);
        });
        assertEquals(0, timeline.getLength());

    }

    @Test
    public void testGetCurrentTick() {
        assertNull(timeline.getCurrentTick());
        Tick tick = new Tick(0);
        timeline = new Timeline(tick);
        assertEquals(tick, timeline.getCurrentTick());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCurrentTick() {
        timeline.setCurrentTick(1);
    }

    @Test
    public void testNextTick() {
        Tick tick = new Tick(0);
        timeline = new Timeline(tick);
        assertNull(timeline.nextTick());
    }

    @Test
    public void testPreviousTick() {
        Tick tick = new Tick(0);
        timeline = new Timeline(tick);
        assertNull(timeline.previousTick());
    }

    @Test
    public void testAppendTick() {
        timeline.appendTick();
        assertNotNull(timeline.getCurrentTick());
        assertEquals(0, timeline.getCurrentTick().getTickNumber());
    }

    @Test
    public void testExtendTicks() {
        timeline.extendTicks(5);
        assertNotNull(timeline.getCurrentTick());
        assertEquals(5, timeline.getCurrentTick().getTickNumber());
    }

    @Test
    public void testGetLength() {
        assertEquals(0, timeline.getLength());
        timeline.extendTicks(5);
        assertEquals(5, timeline.getLength());
    }

    @Test
    public void testSetCurrentTickWE() {
        timeline.extendTicks(5);
        timeline.setCurrentTick(3);
        assertEquals(4, timeline.getCurrentTick().getTickNumber());
    }

    @Test
    public void testSetCurrentTickWE2() {
        timeline.extendTicks(5);
        timeline.setCurrentTick(3);
        assertEquals(4, timeline.getCurrentTick().getTickNumber());
    }
}
