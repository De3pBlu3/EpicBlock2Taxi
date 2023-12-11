package time;

public class Timeline {
    private Tick currentTick;

    public Timeline() {
        this.currentTick = new Tick(0, this);
    }

    public Tick getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(Tick currentTick) {
        this.currentTick = currentTick;
    }

    public Tick nextTick() {
        this.currentTick = this.currentTick.nextTick();
        return this.currentTick;
    }

    public Tick previousTick() {
        this.currentTick = this.currentTick.previousTick();
        return this.currentTick;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "currentTick=" + currentTick +
                '}';
    }
}
