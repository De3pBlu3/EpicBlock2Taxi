package data_structures.network;

import data_structures.lists.DynamicArray;

import java.util.Arrays;

/**
 * Network class! Will have edges and nodes, and will be used to keep track of the network. once the network is created, we will
 * find the central node and call that the "root" node. Though it will be cool to add an entity based on x,y or address?
 * <p>
 * """If some edges connect nodes not yet in the graph, the nodes are added automatically.
 * There are no errors when adding nodes or edges that already exist""" - from networkx documentation
 * we will do the same thing, and just add the node or edge to the network if it doesn't exist.
 */

@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "unused"})
public class Network {

    private final DynamicArray<Node> nodes;
    private final DynamicArray<Edge> edges;
    private final DynamicArray<Edge> uniqueEdges;

    public Network() {
        this.nodes = new DynamicArray<>();
        this.edges = new DynamicArray<>();
        this.uniqueEdges = new DynamicArray<>();
    }

    private void addNodeToNodesArray(Node UserNode) {
        nodes.append(UserNode);
    }
    private void addEdgeToEdgesArray(Edge UserEdge) {
        // if edge already exists, check if weight is less than current, if so, change weight

        if (checkEdgeExists(UserEdge)) {
            if (UserEdge.weight < getEdge(UserEdge.start, UserEdge.end).weight) {
                getEdge(UserEdge.start, UserEdge.end).weight = UserEdge.weight;
            }
            return;
        }
        // add edge to network
        edges.append(UserEdge);
        UserEdge.start.addEdge(UserEdge);
        UserEdge.end.addEdge(UserEdge);
    }

