import misc.Util;
import simulation.Simulation;
import misc.Introduction;

public class Main {

    private static final String NETWORK_DATA_PATH = "src/main/csv/network_connections.csv";
    private static final String NETWORK_LAYOUT_PATH = "src/main/csv/network_points.csv";

    private static double[] getInputs() {

        int partyCount = Util.getIntInput(
                "Enter number of parties: ",
                1,
                50,
                "Please enter an integer",
                "There must be at least 1 party on the map",
                "That's too many!"
        );

        int taxiCount = Util.getIntInput(
                "Enter number of taxis: ",
                1,
                50,
                "Please enter an integer",
                "There must be at least 1 taxi on the map",
                "That's too many!"
        );

        double tickTimeout = Util.getDoubleInput(
                "Enter the timeout (in seconds) per tick: ",
                0.01,
                10,
                "Please enter a number",
                "Minimum timeout value is 0.01 seconds",
                "Maximum timeout value is 10 seconds"
        );

        int timelineLength = Util.getIntInput(
                "Enter initial timeline length (in ticks): ",
                50,
                10_000,
                "Please enter an integer",
                "Minimum timeline length value is 50 ticks",
                "Maximum timeline length value is 10,000 ticks"
        );

        boolean showWeights = Util.getBooleanInput(
                "Would you like to see the connection weights on the map? (y/n): ",
                "Please enter \"y(es)\" or \"no\""
        );

        return new double[]{partyCount, taxiCount, tickTimeout, timelineLength, showWeights ? 1.0 : 0.0};
    }

    public static void main(String[] args) {

        Introduction.showHeader();
        Introduction.showDescription();
        Introduction.showGuide();

        double[] inputs = getInputs();
        int partyCount = (int) inputs[0];
        int taxiCount = (int) inputs[1];
        double tickTimeout = inputs[2];
        int timelineLength = (int) inputs[3];
        boolean showWeights = inputs[4] == 1.0;

        Simulation simulation = Simulation.getInstance(
                partyCount,
                taxiCount,
                timelineLength,
                tickTimeout,
                NETWORK_DATA_PATH,
                NETWORK_LAYOUT_PATH,
                showWeights
        );

        System.out.println();
        Util.waitForEnter("Press enter to start: ");

        simulation.start();
    }

}
