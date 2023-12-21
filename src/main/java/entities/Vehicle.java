package entities;

import data_structures.network.Edge;
import data_structures.network.Location;
import data_structures.network.Node;
import employees.Driver;

import java.util.Arrays;

import static misc.Util.randomRegistrationString;

@SuppressWarnings("unused")
abstract public sealed class Vehicle extends Entity permits Taxi {

    private int size;
    private int capacity;
    private final String registrationNumber;
    private Party occupyingParty;
    private final Driver driver;
    private Node[] currentPath;
    private int currentPathIndex = 0;

    public Vehicle(int size, String registrationNumber, Location loc) {
        super(loc);
        this.setSize(size);
        this.registrationNumber = registrationNumber;
        this.driver = new Driver();
    }

    public Vehicle(int size, Location loc) {
        super(loc);
        this.setSize(size);
        this.registrationNumber = randomRegistrationString();
        this.driver = new Driver();
    }

    public int getSize() {
        return this.size;
    }

    public Node getNextNode() {
        if (this.currentPathIndex < this.currentPath.length - 1) {
            this.currentPathIndex++;
        }
        else {
            System.out.println(this.currentPathIndex);
            System.out.println(this.currentPath.length);
            System.out.println(this.currentPath[this.currentPathIndex]);
            System.out.println(Arrays.toString(this.currentPath));
            throw new IllegalArgumentException("Vehicle is at end of path");
        }
    	return this.currentPath[this.currentPathIndex];
    }
    public void setCurrentPath(Node[] currentPath) {
        this.currentPathIndex = 0;
    	this.currentPath = currentPath;
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

    public Driver getDriver() {
        return this.driver;
    }

    public double getRating() {
        return this.driver.getRating();
    }

    public void rate(double rating) {
        this.driver.rate(rating);
    }

    public void MoveVehicleOneStep(Location loc){
        Location oldLoc = this.getLocation();

        // if vehicle is already at location, do nothing
        if (oldLoc.currentNetLocation().equals(loc.currentNetLocation())){
            return;
        }
        // if vehicle is at edge, do not move
        if (oldLoc.currentNetLocation().getClass() == Edge.class){
            return;
        }

        // is there an edge between the two locations?

        oldLoc.currentNetLocation().removeOccupant(this);
        loc.currentNetLocation().addOccupant(this);
        this.setLocation(loc);
    }

    public void printTableRow() {
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

        System.out.println(row);
    }

    public void showDriverSummary() {
        System.out.printf(
                "%-15s%-20s%-20d%-25s%-15d%-25.2f%-15s%n",
                this.getVehicleName(),
                this.registrationNumber,
                this.driver.getTripsComplete(),
                this.driver.getName(),
                this.driver.getAge(),
                this.driver.getBalance(),
                ((int) this.driver.getRating()) + "/5"
        );
    }

    public String getVehicleName() {
        return this.getClass().getSimpleName();
    }

    protected int getVehicleCharge() {
        return 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "['" + this.registrationNumber + "']";
    }

}
