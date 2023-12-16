package entities;

import network.Location;

import static other.Util.randomRegistrationString;

/**
 * Taxi class
 */
public sealed class Taxi extends Vehicle permits ElectricTaxi, LimoTaxi, SportsTaxi {

    public Taxi(int size, String registrationNumber, Location loc) {
        super(size, registrationNumber, loc);
    }

    public Taxi(int size, Location loc) {
        super(size, randomRegistrationString(), loc);
    }

    @Override
    public final String toString() {
        return this.getClass().getSimpleName() + '[' + this.getAttributeValues() + ']';
    }

}
