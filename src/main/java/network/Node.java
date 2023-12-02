package network;

public class Node {
    String id;
    int x;
    int y;
    Edge[] edges;
    entities.Entity[] occupants;

    public Node(String id, float x, float y) {
        this.x = (int) x;   // TODO: to float or not to float?
        this.y = (int) y;

        this.id = id;
    }

    public static boolean equals(Node n, Node userNode) {   // added for redundancy
        return n.id.equals(userNode.id);
    }

    @Override
    public String toString() {
        return "Node {" +
                "id='" + id + '\'' + "}";
//                ", (x=" + x +
//                ", y=" + y +  ")" +
//                '}';
    }
}
