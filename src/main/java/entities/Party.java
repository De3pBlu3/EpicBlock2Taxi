package entities;

import not_implemented_yet.Node;

/**
 * Party class
 */
public class Party extends Entity {

    public Party(int headcount, Node node) {
        super(headcount, node);
    }

    public Party(Node node) {
        super(1, node);
    }

    @Override
    public String toString() {
        return "Party[" + this.getAttributeValues() + "]";
    }

}
