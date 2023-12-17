package visuals;

import dispatch.Dispatch;
import entities.Entity;
import entities.Taxi;
import entities.Vehicle;
import lists.DynamicArray;
import network.Edge;
import network.Location;
import network.Network;
import network.Node;
import other.CSVReader;
import other.Util;

import javax.swing.*;
import java.awt.*;


class NetworkVisualization extends JPanel {

    private Node[] nodes;
    private Edge[] edges;
    private Dispatch dispatch;


    NetworkVisualization(Node[] nodes, Edge[] edges, Dispatch dispatch) {
        this.nodes = nodes;
        this.edges = edges;
        this.dispatch = dispatch;

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
            DynamicArray<Entity> x = node.getOccupants();

            for (int i = 0; i < x.length(); i++) {
                int x1 = node.getX()+5;
                int y1 = node.getY()+5;
                // draw vehicle image at src/main/java/taxi.png
                g.drawImage(new ImageIcon("src/main/java/taxi.png").getImage(), x1, y1+(i*15), 20, 20, null);
            }
        }


        Vehicle[] x = dispatch.getVehiclesOnMap();


    }
}

public class NetworkVisualizer extends JFrame {

    public NetworkVisualizer(Node[] nodes, Edge[] edges, Dispatch dispatch) {
        setTitle("Network Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);

        NetworkVisualization networkVisualization = new NetworkVisualization(nodes, edges, dispatch);
        add(networkVisualization);

        setVisible(true);
    }

    public static void main(String[] args) {
        Network network = new Network();

                DynamicArray<String> locationNames = CSVReader.processData(
                "src/main/csv/network_data.csv",
                network
        );

        Dispatch dispatch = new Dispatch();

        for (int i = 0; i < 5; i++) {
            int randomSize = Util.randInt(1, 3);
            Node node = network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1)));
            Location loc = new Location(node);
            Taxi taxi = new Taxi(randomSize, 1, loc);
            node.addOccupant(taxi);  // Add taxi to map!
            dispatch.registerVehicle(taxi);  // Add them to mister dispatch list thanks dispatch guy what a great guy
            dispatch.testAddToMap(taxi.getRegistrationNumber(), loc);
        }





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
        SwingUtilities.invokeLater(() -> new NetworkVisualizer(network.getNodesAsArray(), network.getEdgesAsArray(), dispatch));
    }


}
