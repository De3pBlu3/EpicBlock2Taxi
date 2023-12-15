import entities.Party;
import entities.Taxi;
import lists.DynamicArray;
import network.Location;
import network.Network;
import dispatch.Dispatch;
import network.Node;
import other.CSVReader;
import other.Util;
import time.Timeline;
import time.Scheduler;

public class simulationMain {

    static Network network = new Network();
    static Dispatch dispatch = new Dispatch();
    static Timeline timeline = new Timeline();
    static Scheduler scheduler = new Scheduler(timeline, network, dispatch);

    public static void init() {
        int amountoftaxis = 10;
        int amountofparties = 10;

        DynamicArray<String> locationNames = CSVReader.processData(
                "src/main/csv/network_data.csv",
                network
        );

        for (int i = 0; i < amountoftaxis; i++) {
            int randomSize = Util.randInt(1, 3);

            // Sorry this is kinda awkward; I'm just putting them at random locations for now
            Node node = network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1)));
            Location loc = new Location(node);

            Taxi taxi = new Taxi(randomSize, 1, loc);
            taxi.setNode(node);  // For later use in main
            node.addOccupant(taxi);  // Add taxi to map!
            dispatch.registerVehicle(taxi);  // Add them to mister dispatch list thanks dispatch guy what a great guy
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

        // find parties
        for (Party party : dispatch.getAllParties()) {
            scheduler.scheduleParty(party);
        }


        timeline.appendTick();
        timeline.setCurrentTick(0);
        for (int i = 0; i < timeline.getLength(); i++) {
            System.out.println("Tick: " + i);
            timeline.getCurrentTick().executeEvents();
            timeline.getCurrentTick().printEvents();
            timeline.nextTick();
        }


    }

}
