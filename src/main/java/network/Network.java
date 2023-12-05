package network;


import java.util.Arrays;
import lists.DynamicArray;

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

    private DynamicArray<Node> nodes;
    private DynamicArray<Edge> edges;

    public Network() {
        this.nodes = new DynamicArray<Node>();
        this.edges = new DynamicArray<Edge>();
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

    // with weight should be default, NOT other way round TODO invert this
    public void addEdge(Node node1, Node node2, int weight) {
        this.addEdge(node1, node2);
        this.getEdge(node1, node2).weight = weight;
    }

    public void addEdge(String nodeID1, String nodeID2, int weight) {
        this.addEdge(nodeID1, nodeID2);
        this.getEdge(nodeID1, nodeID2).weight = weight;

    }

    private Edge getEdge(String nodeID1, String nodeID2) {
        // get edge from network
        for (Edge e : this.edges) {
            if (e.start.id.equals(nodeID1) && e.end.id.equals(nodeID2)) {
                return e;
            }
        }
        return null;
    }

    private Edge getEdge(Node node1, Node node2) {
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

    public Node[] findPath(Node Nodestart, Node Nodeend) {
        // find path from start to end
        // A* would be cool, but would require a direct line of distance to target node geometrically
        // so we will use Dijkstra's algorithm
        //https://stackoverflow.com/a/10053969
        //"""From Introduction to Algorithms (CLRS) second edition, page 581 :
        //Find a shortest path from u to v for a given vertices u and v.
        // If we solve the single-source problem with source vertex u, we solve this problem also.
        // Moreover, no algorithms for this problem are known that run asymptotically faster than the best single-source algorithms in the worst case.
        //So, stick to Dijkstra's algorithm :)"""

         class DijkstraNode {
            Node node;
            int dist;
            DijkstraNode prev;

            DijkstraNode(Node node, int dist) {
                this.node = node;
                this.dist = dist;
                this.prev = null;
            }

         }

        DynamicArray<DijkstraNode> dijkstraNodes = new DynamicArray<DijkstraNode>();
        for (int i = 0; i < this.nodes.length(); i++) {
            dijkstraNodes.append(new DijkstraNode(this.nodes.get(i), Integer.MAX_VALUE));
        }

        // find start node TODO extract this to a function
        DijkstraNode startNode = null;
        for (DijkstraNode dn : dijkstraNodes) {
            if (dn.node == Nodestart) {
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
            if (dn.node == Nodeend) {
                endNode = dn;
                break;
            }
        }
        if (endNode == null) {
            throw new IllegalArgumentException("Destination node not found in network");
        }


        startNode.dist = 0;

        DijkstraNode[] nodesArray = dijkstraNodes.toArray();
        Arrays.sort(nodesArray, (a, b) -> a.dist - b.dist);
        dijkstraNodes = new DynamicArray<DijkstraNode>(nodesArray);

        while (!dijkstraNodes.isEmpty()) {
            DijkstraNode u = dijkstraNodes.get(0);
            for (DijkstraNode dn : dijkstraNodes) {
                if (dn.dist < u.dist) {
                    u = dn;
                }
            }
            if (u.node == Nodeend) {
                break;
            }

            DijkstraNode[] temp_Q = new DijkstraNode[dijkstraNodes.length() - 1];
            int temp_Q_index = 0;
            for (DijkstraNode dn : dijkstraNodes) {
                if (dn != u) {
                    temp_Q[temp_Q_index] = dn;
                    temp_Q_index++;
                }
            }
            dijkstraNodes = new DynamicArray<DijkstraNode>(temp_Q);

            for (Edge e : u.node.edges) {
                DijkstraNode v = null;
                for (DijkstraNode dn : dijkstraNodes) {
                    if (dn.node == e.end) {
                        v = dn;
                        break;
                    }
                }
                int alt = u.dist + e.weight;
                if (v == null) {
                    continue;
                }
                if (alt < v.dist) {
                    v.dist = alt;
                    v.prev = u;
                }
            }
        }

        DynamicArray<Node> path = new DynamicArray<Node>();
        DynamicArray<DijkstraNode> pathList = new DynamicArray<DijkstraNode>();

        for (DijkstraNode dn = endNode; dn != null; dn = dn.prev) {
            pathList.append(dn); // append node to the path
        }


        // reverse the path
        for (int i = 0; i < pathList.size(); i++) {
            path.append(pathList.get(pathList.size() - i - 1).node);
        }

        return path.toArray();

    }

    public Node[] findPath(String nodeID1, String nodeID2) {
        return findPath(getNode(nodeID1), getNode(nodeID2));
    }


}