    public Node getNode(String nodeID) {
        // get node from network
        for (Node n : this.nodes) {
            if (n.id.equals(nodeID)) {
                return n;
            }
        }
        return null;
    }

//    SEARCHING FOR NODES AND EDGES IN NETWORK
    private boolean checkNodeExists(Node UserNode) {
        // check if node exists in network
        for (Node n : this.nodes) {
            if (UserNode == n) { // == and not .equals because we are looking for the same object, not the same value
                return true;
            }
        }
        return false;
    }
    private boolean checkNodeExists(String nodeID) {
        return this.getNode(nodeID) != null;
    }
    private boolean checkEdgeExists(Edge UserEdge) {
        // check if edge exists in network
        for (Edge e : this.edges) {
            if (e.start.id.equals(UserEdge.start.id) && e.end.id.equals(UserEdge.end.id)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkEdgeExists(String nodeID1, String nodeID2) {
        // check if edge exists in network
        for (Edge e : this.edges) {
            if (e.start.id.equals(nodeID1) && e.end.id.equals(nodeID2)) {
                return true;
            }
        }
        return false;
    }

// ----------------------------------------------------


    private void removeNode(Node UserNode) {
        // remove node from network
    }

    public void addEdge(Node node1, Node node2) {
        this.addEdge(node1, node2, 1);
    }

    public void addEdge(String nodeID1, String nodeID2) {
        this.addEdge(nodeID1, nodeID2, 1);

    }

    // with weight should be default, NOT other way round
    public void addEdge(Node node1, Node node2, int weight) {
        // add edge to network
        if (!checkNodeExists(node1)) {
            addNodeToNodesArray(node1);
        }
        if (!checkNodeExists(node2)) {
            addNodeToNodesArray(node2);
        }
        Edge UserEdge = new Edge();
        UserEdge.start = node1;
        UserEdge.end = node2;
        UserEdge.weight = weight;
        addEdgeToEdgesArray(UserEdge);
    }

    public void addEdge(String nodeID1, String nodeID2, int weight) {
        // add edge to network
        if (!checkNodeExists(nodeID1)) {
            Node node1 = new Node(nodeID1, 0, 0);
            addNodeToNodesArray(node1);
        }
        if (!checkNodeExists(nodeID2)) {
            Node node2 = new Node(nodeID2, 0, 0);
            addNodeToNodesArray(node2);
        }
        Edge UserEdge = new Edge();
        UserEdge.start = getNode(nodeID1);
        UserEdge.end = getNode(nodeID2);
        UserEdge.weight = weight; // TODO: make this be the distance between the two nodes (in ticks)
        addEdgeToEdgesArray(UserEdge);
    }

    public void addTwoWayEdge(String nodeID1, String nodeID2, int weight) {

        if (!checkNodeExists(nodeID1)) {
            Node node1 = new Node(nodeID1, 0, 0);
            addNodeToNodesArray(node1);
        }
        if (!checkNodeExists(nodeID2)) {
            Node node2 = new Node(nodeID2, 0, 0);
            addNodeToNodesArray(node2);
        }

        Node firstNode = this.getNode(nodeID1);
        Node secondNode = this.getNode(nodeID2);

        Edge edge1 = new Edge(null, firstNode, secondNode, weight);
        addEdgeToEdgesArray(edge1);

        Edge edge2 = new Edge(null, secondNode, firstNode, weight);
        addEdgeToEdgesArray(edge2);

        this.uniqueEdges.append(edge1);
    }

    public Edge getEdge(String nodeID1, String nodeID2) {
        // get edge from network
        for (Edge e : this.edges) {
            if (e.start.id.equals(nodeID1) && e.end.id.equals(nodeID2)) {
                return e;
            }
        }
        return null;
    }

    public Edge getEdge(Node node1, Node node2) {
        for (Edge e : this.edges) {
            if (e.start == node1 && e.end == node2) {
                return e;
            }
        }
        return null;
    }


    public DynamicArray<Node> getNodes() {
        return nodes;
    }

    public DynamicArray<Edge> getEdges() {
        return edges;
    }

    public Node[] findPath(Node nodeStart, Node nodeEnd) {
        // find path from start to end
        // A* would be cool, but would require a direct line of distance to target node geometrically,
        // so we will use Dijkstra's algorithm
        //https://stackoverflow.com/a/10053969
        //"""From Introduction to Algorithms (CLRS) second edition, page 581 :
        //Find the shortest path from u to v for a given vertices u and v.
        // If we solve the single-source problem with source vertex u, we solve this problem also.
        // Moreover, no algorithms for this problem are known that run asymptotically faster than the best single-source algorithms in the worst case.
        //So, stick to Dijkstra's algorithm :)"""

         class DijkstraNode {
            final Node node;
            int dist;
            DijkstraNode prev;

            DijkstraNode(Node node, int dist) {
                this.node = node;
                this.dist = dist;
                this.prev = null;
            }

         }

        DynamicArray<DijkstraNode> dijkstraNodes = new DynamicArray<>();
        for (int i = 0; i < this.nodes.length(); i++) {
            dijkstraNodes.append(new DijkstraNode(this.nodes.get(i), Integer.MAX_VALUE));
        }

        // find start node TODO extract this to a function
        DijkstraNode startNode = null;
        for (DijkstraNode dn : dijkstraNodes) {
            if (dn.node == nodeStart) {
                startNode = dn;
                break;
            }
        }

        if (startNode == null) {
            throw new IllegalArgumentException("Source node not found in network");
        }

        // find destination node
        DijkstraNode endNode = null;
        for (DijkstraNode dn : dijkstraNodes) {
            if (dn.node == nodeEnd) {
                endNode = dn;
                break;
            }
        }
        if (endNode == null) {
            throw new IllegalArgumentException("Destination node not found in network");
        }


        startNode.dist = 0;

        // sort nodes by distance

        DijkstraNode[] nodesArray = new DijkstraNode[dijkstraNodes.length()];

        for (int i = 0; i < dijkstraNodes.length(); i++) {
            nodesArray[i] = dijkstraNodes.get(i);
        }

        Arrays.sort(nodesArray, (a, b) -> a.dist - b.dist);


        dijkstraNodes = new DynamicArray<>(nodesArray); // turn array back into dynamic array

        while (!dijkstraNodes.isEmpty()) {

            // ========== FIND SMALLEST DISTANCE NODE ==========
            DijkstraNode u = dijkstraNodes.get(0);

            for (DijkstraNode dn : dijkstraNodes) {
                if (dn.dist < u.dist) {
                    u = dn;
                }
            }

            if (u.node == nodeEnd) {    // if we have reached the end node, break
                break;
            }

            // =================================================



            // remove u from dijkstraNodes array
            DijkstraNode[] temp_Q = new DijkstraNode[dijkstraNodes.length() - 1];

            int temp_Q_index = 0;

            for (DijkstraNode dn : dijkstraNodes) {
                if (dn != u) {
                    temp_Q[temp_Q_index] = dn;
                    temp_Q_index++;
                }
            }


            // turn array back into dynamic array
            dijkstraNodes = new DynamicArray<>(temp_Q);

            // ================================================


            // for edge in the smallest distance node
            for (Edge e : u.node.edges) {

                DijkstraNode v = null;

                // for each other node in the network (not current smallest)
                for (DijkstraNode dn : dijkstraNodes) {
                    if (dn.node == e.end) { // IF the end of the edge lands on a new node (i,e the direction is outwards)
                        v = dn;
                        break;
                    }
                }

                if (v == null) { // if edge points inwards
                    continue;
                }

                int alt = u.dist + e.weight; // current distance to node + weight of edge (new distance)

                if (alt < v.dist) { // if new distance is less than current distance

                    v.dist = alt;
                    v.prev = u;
                }
            }
        }

        DynamicArray<Node> path = new DynamicArray<>();
        DynamicArray<DijkstraNode> pathList = new DynamicArray<>();

        for (DijkstraNode dn = endNode; dn != null; dn = dn.prev) {
            pathList.append(dn); // append node to the path
        }


        // reverse the path
        for (int i = 0; i < pathList.size(); i++) {
            path.append(pathList.get(pathList.size() - i - 1).node);
        }

        Node[] finalPath = new Node[path.size()];
        for (int i = 0; i < path.size(); i++) {
            finalPath[i] = path.get(i);
        }
        return finalPath;

    }

    public Node[] findPath(String nodeID1, String nodeID2) {
        return findPath(getNode(nodeID1), getNode(nodeID2));
    }


    public Edge[] getEdgesAsArray(){
        Edge[] edgesArray = new Edge[this.edges.length()];
        for (int i = 0; i < this.edges.length(); i++) {
            edgesArray[i] = this.edges.get(i);

        }
        return edgesArray;
    }


    /**
     * Returns all edges within a certain range of a node.
     * If the weightLimit is 0, then nothing is returned.
     * @param rootNode the node to start from
     * @param weightLimit how far down the network to go
     * @return an array of edges within range
     */
    public Edge[] getEdgesInRange(Node rootNode, int weightLimit) {
        // get all components within range of node
        DynamicArray<Edge> componentsInRange = new DynamicArray<>();

        // starting from node, iterate through all nodes in network
        // if node is within range, add to componentsInRange
        // recursively call this function on all nodes in componentsInRang
        for (Edge e: rootNode.edges) {
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
    private void getComponentsInRangeHelper(Node Rootnode, int weightLimit, DynamicArray<Edge> componentsInRange) {
        for (Edge e: Rootnode.edges) {
            if (e.weight <= weightLimit) {
                componentsInRange.addIfNotPresent(e);
                getComponentsInRangeHelper(e.end, weightLimit-e.weight, componentsInRange);
            }
        }
    }


    public Node[] getNodesAsArray() {
        Node[] nodesArray = new Node[this.nodes.length()];
        for (int i = 0; i < this.nodes.length(); i++) {
            nodesArray[i] = this.nodes.get(i);
        }
        return nodesArray;
    }

    public Edge[] getEdgesWithIDAsArray() {
        Edge[] edges = new Edge[this.uniqueEdges.length()];

        for (int i = 0; i < this.uniqueEdges.length(); i++) {
            edges[i] = this.uniqueEdges.get(i);
        }

        return edges;
    }

}
