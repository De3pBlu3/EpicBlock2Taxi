package entities;

import network.Location;
import static other.Util.randomRegistrationString;

abstract public class Vehicle extends Entity {

    private int capacity;
    private int size;
    private String registrationNumber;

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
            case 3 -> this.setCapacity(12);
            default -> throw new IllegalArgumentException("Size cannot be greater than 3 (min=1, max=3)");
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

    public void setRegistrationNumber(String reg) {
        this.registrationNumber = reg;
    }

    @Override
    public String toString() {
        return "Vehicle[" + this.getAttributeValues() + ']';
    }

    @Override
    String getAttributeValues() {
        return "size=" + this.size + ", capacity=" + this.capacity + ", registrationNumber=" + this.registrationNumber + super.getAttributeValues();
    }
}
