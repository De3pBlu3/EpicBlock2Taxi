package entities;

import network.Location;

/**
 * Party class
 */
public class Party extends Entity {

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

}
