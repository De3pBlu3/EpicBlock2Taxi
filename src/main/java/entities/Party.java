package entities;

import not_implemented_yet.Cell;

/**
 * Party class
 */
public class Party extends Entity {

    public Party(int headcount, Cell cell) {
        super(headcount, cell);
    }

    public Party(Cell cell) {
        super(1, cell);
    }

    @Override
    public String toString() {
        return "Party[" + this.getAttributeValues() + "]";
    }

}
