import dispatch.Dispatch;
import entities.Taxi;
import lists.DynamicArray;
import network.Location;
import network.Network;
import network.Node;
import other.CSVReader;
import other.Util;
import time.Scheduler;
import time.Timeline;

public class SimMain {
    // static class SimMain

    static Network network = new Network();
    static Dispatch dispatch = new Dispatch();
    static Timeline timeline = new Timeline();
    static Scheduler scheduler = new Scheduler(timeline, network, dispatch);


    public static void init(){
        // init simulation
        // read csv
        // create network
        // create dispatch
        // create timeline
        // create scheduler

        int amountOfTaxis = 10;
        int amountOfParties = 10;



        // Process CSV file, add to network
        DynamicArray<String> locationNames = CSVReader.processData("src/main/csv/network_data.csv", network);

        // Add amount of taxis to the map 'randomly'
        for (int i = 0; i < amountOfTaxis; i++) {
            int randomSize = Util.randInt(1, 3);
            Node node = network.getNode(locationNames.get(Util.randInt(0, locationNames.size() - 1)));
            Location loc = new Location(node);
            Taxi taxi = new Taxi(randomSize, 1, loc);
            node.addOccupant(taxi);  // Add taxi to map!
            dispatch.registerVehicle(taxi);  // Add them to mister dispatch list thanks dispatch guy what a great guy
        }



    }

    public static void run(){
        // run simulation
    }

    public static void main(String[] args) {
        init();
        run();
    }
}
