package entities;

import network.Location;
import network.NetworkComponent;
import network.Node;

/**
 * Entity abstract class.
 * <p>
 * Used to represent an entity that can be placed on the map.
 */
public abstract sealed class Entity permits Vehicle, Party {

    private Location location;

    public Entity(Location location) {
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

    public abstract String toString();

    public abstract boolean equals(Object o);

    // For simplifying toString() implementations
    String getAttributeValues() {
        return "headcount=" + this.headcount + ", location=" + this.location;
    }
    // ================== METHODS FOR INNER USE ==================

    void throwErrorIfLessThanOne(int arg, String argName) {
        if (arg < 1)
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + ' ' + argName + " cannot be less than 1"
            );
    }

}
