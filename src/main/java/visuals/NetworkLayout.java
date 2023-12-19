package visuals;

import lists.DynamicArray;

import network.Node;
import network.Network;
import java.util.Iterator;

public class NetworkLayout implements Iterable<NodeData> {

    private final DynamicArray<String> map;
    private Network network;

    private int maxX = 0;
    private int maxY = 0;

    public NetworkLayout(Network network, String... nodeAndLocationMap) {
        this.map = new DynamicArray<>(nodeAndLocationMap);
        this.network = network;
        this.processDimensionData();
    }

    public NetworkLayout(String... nodeAndLocationMap) {
        this.map = new DynamicArray<>(nodeAndLocationMap);
    }

    public void setNetwork(Network network) {
        this.network = network;
        this.processDimensionData();
    }

    public Network getNetwork() {
        return this.network;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    private void processDimensionData() {
        for (NodeData data : this) {
            int x = data.x();
            int y = data.y();

            if (x < 0)
                throw new IllegalStateException("X coordinate is out of bounds for network visualisation");

            if (y < 0)
                throw new IllegalStateException("Y coordinate is out of bounds for network visualisation");

            if (x > this.maxX)
                this.maxX = x;

            if (y > this.maxY)
                this.maxY = y;

        }
    }

    @Override
    public Iterator<NodeData> iterator() {

        if (getNetwork() == null) {
            throw new IllegalStateException("Iterator cannot be invoked if NetworkLayout.network is null");
        }

        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < map.size();
            }

            @Override
            public NodeData next() {
                String[] mappedArray = map.get(index++).split(", ");

                Node node = network.getNode(mappedArray[0]);
                int x = Integer.parseInt(mappedArray[1]);
                int y = Integer.parseInt(mappedArray[2]);

                return new NodeData(node, x, y);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

    }

}
