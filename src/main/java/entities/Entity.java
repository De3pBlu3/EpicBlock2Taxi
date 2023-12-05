package entities;

import not_implemented_yet.Location;

/**
 * Entity abstract class
 */
public abstract class Entity {

    private int headcount;
    private Location loc;

    public Entity(int headcount, Location loc) {
        this.setHeadcount(headcount);
        this.loc = loc;
    }

    public Location getLoc() {
        return this.loc;
    }

    public void setLoc(Location loc) {
        // Some logic maybe
        this.loc = loc;
    }

    public int getHeadcount() {
        return this.headcount;
    }

    protected void setHeadcount(int headcount) {
        if (headcount < 1)
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() // So it works for all inheriting classes
                    + " headcount cannot be less than 1"
            );

        this.headcount = headcount;
    }

    public abstract String toString();

    // For simplifying toString() implementations
    protected String getAttributeValues() {
        return "headcount=" + this.headcount + ", node=" + this.loc;
    }

}
