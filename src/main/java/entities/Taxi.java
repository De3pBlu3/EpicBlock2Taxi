package entities;

import network.Location;

import static other.Util.randomRegistrationString;

/**
 * Taxi class
 */
public sealed class Taxi extends Vehicle permits ElectricTaxi, LimoTaxi, SportsTaxi {

    public Taxi(int size, String registrationNumber, int headcount, Location loc) {
        super(size, registrationNumber, headcount, loc);
    }

    public Taxi(int size, int headcount, Location loc) {
        super(size, randomRegistrationString(), headcount, loc);
    }




    @Override
    public final String toString() {
        return this.getClass().getSimpleName() + '[' + this.getAttributeValues() + ']';
    }

}
