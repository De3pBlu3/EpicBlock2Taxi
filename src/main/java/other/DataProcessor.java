package other;

import network.Network;
import network.Node;
import visuals.NetworkLayout;

// For processing csv file
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;


public final class DataProcessor {

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
     * @param filePath File path to CSV file.
     * @param network Main network object.
     */
    public static void processNetworkConnections(String filePath, Network network) {

        forEachLine(filePath, (line) -> {
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
     * @param filePath File path to CSV file.
     * @param network Main network object. Needed to access the
     * node references.
     */
    public static NetworkLayout createNetworkPointLayout(String filePath, Network network) {

        NetworkLayout layout = new NetworkLayout();

        forEachLine(filePath, (line) -> {
            String[] mappedArray = line.split(", ");

            Node node = network.getNode(mappedArray[0]);
            int x = Integer.parseInt(mappedArray[1]);
            int y = Integer.parseInt(mappedArray[2]);
            node.setX(x);
            node.setY(y);

            layout.addNode(node);

        });

        return layout;
    }

}
