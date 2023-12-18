package visuals;

import lists.DynamicArray;

import network.Node;
import network.Network;
import java.util.Iterator;

public class NetworkLayout implements Iterable<NodeData> {

    private final DynamicArray<String> map;
    private Network network;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public NetworkLayout(Network network, String... nodeAndLocationMap) {
        this.map = new DynamicArray<String>(nodeAndLocationMap);
        this.network = network;
        this.processDimensionData();
    }

    public NetworkLayout(String... nodeAndLocationMap) {
        this(null, nodeAndLocationMap);
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return this.network;
    }

    // Ignore for now
    private void processDimensionData() {
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
