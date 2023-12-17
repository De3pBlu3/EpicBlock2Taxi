package entities;

import network.Edge;
import network.Location;

import static other.Util.randomRegistrationString;

@SuppressWarnings("unused")
abstract public sealed class Vehicle extends Entity permits Taxi {

    private int size;
    private int capacity;
    private final String registrationNumber;
    private Party occupyingParty;

    public Vehicle(int size, Location loc) {
        super(loc);
        this.setSize(size);
        this.registrationNumber = randomRegistrationString();
    }

    public Vehicle(int size, String registrationNumber, Location loc) {
        super(loc);
        this.setSize(size);
        this.registrationNumber = registrationNumber;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        throwErrorIfLessThanOne(size, "size");

        switch (size) {
            case 1 -> this.capacity = 5;
            case 2 -> this.capacity = 8;
            case 3 -> this.capacity = 20;
            default -> throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " size must be either 1, 2, or 3"
            );
        }

        this.size = size;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Party getParty() {
        return this.occupyingParty;
    }

    public void setParty(Party party) {
        this.occupyingParty = party;
    }

    public Location getDestination() {
        if (this.occupyingParty == null) {
            return null;
        }

        return this.occupyingParty.getDestination();
    }

    public Boolean isOccupied() {
        return this.occupyingParty != null;
    }

    public int getCount() {
        if (!this.isOccupied())
            return 1;

        return this.occupyingParty.getCount() + 1;
    }

    public void MoveVehicleOneStep(Location loc){
        Location oldLoc = this.getLocation();

        // if vehicle is already at location, do nothing
        if (oldLoc.getCurrentNetLocation().equals(loc.getCurrentNetLocation())){
            return;
        }
        // if vehicle is at edge, do not move
        if (oldLoc.getCurrentNetLocation().getClass() == Edge.class){
            return;
        }

        // is there an edge between the two locations?

        oldLoc.getCurrentNetLocation().removeOccupant(this);
        loc.getCurrentNetLocation().addOccupant(this);
        this.setLocation(loc);
    }

    public String asTableRow() {
        StringBuilder row = new StringBuilder();

        row.append(String.format("%-15s", this.registrationNumber));
        row.append(String.format("%-35s", this.getLocation()));
        row.append(String.format("%-15d", this.getCount()));
        row.append(String.format("%-15d", this.capacity));
        row.append(String.format("%-15b", this.isOccupied()));

        if (this.isOccupied()) {
            row.append(String.format("%-15s", this.occupyingParty.getUsername()));
            row.append(String.format("%-15s", this.getDestination()));
        } else {
            row.append(String.format("%-15s", "N/A"));
            row.append(String.format("%-15s", "N/A"));
        }

        return row.toString();
    }

//    @Override
//    String getAttributeValues() {
//        return "size=" + this.size
//                + ", capacity=" + this.capacity
//                + ", registrationNumber=" + this.registrationNumber
//                + ", occupyingParty=" + this.occupyingParty
//                + ", " + super.getAttributeValues();
//    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "['" + this.registrationNumber + "']";
    }

    // Two vehicles will be considered equal if their registration numbers are the same
    @Override
    public final boolean equals(Object o) {
        if (o instanceof Vehicle vehicle)
            return vehicle.getRegistrationNumber().equals(this.registrationNumber);

        return false;
    }

}
