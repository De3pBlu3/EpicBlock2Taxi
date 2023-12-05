package entities;

import not_implemented_yet.Node;

/**
 * Entity abstract class
 */
public abstract class Entity {

    private int headcount;
    private Node node;

    public Entity(int headcount, Node node) {
        this.setHeadcount(headcount);
        this.node = node;
    }

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        // Some logic maybe
        this.node = node;
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
        return "headcount=" + this.headcount + ", node=" + this.node;
    }

}
