package entities;

import not_implemented_yet.Cell;

/**
 * Taxi class
 */
public class Taxi extends Entity {

    private int capacity;

    public Taxi(int capacity, int headcount, Cell cell) {
        super(headcount, cell);
        this.capacity = capacity;
    }

    public Taxi(int capacity, Cell cell) {
        super(cell);
        this.capacity = capacity;
    }

    public Taxi(Cell cell) {
        super(cell);
        this.capacity = 0;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        // Some logic maybe
        this.capacity = capacity;
    }

    public void goTo(Cell cell) {
        // Goes to that cell
    }

    public void goTo(Party party) {
        // Goes to party.getCell() cell
    }

    @Override
    public String toString() {
        return "Taxi[capacity=" + this.capacity + ", " + this.getAttributeValues() + "]";
    }

}
