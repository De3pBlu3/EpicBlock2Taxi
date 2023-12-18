package visuals;

import dispatch.Dispatch;
import entities.Entity;
import entities.Taxi;
import entities.Party;
import lists.DynamicArray;
import network.Edge;
import network.Network;
import network.Node;

import javax.swing.*;
import java.awt.*;


class NetworkVisualization extends JPanel {

    private Node[] nodes;
    private Edge[] edges;
    private final Dispatch dispatch;
    private final Network network;

    NetworkVisualization(Network network, Dispatch dispatch) {
        this.network = network;
        this.dispatch = dispatch;
        update();
    }

    public void update() {
        updateNet();
        repaint();
        setBackground(Color.DARK_GRAY);
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
        g.setColor(new Color(211, 211, 211, 60));
        for (Edge edge : edges) {

            int x1 = edge.getStart().getX();
            int y1 = edge.getStart().getY();

            int x2 = edge.getEnd().getX();
            int y2 = edge.getEnd().getY();

            g.drawLine(x1, y1, x2, y2);
        }

        // Draw nodes
        for (Node node : nodes) {
            g.setColor(Color.BLUE);
            g.fillOval(node.getX() - 13, node.getY() - 13, 26, 26);
            g.setColor(Color.WHITE);
            g.drawString(node.getId(), node.getX() - 20, node.getY()-10);

            DynamicArray<Entity> occupants = node.getOccupants();
            int index = 0;

            for (Entity entity : occupants) {

                Image image = entity.getImage();
                int width = entity.getImageWidth();
                int height = entity.getImageHeight();

                int x1 = node.getX() + 5;
                int y1 = node.getY() + 5 + (index++ * 15);

                g.drawImage(image, x1, y1, width, height, null);
            }

        }
    }
}


public class NetworkVisualizer extends JFrame {

    public NetworkVisualizer(Network network, Dispatch dispatch) {
        setTitle("Network Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        // set x,y coordinates for each node
        int x = 50;
        int y = 50;

        for (Node node: network.getNodesAsArray()) {

            // reset after every 10 nodes
            if (x == 650) {
                x = 50;
                y += 100;
            }
            node.setX(x);
            node.setY(y);
            x += 150;
        }

        NetworkVisualization networkVisualization = new NetworkVisualization(network, dispatch);
        add(networkVisualization);

        setIconImage(new ImageIcon("src/main/png/map_icon.png").getImage());
        setVisible(true);
    }

}
