package entities;

import lists.DynamicArrayable;
import not_implemented_yet.Location;

/**
 * Party class
 */
public class Party extends Entity implements DynamicArrayable<Party> {

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

    public Party[] newArray(int length) {
        return new Party[length];
    }
}
