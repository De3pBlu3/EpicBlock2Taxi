package entities;

import not_implemented_yet.Cell;

/**
 * Taxi class
 */
public class Taxi extends Entity {

    private int capacity;

    public Taxi(int capacity, int headcount, Cell cell) {
        super(headcount, cell);
        this.setCapacity(capacity);
    }

    public Taxi(int capacity, Cell cell) {
        super(1, cell);
        this.setCapacity(capacity);
    }

    public Taxi(Cell cell) {
        super(1, cell);
        this.capacity = 1;  // Can a taxi have 0 as their capacity?
    }

    public int getCapacity() {
        return this.capacity;
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

    public void goTo(Cell cell) {
        // Goes to that cell
        // To be implemented
    }

    public void goTo(Party party) {
        // Goes to party.getCell() cell
        // To be implemented
    }

    @Override
    public String toString() {
        return "Taxi[capacity=" + this.capacity + ", " + this.getAttributeValues() + "]";
    }

}
