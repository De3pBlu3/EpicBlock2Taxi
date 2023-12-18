import entities.Party;
import entities.Taxi;
import lists.DynamicArray;
import network.Edge;
import network.Location;
import network.Network;
import dispatch.Dispatch;
import network.Node;
import other.CSVReader;
import other.Util;
import time.PartyRequestTaxi;
import time.Timeline;
import time.Scheduler;
import visuals.NetworkVisualizer;

import javax.swing.*;

public class simulationMain {

    static Network network = new Network();
    static Dispatch dispatch = new Dispatch();
    static Timeline timeline = new Timeline();
    static Scheduler scheduler = new Scheduler(timeline, network, dispatch);

    public static void init() {
        int amountoftaxis = 5;
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
            Party party = new Party(partyCount + 1, "username"+ i, partyLoc, destLoc);
            dispatch.registerParty(party);

            party.setNode(Partynode);
            Partynode.addOccupant(party);
        }

    }

    public static void main(String[] args) {

        init();

        NetworkVisualizer netVis = new NetworkVisualizer(network, dispatch);

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

//            System.out.printf(
//                    "%-15s %-35s %-15n %-15s %-15s %-15s %-15s %-15s %n%s%n",
//                    "Registration", "Location", "Count", "Capacity", "Occupied", "Party", "Destination",
//                    "=".repeat(145)
//            );

            dispatch.getAllVehicles().forEach(
                    (vehicle) -> System.out.println(vehicle.asTableRow())
            );

            System.out.println();
            netVis.repaint();
            Util.sleep(0.25);



        }

        System.out.println("Simulation finished!");

    }

}
