package network;


/**
 * Network class! Will have edges and nodes, and will be used to keep track of the network. once the network is created, we will
 * find the central node and call that the "root" node. Though it will be cool to add an entity based on x,y or address?
 *
 * """If some edges connect nodes not yet in the graph, the nodes are added automatically.
 * There are no errors when adding nodes or edges that already exist""" - from networkx documentation
 * we will do the same thing, and just add the node or edge to the network if it doesn't exist.
 *
 */

public class Network {
    private Node[] nodes;
    private Edge[] edges;

    public Network() {
        this.nodes = new Node[0]; // This could start with a size of much more than 1, but just for testing purposes this is ok
        this.edges = new Edge[0];
    }

    private void addNodeToNodesArray(Node UserNode) {
//         add node to network
        Node[] temp_nodes = new Node[this.nodes.length + 1]; // TODO create new array with more space, maybe create dynamic array
//         https://www.geeksforgeeks.org/creating-a-dynamic-array-in-java/ SOMETHING LIKE THIS

        System.arraycopy(this.nodes, 0, temp_nodes, 0, this.nodes.length);

//        manual copy, same as above
//        for (int i = 0; i < this.nodes.length; i++) {
//            temp_nodes[i] = this.nodes[i];
//        }

        temp_nodes[this.nodes.length] = UserNode;
        this.nodes = temp_nodes;

    }
    private void addEdgeToEdgesArray(Edge UserEdge) {
        // add edge to network
        Edge[] temp_edges = new Edge[this.edges.length + 1]; // TODO create new array with more space, maybe create dynamic array

        System.arraycopy(this.edges, 0, temp_edges, 0, this.edges.length);

        temp_edges[this.edges.length] = UserEdge;
        this.edges = temp_edges;

    }

    private Node getNode(String nodeID) {
        // get node from network
        for (Node n : this.nodes) {
            if (n.id.equals(nodeID)) {
                return n;
            }
        }
        return null;
    }

//    SEARCHING FOR NODES AND EDGES IN NETWORK ---- TODO: implement searching algorithms
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
        // check if node exists in network
        for (Node n : this.nodes) {
            if (n.id.equals(nodeID)) {
                return true;
            }
        }
        return false;
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

    // TODO: give the ability to add edges by two node id's. this will be public addEdge(String node1, String node2) or something idk.
    public void addEdge(Node node1, Node node2) {
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
        UserEdge.weight = 1; // TODO: make this be the distance between the two nodes (in ticks)
        addEdgeToEdgesArray(UserEdge);

    }

    public void addEdge(String nodeID1, String nodeID2) {
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
        UserEdge.weight = 1; // TODO: make this be the distance between the two nodes (in ticks)
        addEdgeToEdgesArray(UserEdge);

    }

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
        UserEdge.weight = weight; // TODO: make this be the distance between the two nodes (in ticks)
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

    public Node[] getNodes() {
        return nodes;
    }

    public Edge[] getEdges() {
        return edges;
    }



}
