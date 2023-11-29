package entities;

import not_implemented_yet.Cell;

/**
 * Entity abstract class
 */
public abstract class Entity {

    private int headcount;
    private Cell cell;

    public Entity(int headcount, Cell cell) {
        this.setHeadcount(headcount);
        this.cell = cell;
    }

    public Entity(Cell cell) {
        this.headcount = 1;  // Idk if we can have empty entities?
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

    protected void setHeadcount(int headcount) {
        if (headcount < 1)
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() // So it works for all inheriting classes
                    + " headcount cannot be less than 1"
            );

        this.headcount = headcount;
    }

    public abstract String toString();

    // For simplifying toString() implementations
    protected String getAttributeValues() {
        return "headcount=" + this.headcount + ", cell=" + this.cell;
    }

}
