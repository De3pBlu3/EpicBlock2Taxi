package entities;

import network.Location;
import network.NetworkComponent;
import network.Node;

/**
 * Entity abstract class
 */
@SuppressWarnings("SameParameterValue")
public abstract sealed class Entity permits Vehicle, Party {

    private int headcount;
    private Location location;

    public Entity(int headcount, Location location) {
        this.setHeadcount(headcount);
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public NetworkComponent getNetwork() {
        return this.location.getCurrentNetLocation();
    }

    public int getHeadcount() {
        return this.headcount;
    }

    public void setHeadcount(int headcount) {
        throwErrorIfLessThanOne(headcount, "headcount");
        this.headcount = headcount;
    }

    public abstract String toString();

    public abstract boolean equals(Object o);

    // For simplifying toString() implementations
    String getAttributeValues() {
        return "headcount=" + this.headcount + ", location=" + this.location;
    }

    void throwErrorIfLessThanOne(int arg, String argName) {
        if (arg < 1)
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName()
                    + ' ' + argName + " cannot be less than 1"
            );
    }

    void throwErrorIfLessThan(int arg1, String arg1Name, int arg2, String arg2Name) {
        if (arg1 < arg2)
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName()
                    + ' ' + arg1Name + " (" + arg1
                    + ") cannot be less than "
                    + arg2Name + " (" + arg2 + ')'
            );
    }

}
