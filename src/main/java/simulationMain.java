import entities.*;
import lists.DynamicArray;
import network.Location;
import network.Network;
import dispatch.Dispatch;
import network.Node;
import other.CSVReader;
import other.Util;
import time.PartyRequestTaxi;
import time.Timeline;
import time.Scheduler;
import visuals.NetworkLayout;
import visuals.NetworkVisualizer;

public class simulationMain {

    static Network network = new Network();
    static Dispatch dispatch = new Dispatch();
    static Timeline timeline = new Timeline();
    static Scheduler scheduler = new Scheduler(timeline, network, dispatch);

    static String[] networkLocations = {
            "ISE Building, 90, 100",
            "Dromroe Village, 350, 80",
            "Cappavilla Village, 550, 95",
            "Stables, 400, 165",
            "Glucksman Library, 200, 250",
            "Killmurray Village, 600, 230",
            "Spar Castletroy, 100, 300",
            "Plassey Village, 310, 340",
            "Apache Pizza, 560, 350",
            "Bank of Ireland, 80, 400",
            "Chicken Shop, 400, 420",
            "Troy/Groody Village, 95, 460",
            "Subway, 520, 470"
    };

    public static void init() {
        int numParties = 12;
        int numTaxis = 5;

        DynamicArray<String> locationNames = CSVReader.processData("src/main/csv/network_data.csv", network);

        for (int i = 0; i < numParties; i++) {

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

        for (int i = 0; i < numTaxis; i++) {
            int partyCount = Util.randInt(0, 5);
            Node partyNode = network.getNode(locationNames.getRandom());
            Location partyLoc = new Location(partyNode);
            Location destLoc = new Location(network.getNode(locationNames.getRandom()));

            while (destLoc == partyLoc) {
                destLoc = new Location(network.getNode(locationNames.getRandom()));
            }

            Party party = new Party(partyCount + 1, "username_" + i, partyLoc, destLoc);
            dispatch.registerParty(party);

            party.setNode(partyNode);
            partyNode.addOccupant(party);
        }

    }

    public static void main(String[] args) {

        init();

        NetworkLayout layout = new NetworkLayout(network, networkLocations);
        NetworkVisualizer netVis = new NetworkVisualizer(network, dispatch, layout);

        timeline.extendTicks(150);
        System.out.println("Timeline length: " + timeline.getLength());

        // randomly over 150 ticks add parties to map
        for (Party party : dispatch.getAllParties()) {
            timeline.setCurrentTick(Util.randInt(0, 150));
            new PartyRequestTaxi(timeline.getCurrentTick(), party, dispatch);
        }

        // LOOP THROUGH ALL TICKS
        timeline.appendTick();
        timeline.setCurrentTick(0);

        for (int i = 0; i < timeline.getLength(); i++){
            timeline.setCurrentTick(i);
            timeline.getCurrentTick().executeEvents();

            dispatch.getAllVehicles().forEach(Vehicle::printTableRow);
            System.out.println();

            netVis.repaint();
            Util.sleep(0.25);
        }

        Util.print(Util.Color.GREEN, "Simulation finished!");

    }

}
