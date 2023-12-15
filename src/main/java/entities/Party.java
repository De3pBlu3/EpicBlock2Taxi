package entities;

import network.Location;

/**
 * Party class
 */
@SuppressWarnings("unused")
public final class Party extends Entity {

    public String username;
    public Location destination;
    public Taxi assignedTaxi;
    public boolean assigned = false;

    public Party(int headcount, Location loc, String username, Location destination) {
        super(headcount, loc);
        this.username = username;
        this.destination = destination;
    }

    public Party(Location loc) {
        super(1, loc);
    }

    @Override
    public String toString() {
        return "Party[" + this.getAttributeValues() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Party party) {
            return (party.getHeadcount() == getHeadcount() && party.getLocation() == this.getLocation());
        }

        return false;
    }

    public void setAssigned(boolean b) {
        this.assigned = b;
    }
}
