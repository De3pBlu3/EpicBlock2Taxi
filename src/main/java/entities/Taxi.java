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
    public void MoveVehicleOneStep(Taxi vehicle, Location loc){
        Location oldLoc = vehicle.getLoc();

        // if vehicle is already at location, do nothing
        if (oldLoc.getCurrentNetLocation().equals(loc.getCurrentNetLocation())){
            return;
        }
        // if vehicle is at edge, do not move
        if (oldLoc.getCurrentNetLocation().getClass() == Edge.class){
            return;
        }

        // is there an edge between the two locations?

        oldLoc.getCurrentNetLocation().removeOccupant(vehicle);
        loc.getCurrentNetLocation().addOccupant(vehicle);
        vehicle.setLoc(loc);
    }

    @Override
    public final String toString() {
        return this.getClass().getSimpleName() + '[' + this.getAttributeValues() + ']';
    }

}
