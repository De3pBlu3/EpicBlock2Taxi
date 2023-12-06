package entities;

import network.Location;

/**
 * Party class
 */
public class Party extends Entity {

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

}
