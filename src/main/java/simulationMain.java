import entities.Party;
import entities.Taxi;
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

public class simulationMain {

    static Network network = new Network();
    static Dispatch dispatch = new Dispatch();
    static Timeline timeline = new Timeline();
    static Scheduler scheduler = new Scheduler(timeline, network, dispatch);

    public static void init() {
        int amountoftaxis = 5;
        int amountofparties = 5;

        DynamicArray<String> locationNames = CSVReader.processData(
                "src/main/csv/network_data.csv",
                network
        );

        for (int i = 0; i < amountoftaxis; i++) {
            int randomSize = Util.randInt(1, 3);

            // Sorry this is kinda awkward; I'm just putting them at random locations for now
            Node node = network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1)));
            Location loc = new Location(node);

            Taxi taxi = new Taxi(randomSize, loc);
            taxi.setNode(node);  // For later use in main
            node.addOccupant(taxi);  // Add taxi to map!
            dispatch.registerVehicle(taxi);  // Add them to mister dispatch list thanks dispatch guy what a great guy
            dispatch.testAddToMap(taxi.getRegistrationNumber(), taxi.getLocation());  // Add them to the map thanks dispatch guy what a great guy
        }

        for (int i = 0; i < amountofparties; i++) {
            int partyCount = Util.randInt(0, 5);
            // Create mister party
            Node Partynode = network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1)));
            Location partyLoc = new Location(Partynode);
            Location destLoc = new Location(network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1))));
            while (destLoc == partyLoc) {
                destLoc = new Location(network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1))));
            }
            Party party = new Party(partyCount + 1, partyLoc, "username"+ i, destLoc);
            dispatch.registerParty(party);

            party.setNode(Partynode);
            Partynode.addOccupant(party);
        }

    }

    public static void main(String[] args) {

        init();

        timeline.extendTicks(150);
        System.out.println("Timeline length: " + timeline.getLength());

        // randomly over 100 ticks add parties to map
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

//             PRINT OUT ALL VEHICLES TODO make this observer pattern
            Object[][] table = new String[dispatch.getAllVehicles().length()][];
            for (int x = 0; x < dispatch.getAllVehicles().length(); x++) {
                table[x] = new String[]{
                        dispatch.getAllVehicles().get(x).getRegistrationNumber(),
                        dispatch.getAllVehicles().get(x).getLocation().getCurrentNetLocation().toString(),
                        dispatch.getAllVehicles().get(x).getCount() + "",
                        dispatch.getAllVehicles().get(x).getCapacity() + "",
                        dispatch.getAllVehicles().get(x).getParty() == null ? "False": "True",
                        // if assigned, print party username, else print "none"
                        dispatch.getAllVehicles().get(x).getParty() == null ? "none" :
                                dispatch.getAllVehicles().get(x).getParty().getUsername(),

                        dispatch.getAllVehicles().get(x).getParty() == null ? "none" :
                                dispatch.getAllVehicles().get(x).getDestination().getCurrentNetLocation().toString()
                };
            }

            // print header
            System.out.format("%-15s\t%-35s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%n", "Registration", "Location", "Headcount", "Capacity", "Occupied", "Party", "Destination");
            for (final Object[] row : table) {
            // left justify all columns
                System.out.format("%-15s\t%-35s\t%-15s\t%-15s\t%-15s\t%-15s\t%-15s\t%n", row);
            }


            System.out.println("==================================");
            System.out.flush();

        }


    }

}
