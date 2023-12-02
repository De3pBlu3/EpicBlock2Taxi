package network;

public class Node {
    String id;
    int x;
    int y;
    Edge[] edges;
    entities.Entity[] occupants;

    public static boolean equals(Node n, Node userNode) {   // added for redundancy
        return n.id.equals(userNode.id);
    }
}
