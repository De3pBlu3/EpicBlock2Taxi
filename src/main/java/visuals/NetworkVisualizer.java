package visuals;

import entities.Entity;
import entities.Party;
import data_structures.network.Edge;
import data_structures.network.Network;
import data_structures.network.Node;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("FieldCanBeLocal")
class NetworkVisualization extends JPanel {

    private static final Color LIGHT_GRAY = new Color(200, 200, 200);
    private static final Color LIGHTER_GRAY = new Color(211, 211, 211);
    private static final Color DARK_GRAY = new Color(76, 78, 82);
    private static final Color MINT = new Color(180, 217, 192);

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
    private final Network network;
    private final boolean showWeights;

    NetworkVisualization(Network network, boolean showWeights) {
        this.network = network;
        this.showWeights = showWeights;
        this.setBackground(DARK_GRAY);
        this.setFont(this.font);
        this.update();
    }

    public void update() {
        this.updateNet();
        this.repaint();
    }

    private void updateNet() {
        this.nodes = network.getNodesAsArray();
        this.edges = network.getUniqueEdges();
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

        int partyCount = 0;
        int otherCount = 0;

        for (Entity occupant : node.getOccupants()) {

            Image image = occupant.getImage();
            int width = occupant.getImageWidth();
            int height = occupant.getImageHeight();
            int x1, y1;

            if (occupant instanceof Party) {
                x1 = node.getX() - this.nodeRadius - width;
                y1 = node.getY() + this.nodeRadius + (partyCount++ * 20);
            } else {
                x1 = node.getX() + this.nodeRadius;
                y1 = node.getY() + this.nodeRadius + (otherCount++ * 20);
            }

            g.drawImage(image, x1, y1, width, height, null);
        }
    }

    private void drawEdgeConnection(Graphics g, Edge edge) {
        int x1 = edge.getStart().getX();
        int y1 = edge.getStart().getY();

        int x2 = edge.getEnd().getX();
        int y2 = edge.getEnd().getY();

        g.setColor(LIGHTER_GRAY);
        g.drawLine(x1, y1, x2, y2);

        if (!this.showWeights)
            return;

        int xMid = (x1 + x2) / 2;
        int yMid = (y1 + y2) / 2;

        int maxX = Math.max(x1, x2);
        int minX = x2 == maxX ? x1 : x2;

        int maxY = Math.max(y1, y2);
        int minY = y2 == maxY ? y1 : y2;

        int opp = maxY - minY;
        int adj = maxX - minX;

        double angle = (Math.atan((double) opp/adj) * (180/Math.PI));

        // Ignore these irrelevant calculations (to make layout as nice as possible)
        boolean left = maxX == x1 ? (x2 < x1) : (x1 < x2);
        int offsetMultiplicand = left ? 1 : -1;

        int xOffset = offsetMultiplicand;
        int yOffset = offsetMultiplicand;

        int magicMultiplicand = angle > 75 ? 5 : 3;

        if (angle > 25) {
            xOffset *= (int) Math.round((((90 - angle) / 10) * magicMultiplicand) / 10) * 10;
        } else {
            yOffset *= (int) Math.round(((angle / 10) * 10) / 10) * 10;
        }

        g.setColor(MINT);
        g.drawString(String.valueOf(edge.getWeight()), xMid + xOffset, yMid + yOffset);

    }

    @Override
    protected void paintComponent(Graphics g) {

        // Magic rendering method to make quality good :3
        // Eliminates the jaggedness of the text & shapes somehow
        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

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

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class NetworkVisualizer extends JFrame {

    private final static int DEFAULT_WINDOW_WIDTH = 700;
    private final static int DEFAULT_WINDOW_HEIGHT = 500;

    private final int xOffset = 150;
    private final int yOffset = 100;

    private final Simulation simulation;

    public NetworkVisualizer(Network network, NetworkLayout networkLayout, boolean showWeights) {

        this.setTitle("Network Visualization");
        this.setIconImage(new ImageIcon("src/main/png/map_icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.simulation = Simulation.getInstance();

        JButton pauseButton = new JButton("Pause");

        this.applyButtonAttributes(pauseButton);
        this.add(pauseButton, BorderLayout.SOUTH);

        if (networkLayout == null) {

            this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT + 40);

            // Beginning co-ordinates for first node
            int x = 100;
            int y = 100;

            for (Node node : network.getNodesAsArray()) {

                // reset after every 5 nodes
                if (x == DEFAULT_WINDOW_WIDTH) {
                    y += yOffset;  // Move y co-ord to new row
                    x = 100;         // Reset x co-ord to beginning of new row
                }

                node.setX(x);
                node.setY(y);
                x += xOffset;
            }

        } else {

            this.setSize(networkLayout.getMaxX() + 100, networkLayout.getMaxY() + 140);

            for (Node node : networkLayout) {

                int x = node.getX();
                int y = node.getY();

                if (x < 0) {
                    throw new IllegalStateException("X coordinate is out of bounds for network visualisation");
                }

                if (y < 0 ) {
                    throw new IllegalStateException("Y coordinate is out of bounds for network visualisation");
                }

                node.setX(x);
                node.setY(y);
            }

        }

        this.add(new NetworkVisualization(network, showWeights), BorderLayout.CENTER);
    }

    private void applyButtonAttributes(JButton button) {

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(this.getMaximumSize().width, 40));

        button.addActionListener(e -> {
            simulation.togglePause();
            button.setText(simulation.isPaused() ? "Resume" : "Pause");
            button.setBackground(simulation.isPaused() ? Color.LIGHT_GRAY : Color.WHITE);
        });

        button.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_PAUSE)
                    simulation.togglePause();
            }

        });

        this.add(button, BorderLayout.SOUTH);
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            this.requestFocus();
            this.setAlwaysOnTop(true);  // Bring to front
            this.setAlwaysOnTop(false);
        });
    }

    public NetworkVisualizer(Network network) {
        this(network, null, false);
    }

}
