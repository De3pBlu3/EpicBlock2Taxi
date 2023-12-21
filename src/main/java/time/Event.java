package time;


@SuppressWarnings("unused")
abstract public class Event {
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
