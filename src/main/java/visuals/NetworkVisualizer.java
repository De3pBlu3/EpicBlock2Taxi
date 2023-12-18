package visuals;

import dispatch.Dispatch;
import entities.Entity;
import entities.Taxi;
import entities.Party;
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

    private final String TAXI_PATH = "src/main/png/taxi.png";
    private final String SPORTS_TAXI_PATH = "src/main/png/sports_taxi.png";
    private final String ELECTRIC_TAXI_PATH = "src/main/png/electric_taxi.png";
    private final String LIMO_TAXI_PATH = "src/main/png/limo_taxi.png";
    private final String USER_PATH = "src/main/png/user.png";

    private Node[] nodes;
    private Edge[] edges;
    private Dispatch dispatch;
    private Network network;

    NetworkVisualization(Network network, Dispatch dispatch) {
        this.network = network;
        this.dispatch = dispatch;
        update();
    }

    public void update() {
        updateNet();
        repaint();
    }
    private void updateNet() {
        this.nodes = network.getNodesAsArray();
        this.edges = network.getEdgesAsArray();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Clear the screen
        g.clearRect(0, 0, 10000, 10000);

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
                if (x.get(i) instanceof Taxi) {
                    int x1 = node.getX() + 5;
                    int y1 = node.getY() + 5;
                    g.drawImage(new ImageIcon(TAXI_PATH).getImage(), x1, y1+(i*15), 20, 20, null);
                }
                if (x.get(i) instanceof Party) {
                    int x1 = node.getX() + 5;
                    int y1 = node.getY() + 5;
                    g.drawImage(new ImageIcon(USER_PATH).getImage(), x1, y1+(i*15), 20, 20, null);
                }
            }

        }
    }
}


public class NetworkVisualizer extends JFrame {

    public NetworkVisualizer(Network network, Dispatch dispatch) {
        setTitle("Network Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);

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

        NetworkVisualization networkVisualization = new NetworkVisualization(network, dispatch);
        add(networkVisualization);

        setVisible(true);
    }

}
