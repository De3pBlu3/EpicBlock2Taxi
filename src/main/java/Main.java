import simulation.Simulation;

public class Main {

    private static final String NETWORK_DATA_PATH = "src/main/csv/network_connections.csv";
    private static final String NETWORK_LAYOUT_PATH = "src/main/csv/network_points.csv";

    public static void main(String[] args) {

        Simulation simulation = Simulation.getInstance(
                15,
                7,
                150,
                0.1,
                NETWORK_DATA_PATH,
                NETWORK_LAYOUT_PATH
        );

        simulation.start();
    }

}
