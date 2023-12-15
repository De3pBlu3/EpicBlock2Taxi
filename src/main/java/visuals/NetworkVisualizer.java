package visuals;

import lists.DynamicArray;
import network.Edge;
import network.Network;
import network.Node;
import other.CSVReader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class NetworkVisualization extends JPanel {

    private Node[] nodes;
    private Edge[] edges;

    NetworkVisualization(Node[] nodes, Edge[] edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw edges
        g.setColor(Color.BLACK);
        for (Edge edge : edges) {
            g.drawLine(edge.getStart().getX(), edge.getStart().getY(), edge.getEnd().getX(), edge.getEnd().getY());
        }

        // Draw nodes
        g.setColor(Color.BLUE);
        for (Node node : nodes) {
            g.fillOval(node.getX() - 5, node.getY() - 5, 25, 25);
            g.drawString(node.getId(), node.getX() - 10, node.getY()-10);
        }
    }
}

public class NetworkVisualizer extends JFrame {

    public NetworkVisualizer(Node[] nodes, Edge[] edges) {
        setTitle("Network Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);

        NetworkVisualization networkVisualization = new NetworkVisualization(nodes, edges);
        add(networkVisualization);

        setVisible(true);
    }

    public static void main(String[] args) {
        Network network = new Network();

                DynamicArray<String> locationNames = CSVReader.processData(
                "src/main/csv/network_data.csv",
                network
        );

        // set x,y coordinates for each node
        int x = 50;
        int y = 50;
        for (Node node: network.getNodesAsArray()) {

            // reset after every 10 nodes
            if (x == 450) {
                x = 50;
                y += 100;
            }
            node.setX(x);
            node.setY(y);
            x += 100;
        }

        // Create and show the visualization
        SwingUtilities.invokeLater(() -> new NetworkVisualizer(network.getNodesAsArray(), network.getEdgesAsArray()));
    }


}
