package entities;

import network.Location;

/**
 * Taxi class
 */
public class Taxi extends Vehicle {

    public Taxi(int size, String registrationNumber, int headcount, Location loc) {
        super(size, registrationNumber, headcount, loc);
    }

    public Taxi(int size, int headcount, Location loc) {
        super(size, headcount, loc);
    }

    @Override
    public final String toString() {
        return this.getClass().getSimpleName() + '[' + this.getAttributeValues() + ']';
    }

}
