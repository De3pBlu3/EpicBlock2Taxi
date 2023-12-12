package entities;

import network.Location;

/**
 * Party class
 */
@SuppressWarnings("unused")
public final class Party extends Entity {

    public String username;

    public Party(int headcount, Location loc, String username) {
        super(headcount, loc);
        this.username = username;
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

}
