// Make explicit that this class is abstract and cannot be instantiated directly

package time;

public class Event {
    private final Tick parentTick;

    public Event(Tick tick) {
        this.parentTick = tick;
        tick.addEvent(this);
    }

    public Tick getParentTick() {
        return parentTick;
    }


    public void execute() {
    }

}
