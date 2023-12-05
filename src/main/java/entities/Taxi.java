package entities;

import lists.DynamicArrayable;
import not_implemented_yet.Location;

/**
 * Taxi class
 */
public class Taxi extends Entity implements DynamicArrayable<Taxi> {

    private int capacity;
    private String registrationString;

    public Taxi(int capacity, int headcount, Location loc) {
        super(headcount, loc);
        this.setCapacity(capacity);
    }

    public Taxi(int capacity, Location loc) {
        super(1, loc);
        this.setCapacity(capacity);
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String getRegistrationString() {
        return registrationString;
    }

    public void setCapacity(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException(
                    "Taxi capacity (" + capacity + ") "
                    + "can not be less than 1"
            );

        int currentHeadcount;
        if (capacity < (currentHeadcount = this.getHeadcount()))
            throw new IllegalArgumentException(
                    "Taxi capacity (" + capacity + ") "
                            + "can not be less than headcount ("
                            + currentHeadcount + ")"
            );

        this.capacity = capacity;
    }

    public void goTo(Location loc) {
        // Goes to that node
        // To be implemented
    }

    public void goTo(Party party) {
        // Goes to party.getNode() node
        // To be implemented
    }

    @Override
    public String toString() {
        return "Taxi[capacity=" + this.capacity + ", " + this.getAttributeValues() + "]";
    }


    @Override
    public Taxi[] newArray(int length) {
        return new Taxi[length];
    }
}
