package entities;

import network.Edge;
import network.Location;

/**
 * Taxi class
 */
public sealed class Taxi extends Vehicle permits ElectricTaxi, LimoTaxi, SportsTaxi {

    public Taxi(int size, String registrationNumber, int headcount, Location loc) {
        super(size, registrationNumber, headcount, loc);
    }


    @Override
    public final String toString() {
        return this.getClass().getSimpleName() + '[' + this.getAttributeValues() + ']';
    }

}
