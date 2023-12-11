package entities;

import network.Location;
import network.Node;

/**
 * Entity abstract class
 */
public abstract class Entity {

    private int headcount;
    private Location location;
    private Node node;

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

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getHeadcount() {
        return this.headcount;
    }

    public void setHeadcount(int headcount) {
        throwErrorIfLessThanOne(headcount, "headcount");
        this.headcount = headcount;
    }

    public abstract String toString();

    // For simplifying toString() implementations
    String getAttributeValues() {
        return "headcount=" + this.headcount + ", location=" + this.location + ", node=" + this.node;
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
