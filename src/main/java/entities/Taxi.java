package entities;

import not_implemented_yet.Location;

/**
 * Taxi class
 */
public class Taxi extends Entity {

    private int capacity;
    private String registrationString;

    public Taxi(int capacity, int headcount, Location loc) {
        super(headcount, loc);
        this.setCapacity(capacity);
    }

    public Taxi(int capacity, String registrationString, int headcount, Location loc) {
        super(headcount, loc);
        this.setCapacity(capacity);
        this.registrationString = registrationString;
    }
    public int getCapacity() {
        return this.capacity;
    }

    public String getRegistrationString() {
        return registrationString;
    }

    public void setRegistrationString(String reg) {
        this.registrationString = reg;
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

}
