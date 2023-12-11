package entities;

import network.Location;

import java.util.Objects;

import static other.Util.randomRegistrationString;

abstract public class Vehicle extends Entity {

    private int capacity;
    private int size;
    private final String registrationNumber;

    public Vehicle(int size, int headcount, Location loc) {
        super(headcount, loc);
        this.setSize(size);
        this.registrationNumber = randomRegistrationString();
    }

    public Vehicle(int size, String registrationNumber, int headcount, Location loc) {
        super(headcount, loc);
        this.setSize(size);
        this.registrationNumber = registrationNumber;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        throwErrorIfLessThanOne(size, "size");

        switch (size) {
            case 1 -> this.setCapacity(5);
            case 2 -> this.setCapacity(8);
            case 3 -> this.setCapacity(20);
            default -> throw new IllegalArgumentException(
                    this.getClass().getSimpleName()
                    + " cannot be greater than 3 (min_size=1, max_size=3)"
            );
        }

        this.size = size;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        throwErrorIfLessThanOne(capacity, "capacity");
        throwErrorIfLessThan(capacity, "capacity", this.getHeadcount(), "headcount");

        this.capacity = capacity;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    String getAttributeValues() {
        return "size=" + this.size
                + ", capacity=" + this.capacity
                + ", registrationNumber=" + this.registrationNumber
                + ", " + super.getAttributeValues();
    }

    // Two vehicles will be considered equal if their registration numbers are the same
    @Override
    public final boolean equals(Object o) {
        if (o instanceof Vehicle vehicle)
            return vehicle.getRegistrationNumber().equals(this.registrationNumber);

        return false;
    }

}