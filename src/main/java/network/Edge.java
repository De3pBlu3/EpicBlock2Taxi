package network;

@SuppressWarnings("unused")
public class Edge extends NetworkComponent {

    int weight; // length of edge / speed of edge = time to traverse edge (in ticks)
    Node start;
    Node end;

    public Edge() {}

    public Edge(Node first, Node second, int weight) {
        super();
        this.start = first;
        this.end = second;
        this.weight = weight;
    }

    public Edge(String id, Node first, Node second, int weight) {
        super(id);
        this.start = first;
        this.end = second;
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public Node getStart() {
        return this.start;
    }

    public Node getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "Edge[id=" + this.id
                + ", weight = " + this.weight
                + ", start = " + this.start
                + ", end = " + this.end
                + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge edge) {
            return edge.getId().equals(this.getId());
        }

        return false;
    }

}

