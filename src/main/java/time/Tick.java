package time;

import data_structures.lists.DynamicArray;

public class Tick {
    private final int tickNumber;
    private Tick prevTick = null;
    private Tick nextTick = null;

    private final DynamicArray<Event> events = new DynamicArray<>();

    public Tick(int tick) {
        this.tickNumber = tick;
    }

    public void addEvent(Event event) {
        events.append(event);
    }

    public void printEvents() {
        System.out.println(events);
    }

    public int getTickNumber() {
        return tickNumber;
    }

    public Tick nextTick() {
        return this.nextTick;
    }
    public Tick nextTick(Tick nextTickAdd) {
        if (this.nextTick == null) {
            this.nextTick = nextTickAdd;
            nextTickAdd.prevTick = this;
        }
        return this.nextTick;
    }


    // TODO same as nextTick but for previous
    public Tick previousTick() {
        return this.prevTick;
    }


    public void executeEvents() {
        if (events.isEmpty()) {
            return;
        }
        for (Event event : events) {
            event.execute();
        }
    }


    @Override
    public String toString() {
        return "Tick[" + tickNumber + ']';
    }
}
