package network;


@SuppressWarnings("unused")
public class Edge extends NetworkComponent {
    int weight; // length of edge / speed of edge = time to traverse edge (in ticks)
    Node start;
    Node end;

    public Edge(Node first, Node second, int weight) {
        this.start = first;
        this.end = second;
        this.weight = weight;
    }

    public Edge() {
    }


    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "{" +
                "weight = " + weight +
                ", start = " + start +
                ", end = " + end +
                ", occupants = " + occupants +
                '}';
    }

}

