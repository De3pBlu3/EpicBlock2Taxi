package time;

import lists.DynamicArray;

public class Tick {
    private final int tickNumber;
    private Tick prevTick = null;
    private Tick nextTick = null;

    private DynamicArray<Event> events = new DynamicArray<Event>();

    public Tick(int tick) {
        this.tickNumber = tick;
    }

    public void addEvent(Event event) {
        events.append(event);
    }



    public int getTickNumber() {
        return tickNumber;
    }

    public Tick nextTick() {
        if (this.nextTick == null) {
            this.nextTick = new Tick(this.tickNumber + 1);
            this.nextTick.prevTick = this;
        }
            return this.nextTick;
    }

    public Tick previousTick() {
        if (this.prevTick == null) {
            this.prevTick = new Tick(this.tickNumber - 1);
            this.prevTick.nextTick = this;
        }
        return this.prevTick;
    }



    @Override
    public String toString() {
        return "Tick{" +
                "tick=" + tickNumber +
                '}';
    }
}
