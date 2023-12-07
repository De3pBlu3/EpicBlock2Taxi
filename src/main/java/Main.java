import dispatch.Dispatch;
import entities.Party;
import lists.DynamicArray;
import network.*;
import other.*;
import entities.Taxi;

import java.util.List;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        Introduction.showHeader();
        Introduction.showDescription();
        Util.sleep(1);

        // Init network
        Network network = new Network();

        // Process CSV file, returning list of all possible locations
        DynamicArray<String> locations = CSVReader.processData(
                "src/main/csv/network_data.csv",
                network
        );

        // For displaying menu -> using list.forEach(consumer)
        Consumer<String> consumer = (str) -> {
            int index = locations.indexOf(str);
            String label = String.format("%02d", index + 1);

            System.out.println("│ " + label + ") " + str);
        };

        // Display menu
        System.out.println("LOCATIONS");
        System.out.println("━".repeat(30));
        locations.forEach(consumer);
        System.out.println();

        // Get current user location
        int menuOption1 = Util.getIntInput(
                "Enter your current location (1-" + locations.size() + "): ",
                1,
                11,
                "Please enter a valid option"
        );

        String currentLocation = locations.get(menuOption1 - 1);

        // Get where they want to go
        int menuOption2 = Util.getIntInput(
                "Enter your destination (1-" + locations.size() + "): ",
                1,
                11,
                "Please enter a valid option"
        );

        String destination = locations.get(menuOption2 - 1);

        // Init mister dispatch :3
        Dispatch dispatch = new Dispatch();

        // Add some random taxis to the map
        for (int i = 0; i < 20; i++) {
            int randomCapacity = Util.randInt(2, 5);

            String reg = Util.randomRegistrationString();

            // Sorry this is kinda awkward
            Node node = network.getNode(locations.get(Util.randInt(0, locations.size() - 1)));
            Location loc = new Location(node);

            Taxi taxi = new Taxi(randomCapacity, reg, 1, loc);
            node.addOccupant(taxi);  // Add taxi to map! (Hopefully)
            dispatch.registerVehicle(taxi);  // Add them to mister dispatch list thanks dispatch guy what a great guy

        }

        // Ask user how many in their party :3333333333
        int partyCount = Util.getIntInput(
                "How many people are you riding with?: ",
                0,
                5,
                "Sorry! 5 is the max. capacity :("
        );

        // Create mister party
        Location userLoc = new Location(network.getNode(currentLocation));
        Party party = new Party(partyCount + 1, userLoc);

        // Find taxis
        List<String> taxiRegistrationNumbersInRange = dispatch.testGetVehiclesInRange(userLoc, 2);
        DynamicArray<Taxi> appropriateTaxis = new DynamicArray<>();

        for (String taxiReg : taxiRegistrationNumbersInRange) {
            Taxi taxi = dispatch.getVehicleFromReg(taxiReg);
            if (taxi.getCapacity() >= partyCount)
                appropriateTaxis.append(taxi);

        }

        if (appropriateTaxis.isEmpty()) {
            Util.print(Util.Color.RED, "Sorry! No taxi waxies today D:");
            System.exit(0);  // Too bad sorry user; bye bye
        }

        // If more than 1 appropriate taxi, um... they can have the first one anyway :D
        Taxi taxi = appropriateTaxis.get(0);

        // Time to find the fastest path I guess
        Node[] fastestPath = network.findPath(currentLocation, destination);

        // Print out fastest path (i think its kinda maybe working?)
        for (Node n: fastestPath) {
            System.out.println(n);
        }
    }

}
