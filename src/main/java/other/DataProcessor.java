package other;

import network.Network;
import network.Node;
import visuals.NetworkLayout;
import visuals.NodeData;

// For processing csv file
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;


public final class DataProcessor {

    private static final String NETWORK_DATA_PATH = "src/main/csv/network_data.csv";
    private static final String NETWORK_LAYOUT_PATH = "src/main/csv/network_layout.csv";

    /**
     * For each line in the given CSV file, the given consumer executes its
     * accept() method.
     *
     * @param filePath File path to CSV file.
     * @param consumer Consumer taking a line argument.
     */
    public static void forEachLine(String filePath, Consumer<String> consumer) {

        String line;

        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            br.readLine();

            while ((line = br.readLine()) != null) {
                consumer.accept(line);
            }

            br.close();

        } catch (IOException e) {
            Util.print(Util.Color.RED, "CSV file could not be read");
        }
    }

    /**
     * Process the network data and adds given nodes and edges
     * to network.
     *
     * @param network Main network object.
     */
    public static void processNetworkData(Network network) {

        forEachLine(NETWORK_DATA_PATH, (line) -> {
            String[] row = line.split(", ");

            String start = row[0];
            String end = row[1];
            int weight = Integer.parseInt(row[2]);

            // Bi-directional connection
            network.addEdge(start, end, weight);
            network.addEdge(end, start, weight);
        });
    }

    /**
     * Creates and returns a network layout
     *
     * @param network Main network object. Needed to access the
     * node references.
     */
    public static NetworkLayout createNetworkLayout(Network network) {

        NetworkLayout layout = new NetworkLayout();

        forEachLine(NETWORK_LAYOUT_PATH, (line) -> {
            String[] mappedArray = line.split(", ");

            Node node = network.getNode(mappedArray[0]);
            int x = Integer.parseInt(mappedArray[1]);
            int y = Integer.parseInt(mappedArray[2]);

            layout.addNodeData(new NodeData(node, x, y));

        });

        return layout;
    }

}
