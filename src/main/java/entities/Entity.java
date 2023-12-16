package entities;

import network.Location;
import network.Node;

/**
 * Entity abstract class
 */
@SuppressWarnings("SameParameterValue")
public abstract sealed class Entity permits Vehicle, Party {

    private Location location;
    private Node node;

    public Entity(Location location) {
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

    public abstract String toString();

    public abstract boolean equals(Object o);

    // ================== METHODS FOR INNER USE ==================

    // For simplifying toString() implementations
    String getAttributeValues() {
        return "location=" + this.location + ", node=" + this.node;
    }

    void throwErrorIfLessThanOne(int arg, String argName) {
        if (arg < 1)
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + ' ' + argName + " cannot be less than 1"
            );
    }

}
