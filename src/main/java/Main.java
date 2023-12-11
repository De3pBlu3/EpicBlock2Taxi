import dispatch.Dispatch;
import entities.Party;
import lists.DynamicArray;
import network.*;
import other.*;
import entities.Taxi;

import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {

        Introduction.showHeader();
        Introduction.showDescription();
        Util.sleep(1);

        Network network = new Network();

        // Process CSV file, returns list of all possible locations
        DynamicArray<String> locationNames = CSVReader.processData(
                "src/main/csv/network_data.csv",
                network
        );

        // For displaying menu -> using list.forEach(consumer)
        Consumer<String> consumer = (str) -> {
            int index = locationNames.indexOf(str);
            String label = String.format("%02d", index + 1);

            System.out.println("│ " + label + ") " + str);
        };

        System.out.println(network.getEdges());

        // Display menu
        System.out.println("LOCATIONS");
        System.out.println("=".repeat(25));
        locationNames.forEach(consumer);
        System.out.println();

        // Get current user location
        int menuOption1 = Util.getIntInput(
                "Enter your current location (1-" + locationNames.size() + "): ",
                1,
                locationNames.size(),
                "Please enter a valid option"
        );

        // Option number to string
        String currentLocation = locationNames.get(menuOption1 - 1);

        // Get where they want to go
        int menuOption2;
        while (true) {
            menuOption2 = Util.getIntInput(
                    "Enter your destination (1-" + locationNames.size() + "): ",
                    1,
                    locationNames.size(),
                    "Please enter a valid option"
            );

            if (menuOption2 == menuOption1) {
                Util.print(Util.Color.RED, "Um... you're already here");
                continue;
            }
            break;
        }

        // Option number to string
        String destination = locationNames.get(menuOption2 - 1);

        // Init mister dispatch
        Dispatch dispatch = new Dispatch();

        // Add 30 taxis to the map 'randomly'
        for (int i = 0; i < 30; i++) {
            int randomSize = Util.randInt(1, 3);

            // Sorry this is kinda awkward; I'm just putting them at random locations for now
            Node node = network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1)));
            Location loc = new Location(node);

            Taxi taxi = new Taxi(randomSize, 1, loc);
            taxi.setNode(node);  // For later use in main
            node.addOccupant(taxi);  // Add taxi to map!
            dispatch.registerVehicle(taxi);  // Add them to mister dispatch list thanks dispatch guy what a great guy
        }

        // Ask user how many in their party
        int partyCount = Util.getIntInput(
                "How many people are you riding with? (excluding you): ",
                0,
                5,
                "Sorry! 5 is the max. capacity :("
        );

        // Create mister party
        Node partyNode = network.getNode(currentLocation);
        Location userLoc = new Location(partyNode);
        Party party = new Party(partyCount + 1, userLoc);
        party.setNode(partyNode);
        partyNode.addOccupant(party);

        System.out.println("\nLocating nearest taxis for you...\n");

        // Find taxis
        DynamicArray<String> taxiRegistrationNumbersInRange = dispatch.testGetVehiclesInRange(userLoc, 20);
        DynamicArray<Taxi> appropriateTaxis = new DynamicArray<>();

        for (String taxiReg : taxiRegistrationNumbersInRange) {
            dispatch.getVehicleFromReg(taxiReg).ifPresent(
                    (v) -> {
                        if (v.getCapacity() >= partyCount)
                            appropriateTaxis.append((Taxi) v);
                    }
            );
        }

        if (appropriateTaxis.isEmpty()) {
            Util.print(Util.Color.RED, "Sorry! No taxi's available right now. Please try again later.");
            System.exit(0);  // Too bad sorry user; bye bye
        }

        // If more than 1 appropriate taxi, um... they can have a random one
        Taxi taxi = appropriateTaxis.get(Util.randInt(0, appropriateTaxis.size() - 1));
        Node taxiNode = taxi.getNode();

        // Only travels if not in party's node already
        if (!taxiNode.equals(partyNode)) {
            Node[] pathToParty = network.findPath(taxiNode, partyNode);

            for (Node node : pathToParty) {
                taxi.getNode().removeOccupant(taxi);  // Swap current node with next node
                taxi.setNode(node);
            }
        }

        // Fancy CLI graphics

        Util.print(Util.Color.YELLOW, "A taxi is on the way!\n" + Util.Color.NONE.getValue());

        int eta1 = Util.randInt(4, 10);

        System.out.println("Pickup in: ");
        for (int i = eta1; i > -1; i--) {
            System.out.print(Util.Color.YELLOW.getValue() + i + " sec(s)\r");
            Util.sleep(1);
        }

        Util.print(Util.Color.GREEN, "Taxi has arrived!\n");
        Util.sleep(1);

        Node[] pathToDestination = network.findPath(currentLocation, destination);

        for (Node node : pathToDestination) {
            taxi.getNode().removeOccupant(taxi);
            party.getNode().removeOccupant(party);
            taxi.setNode(node);
            party.setNode(node);
        }

        int eta2 = Util.randInt(4, 10);

        System.out.println("Arriving at your destination in:");
        for (int i = eta2; i > -1; i--) {
            System.out.print(Util.Color.YELLOW.getValue() + i + " sec(s)\r");
            Util.sleep(1);
        }

        Util.print(Util.Color.GREEN, "You have arrived!\n");

        // Charge them!
        int cost = Util.randInt(1, 5000);
        System.out.println("That journey cost you " + Util.Color.RED.getValue() + "€" + cost + Util.Color.NONE.getValue());
        Util.sleep(2);
        System.out.println("However, your driver was feeling nice, so he let you off for free :D");

        Util.sleep(2);
        System.out.println("\nThank you for ordering a Taxi with us! We hope to see you again soon.");
    }

}
