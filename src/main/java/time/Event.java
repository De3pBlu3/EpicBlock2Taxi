package time;

public class Event {
    private final Tick parentTick;

    public Event(Tick tick) {
        this.parentTick = tick;
    }

    public Tick getParentTick() {
        return parentTick;
    }


    public void execute() {
    }

}
