package visuals;

import dispatch.Dispatch;
import entities.Entity;
import network.Edge;
import network.Network;
import network.Node;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
class NetworkVisualization extends JPanel {

    private static final Color LIGHT_GRAY = new Color(200, 200, 200);
    private static final Color LIGHTER_GRAY = new Color(211, 211, 211);
    private static final Color DARK_GRAY = new Color(76, 78, 82);

    private final int nodeRadius = 13;
    private final int nodeDiameter = nodeRadius * 2;
    private final int nodeBorderWidth = 4;
    private final int outerNodeCircleOffset = -this.nodeRadius;
    private final int innerNodeCircleOffset = -this.nodeRadius + (this.nodeBorderWidth / 2);
    private final int innerNodeCircleDiameter = this.nodeDiameter - this.nodeBorderWidth;
    private final int labelYOffset = -this.nodeRadius - 5;

    private final Font font = new Font("Helvetica", Font.BOLD, 10);

    private Node[] nodes;
    private Edge[] edges;
    private final Dispatch dispatch;
    private final Network network;

    NetworkVisualization(Network network, Dispatch dispatch) {
        this.network = network;
        this.dispatch = dispatch;
        this.setBackground(DARK_GRAY);
        this.setFont(this.font);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 450, 700));
        this.update();
    }

    public void update() {
        this.updateNet();
        this.repaint();
    }

    private void updateNet() {
        this.nodes = network.getNodesAsArray();
        this.edges = network.getEdgesAsArray();
    }

    private void drawLabelledNode(Graphics g, Node node) {

        String label = node.getId();
        FontMetrics fm = g.getFontMetrics();
        int halfLabelLength = fm.stringWidth(label) / 2;

        // Outer circle (border)
        g.setColor(LIGHT_GRAY);
        g.fillOval(
                node.getX() + this.outerNodeCircleOffset,
                node.getY() + this.outerNodeCircleOffset,
                this.nodeDiameter,
                this.nodeDiameter
        );

        // Inner circle
        g.setColor(DARK_GRAY);
        g.fillOval(
                node.getX() + this.innerNodeCircleOffset,
                node.getY() + this.innerNodeCircleOffset,
                this.innerNodeCircleDiameter,
                this.innerNodeCircleDiameter
        );

        g.setColor(Color.WHITE);

        g.drawString(
                node.getId(),
                node.getX() - halfLabelLength,
                node.getY() +  this.labelYOffset
        );

    }

    private void drawNodeOccupants(Graphics g, Node node) {

        int index = 0;

        for (Entity occupant : node.getOccupants()) {

            Image image = occupant.getImage();
            int width = occupant.getImageWidth();
            int height = occupant.getImageHeight();

            int x1 = node.getX() + 5;
            int y1 = node.getY() + 5 + (index++ * 15);

            g.drawImage(image, x1, y1, width, height, null);
        }
    }

    private void drawEdgeConnection(Graphics g, Edge edge) {
        int x1 = edge.getStart().getX();
        int y1 = edge.getStart().getY();

        int x2 = edge.getEnd().getX();
        int y2 = edge.getEnd().getY();

        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    protected void paintComponent(Graphics g) {

        // Clear the screen
        g.clearRect(0, 0, 10000, 10000);
        super.paintComponent(g);

        // Draw edges
        g.setColor(LIGHTER_GRAY);

        for (Edge edge : edges) {
            this.drawEdgeConnection(g, edge);
        }

        // Draw nodes
        for (Node node : nodes) {
            this.drawLabelledNode(g, node);
            this.drawNodeOccupants(g, node);
        }
    }
}

@SuppressWarnings("FieldCanBeLocal")
public class NetworkVisualizer extends JFrame {

    private final static int WINDOW_WIDTH = 700;
    private final static int WINDOW_HEIGHT = 500;

    private final int xOffset = 150;
    private final int yOffset = 100;

    public NetworkVisualizer(Network network, Dispatch dispatch, NetworkLayout networkLayout) {

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("Network Visualization");
        this.setIconImage(new ImageIcon("src/main/png/map_icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        if (networkLayout == null) {

            // Beginning co-ordinates for first node
            int x = 100;
            int y = 100;

            for (Node node : network.getNodesAsArray()) {

                // reset after every 5 nodes
                if (x == WINDOW_WIDTH) {
                    y += yOffset;  // Move y co-ord to new row
                    x = 100;    // Reset x co-ord to beginning of new row
                }

                node.setX(x);
                node.setY(y);
                x += xOffset;
            }

        } else {

            for (NodeData nodeData : networkLayout) {

                Node node = nodeData.node();
                int x = nodeData.x();
                int y = nodeData.y();

                if (x < 0 || x > 700) {
                    throw new IllegalStateException("X coordinate is out of bounds for network visualisation");
                }

                if (y < 0 || y > 500) {
                    throw new IllegalStateException("Y coordinate is out of bounds for network visualisation");
                }

                node.setX(x);
                node.setY(y);
            }
        }

        NetworkVisualization networkVisualization = new NetworkVisualization(network, dispatch);

        this.add(networkVisualization);
        this.setVisible(true);
    }

    public NetworkVisualizer(Network network, Dispatch dispatch) {
        this(network, dispatch, null);
    }

}
