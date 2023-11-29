package entities;

import not_implemented_yet.Cell;

/**
 * Entity abstract class
 */
public abstract class Entity {

    private int headcount = 0;
    private Cell cell;

    public Entity(int headcount, Cell cell) {
        this.headcount = headcount;
        this.cell = cell;
    }

    public Entity(Cell cell) {
        this.headcount = 1; // idk if we can have empty entities?
        this.cell = cell;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        // Some logic maybe
        this.cell = cell;
    }

    public int getHeadcount() {
        return this.headcount;
    }

    public void setHeadcount(int headcount) {
        // Some logic maybe
        this.headcount = headcount;
    }

    public abstract String toString();

    // For simplifying toString() implementations
    protected String getAttributeValues() {
        return "headcount=" + this.headcount + ", cell=" + this.cell;
    }

}
