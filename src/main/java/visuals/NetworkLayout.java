package visuals;

import lists.DynamicArray;


import java.util.Iterator;

public class NetworkLayout implements Iterable<NodeData> {

    private final DynamicArray<NodeData> nodeDataList;
    private final DynamicArray<String> locationNames;

    private int maxX = 0;
    private int maxY = 0;

    public NetworkLayout() {
        this.nodeDataList = new DynamicArray<>();
        this.locationNames = new DynamicArray<>();
    }

    public void addNodeData(NodeData nodeData) {
        this.nodeDataList.append(nodeData);
        this.locationNames.append(nodeData.node().getId());

        int x = nodeData.x();
        int y = nodeData.y();

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

    public Iterator<NodeData> iterator() {

        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < nodeDataList.size();
            }

            @Override
            public NodeData next() {
                return nodeDataList.get(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

    }

}
