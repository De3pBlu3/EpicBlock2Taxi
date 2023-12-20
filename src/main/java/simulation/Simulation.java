package simulation;

import dispatch.Dispatch;
import entities.*;
import lists.DynamicArray;
import network.Location;
import network.Network;
import network.Node;
import other.DataProcessor;
import other.Util;
import time.PartyRequestTaxi;
import time.Scheduler;
import time.Timeline;
import visuals.NetworkLayout;
import visuals.NetworkVisualizer;


public class Simulation {

    private final Timeline timeline;
    private final Network network;
    private final Dispatch dispatch;
    private final DynamicArray<String> locationNames;
    private final NetworkLayout layout;
    private final int partyCount;
    private final int taxiCount;
    private final double tickTimeout;
    private boolean userPause = false;
    private final boolean showWeights;

    private static Simulation single_instance = null;

    private Simulation(
        int numberOfParties,
        int numberOfTaxis,
        int timelineLength,
        double tickTimeout,
        String networkConnectionDataFile,
        String networkPointDataFile,
        boolean showWeights
    ) {
        this.partyCount = numberOfParties;
        this.taxiCount = numberOfTaxis;

        this.timeline = new Timeline();
        this.timeline.extendTicks(timelineLength);

        this.tickTimeout = tickTimeout;
        this.network = new Network();
        this.dispatch = new Dispatch();
        this.showWeights = showWeights;

        DataProcessor.processNetworkConnections(networkConnectionDataFile, this.network);
        this.layout = DataProcessor.createNetworkPointLayout(networkPointDataFile, this.network);

        this.locationNames = this.layout.getLocationNames();

        this.addTaxisToMap();
        this.addPartiesToMap();

        Scheduler.init(this.timeline, this.network, this.dispatch);

    }

    public static Simulation getInstance(
            int numberOfParties,
            int numberOfTaxis,
            int timelineLength,
            double tickTimeout,
            String networkConnectionDataFile,
            String networkPointDataFile,
            boolean showWeights
    ) {
        if (single_instance == null)
            single_instance = new Simulation(
                    numberOfParties,
                    numberOfTaxis,
                    timelineLength,
                    tickTimeout,
                    networkConnectionDataFile,
                    networkPointDataFile,
                    showWeights
            );

        return single_instance;
    }

    public static Simulation getInstance() {
        if (single_instance == null){
            throw new IllegalStateException("Simulation has not been initialised");
        }
        return single_instance;
    }

    private void addTaxisToMap() {

        for (int i = 0; i < this.taxiCount; i++) {

            // Sorry this is kinda awkward; I'm just putting them at random locations for now
            Node node = network.getNode(locationNames.getRandom());
            Location loc = new Location(node);

            Taxi taxi = switch (Util.randInt(1, 3)) {
                case 1 -> new SportsTaxi(loc);
                case 2 -> new ElectricTaxi(loc);
                case 3 -> new LimoTaxi(loc);
                default -> new Taxi(1, loc);
            };

            taxi.setNode(node);  // For later use in main
            node.addOccupant(taxi);  // Add taxi to map!
            dispatch.registerVehicle(taxi);  // Add them to mister dispatch list thanks dispatch guy what a great guy
            dispatch.testAddToMap(taxi.getRegistrationNumber(), taxi.getLocation());  // Add them to the map thanks dispatch guy what a great guy
        }

    }

    private void addPartiesToMap() {

        for (int i = 0; i < this.partyCount; i++) {

            int partyCount = Util.randInt(0, 5);
            Node partyNode = this.network.getNode(locationNames.getRandom());

            Location partyLoc = new Location(partyNode);
            Location destLoc = new Location(network.getNode(locationNames.getRandom()));

            while (destLoc == partyLoc) {
                destLoc = new Location(network.getNode(locationNames.getRandom()));
            }

            Party party = new Party(partyCount + 1, "username_" + i, partyLoc, destLoc);
            this.dispatch.registerParty(party);

            party.setNode(partyNode);
            partyNode.addOccupant(party);
        }

    }

    public void start() {

        NetworkVisualizer visualizer = new NetworkVisualizer(this.network, this.layout, this.showWeights);
        visualizer.start();

        System.out.println("Timeline length: " + this.timeline.getLength() + '\n');

        // randomly over 150 ticks add parties to map
        for (Party party : this.dispatch.getAllParties()) {
            this.timeline.setCurrentTick(Util.randInt(0, this.timeline.getLength())-1);
                new PartyRequestTaxi(this.timeline.getCurrentTick(), party, this.dispatch);
            }


        // LOOP THROUGH ALL TICKS
        this.timeline.appendTick();
        this.timeline.setCurrentTick(0);

        for (int i = 0; i < timeline.getLength(); i++){
            if (userPause) {
                Util.print(Util.Color.YELLOW, "Simulation paused.");
                Util.print(Util.Color.YELLOW, "Press space or the 'Resume' button to continue.");

                // read a string and throw it away
                while (userPause) {
                    System.out.print("");
                }
            }

            this.timeline.setCurrentTick(i);
            this.timeline.getCurrentTick().executeEvents();

            Util.print(Util.Color.CYAN, "Tick: ");
            Util.print(Util.Color.YELLOW, this.timeline.getCurrentTick().getTickNumber());
            System.out.println('\n' + "=".repeat(145));
            System.out.printf(
                    "%-15s%-35s%-15s%-15s%-15s%-15s%-15s%n%s%n",
                    "Registration", "Location", "Count", "Capacity", "Occupied", "Party", "Destination",
                    "=".repeat(145)
            );

            this.dispatch.getAllVehicles().forEach(Vehicle::printTableRow);
            System.out.println();

            visualizer.repaint();
            Util.sleep(this.tickTimeout);
        }

        Util.print(Util.Color.GREEN, "Simulation finished!");
    }

    public void togglePause() {
        userPause = !userPause;
    }

    public boolean isPaused() {
        return userPause;
    }

}
