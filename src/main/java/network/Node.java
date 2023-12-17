package network;

import lists.DynamicArray;

@SuppressWarnings("unused")
public class Node extends NetworkComponent {

    int x;
    int y;
    Edge[] edges;

    public void addEdge(Edge UserEdge){
        if (this.edges == null) {
            this.edges = new Edge[1];
            this.edges[0] = UserEdge;
            return;
        }
        Edge[] temp_edges = new Edge[this.edges.length + 1]; // TODO create new array with more space, maybe create dynamic array
        System.arraycopy(this.edges, 0, temp_edges, 0, this.edges.length);
        temp_edges[this.edges.length] = UserEdge;
        this.edges = temp_edges;
    }

    public Node(String id, float x, float y) {
        super(id);
        this.x = (int) x;   // TODO: to float or not to float?
        this.y = (int) y;
    }

    /**
     * Returns all edges within a certain range of this node.
     * If the weightLimit is 0, then nothing is returned.
     * @param weightLimit how far down the network to go
     * @return an array of edges within range
     */
    @Override
    public Edge[] getEdgesInRange(int weightLimit) {
        // get all components within range of node
        DynamicArray<Edge> componentsInRange = new DynamicArray<>();

        // starting from this node, iterate through all nodes in network
        // if node is within range, add to componentsInRange
        // recursively call this function on all nodes in componentsInRange
        for (Edge e: this.edges) {
            if (e.weight <= weightLimit) {
                componentsInRange.addIfNotPresent(e);
                getComponentsInRangeHelper(e.end, weightLimit-e.weight, componentsInRange);
            }
        }



        Edge[] componentsInRangeArray = new Edge[componentsInRange.length()];
        for (int i = 0; i < componentsInRange.length(); i++) {
            componentsInRangeArray[i] = componentsInRange.get(i);
        }
        return componentsInRangeArray;
    }

    private void getComponentsInRangeHelper(Node node, int weightLimit, DynamicArray<Edge> componentsInRange) {
        if (weightLimit <= 0) {
            return;
        }
        for (Edge e: node.edges) {
            if (e.weight <= weightLimit) {
                componentsInRange.addIfNotPresent(e);
                getComponentsInRangeHelper(e.end, weightLimit-e.weight, componentsInRange);
            }
        }
    }

    public Edge getEdge(Node node) {
        for (Edge e: this.edges) {
            if (e.end.equals(node)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Node[id='"
                + this.id
                + "', occupants = "
                + this.occupants
                + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node node) {
            return node.id.equals(this.id);
        }

        return false;
    }
//
//    public static boolean equals(Node n, Node userNode) {   // added for redundancy
//        return n.id.equals(userNode.id);
//    }

}
