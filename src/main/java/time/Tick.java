package time;

public class Tick {
    private final int tickNumber;
    private Tick prevTick = null;
    private Tick nextTick = null;

    public Tick(int tick) {
        this.tickNumber = tick;
    }

    public int getTickNumber() {
        return tickNumber;
    }

    public Tick nextTick() {
        return new Tick(this.tickNumber + 1);
    }

    public Tick previousTick() {
        return new Tick(this.tickNumber - 1);
    }



    @Override
    public String toString() {
        return "Tick{" +
                "tick=" + tickNumber +
                '}';
    }
}
