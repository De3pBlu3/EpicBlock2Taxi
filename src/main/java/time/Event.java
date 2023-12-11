package time;

public class Event {
    private final Tick tick;

    public Event(Tick tick) {
        this.tick = tick;
    }

    public Tick getTick() {
        return tick;
    }

}
