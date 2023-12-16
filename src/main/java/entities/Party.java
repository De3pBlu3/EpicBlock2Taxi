package entities;

import network.Location;

/**
 * Party class
 */
@SuppressWarnings("unused")
public final class Party extends Entity {

    private String username;
    private int count;
    private Location destination;
    private Taxi assignedTaxi;
    private boolean assigned = false;

    public Party(int count, Location loc, String username, Location destination) {
        super(loc);
        this.username = username;
        this.count = count;
        this.destination = destination;
    }

    public Party(String username, Location loc, Location destination) {
        super(loc);
        this.username = username;
        this.destination = destination;
    }

    public Party(Location loc) {
        super(loc);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        throwErrorIfLessThanOne(count, "count");
        this.count = count;
    }

    public Location getDestination() {
        return this.destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public boolean isAssigned() {
        return this.assigned;
    }

    public void setAssigned(boolean value) {
        this.assigned = value;
    }

    @Override
    public String toString() {
        return "Party[" + this.getAttributeValues() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Party party) {
            return (party.getLocation() == this.getLocation());
        }

        return false;
    }
}
