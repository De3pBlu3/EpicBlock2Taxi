package network;

public class Node {
    String id;
    int x;
    int y;
    Edge[] edges;
    entities.Entity[] occupants;

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
