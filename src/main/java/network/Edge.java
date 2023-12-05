package network;

import java.util.Arrays;

public class Edge {
    int weight; // length of edge / speed of edge = time to traverse edge (in ticks)
    Node start;
    Node end;
    entities.Entity[] occupants;

    public Edge(Node first, Node second, int weight) {
        this.start = first;

        this.end = second;
        this.weight = weight;
    }

    public Edge() {
    }



    @Override
    public String toString() {
        return "{" +
                "weight = " + weight +
                ", start = " + start +
                ", end = " + end +
                ", occupants = " + Arrays.toString(occupants) +
                '}';
    }
}

