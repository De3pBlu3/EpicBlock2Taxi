package entities;

import network.Location;

/**
 * Party class
 */
public final class Party extends Entity {

    public Party(int headcount, Location loc) {
        super(headcount, loc);
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
