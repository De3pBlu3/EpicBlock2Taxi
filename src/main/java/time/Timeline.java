package time;

public class Timeline {
    // TODO throw exceptions
    private Tick currentTick;
    private int tickLength;
    private Tick rootTick;

    public Timeline() {
        tickLength = 0;
    }
    public Timeline(Tick currentTick) {
        this.currentTick = currentTick;
        this.rootTick = currentTick;
        tickLength = 1;
    }

    public Tick getCurrentTick() {
        return currentTick;  // FIXME: Returns null at some stage?
    }

    public void setCurrentTick(int currentTickNumber) {
        if (currentTickNumber > this.tickLength) {
            throw new IllegalArgumentException("Tick number is greater than the length of the timeline");
        }
        if (currentTickNumber == -1){
            getLastTick();

        }
        this.currentTick = this.rootTick;
        for (int i = 0; i < currentTickNumber; i++) {
            nextTick();
        }
    }

    public Tick nextTick() {
        this.currentTick = this.currentTick.nextTick();
        return this.currentTick;
    }

    public Tick previousTick() {
        this.currentTick = this.currentTick.previousTick();
        return this.currentTick;
    }

    public void appendTick() {
        if (this.currentTick == null) {
            this.currentTick = new Tick(0);
            this.rootTick = this.currentTick;
            this.tickLength++;
            return;
        }
        while (this.currentTick.nextTick() != null) {
            this.currentTick = this.currentTick.nextTick();
        }
        this.tickLength++;
        Tick newTick = new Tick(this.tickLength);
        this.currentTick = this.currentTick.nextTick(newTick);
    }

    public void extendTicks(int ticks) {
        for (int i = 0; i < ticks; i++) {
            appendTick();
        }
    }

    private Tick getLastTick() {
        Tick lastTick = this.rootTick;
        while (lastTick.nextTick() != null) {
            lastTick = lastTick.nextTick();
        }
        return lastTick;
    }

    public int getLength() {
        return this.tickLength;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Tick tick = this.rootTick; tick != null; tick = tick.nextTick()) {
            str.append(tick);
            tick.printEvents();
        }
        if (str.isEmpty()) {
            return "Timeline is empty";
        }

        return str.toString();
    }
}
