import dispatch.Dispatch;
import entities.Taxi;
import network.Location;
import network.Network;
import time.Timeline;

public class TimelineMain {
    public static void main(String[] args) {
        Network network = new Network();
        network.addEdge("A", "B", 5);
        network.addEdge("B", "C", 5);
        network.addEdge("C", "D", 7);
        network.addEdge("D", "C", 7);
        network.addEdge("A", "C", 5);


        Location location1 = new Location(network.getNode("A"));
        Location location2 = new Location(network.getNode("B"));
        Location location3 = new Location(network.getNode("C"));
        Location location4 = new Location(network.getNode("D"));


        Taxi taxi = new Taxi(4, "ABC123",  1, location1);


        Dispatch dispatch = new Dispatch();
        dispatch.registerVehicle(taxi);
        dispatch.testAddToMap("ABC123", taxi.getLoc());



        Timeline timeline = new Timeline();
        timeline.appendTick();
        timeline.appendTick();
        timeline.appendTick();
        timeline.setCurrentTick(0);
        timeline.getCurrentTick().addEvent(new time.Move(timeline.getCurrentTick(), taxi, location2));
        timeline.nextTick();
        timeline.getCurrentTick().addEvent(new time.Move(timeline.getCurrentTick(), taxi, location3));
        timeline.nextTick();
        timeline.getCurrentTick().addEvent(new time.Move(timeline.getCurrentTick(), taxi, location4));
        System.out.println(timeline);

        timeline.setCurrentTick(0);

        for (int i = 0; i < 3; i++) {
            timeline.getCurrentTick().executeEvents();
            timeline.nextTick();
            System.out.println(taxi.getLoc().getCurrentNetLocation());

        }
    }
}
