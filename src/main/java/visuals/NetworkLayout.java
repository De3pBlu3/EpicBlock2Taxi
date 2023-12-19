package visuals;

import lists.DynamicArray;
import network.Node;


import java.util.Iterator;

public class NetworkLayout implements Iterable<Node> {

    private final DynamicArray<Node> nodes;
    private final DynamicArray<String> locationNames;

    private int maxX = 0;
    private int maxY = 0;

    public NetworkLayout() {
        this.nodes = new DynamicArray<>();
        this.locationNames = new DynamicArray<>();
    }

    public void addNode(Node node) {
        this.nodes.append(node);
        this.locationNames.append(node.getId());

        int x = node.getX();
        int y = node.getY();

        if (x < 0)
            throw new IllegalStateException("X coordinate is out of bounds for network visualisation");

        if (y < 0)
            throw new IllegalStateException("Y coordinate is out of bounds for network visualisation");

        if (x > this.maxX)
            this.maxX = x;

        if (y > this.maxY)
            this.maxY = y;

    }

    public DynamicArray<String> getLocationNames() {
        return this.locationNames;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    @Override
    public Iterator<Node> iterator() {

        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < nodes.size();
            }

            @Override
            public Node next() {
                return nodes.get(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

    }

}
