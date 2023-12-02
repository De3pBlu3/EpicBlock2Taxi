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
    node[] nodes;
    edge[] edges;
    node currentLocation;

    class edge {
        int weight; // length of edge / speed of edge = time to traverse edge (in ticks)
        node start;
        node end;
        entities.Entity[] occupants;
    }

    class node {
        String id;
        int x;
        int y;
        edge[] edges;
        entities.Entity[] occupants;
    }

    Network(node initNode) {
        node currentLocation = initNode;
        node nodes[] = new node[1]; // This could start with a size of much more than 1, but just for testing purposes this is ok
    }

    private void addNode(node UserNode) {
//         add node to network
        node[] temp_nodes = new node[this.nodes.length + 1]; // TODO create new array with more space, maybe create dynamic array
//         https://www.geeksforgeeks.org/creating-a-dynamic-array-in-java/ SOMETHING LIKE THIS

        System.arraycopy(this.nodes, 0, temp_nodes, 0, this.nodes.length);

//        manual copy, same as above
//        for (int i = 0; i < this.nodes.length; i++) {
//            temp_nodes[i] = this.nodes[i];
//        }

        temp_nodes[this.nodes.length] = UserNode;
        this.nodes = temp_nodes;

    }


//    SEARCHING FOR NODES AND EDGES IN NETWORK ---- TODO: implement searching algorithms
    private boolean checkNodeExists(node UserNode) {
        // check if node exists in network
        for (node n : this.nodes) {
            if (n.id.equals(UserNode.id)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkNodeExists(String nodeID) {
        // check if node exists in network
        for (node n : this.nodes) {
            if (n.id.equals(nodeID)) {
                return true;
            }
        }
        return false;
    }



    private void addEdgeToNodes(edge UserEdge) {
        // add edge to network
    }

    private void removeNode(node UserNode) {
        // remove node from network
    }

    // TODO: give the ability to add edges by two node id's. this will be public addEdge(String node1, String node2) or something idk.

    public void addEdge(node node1, node node2) {
        // add edge to network

    }


}
