
package time;

import entities.Party;
import data_structures.network.Network;
import dispatch.Dispatch;
import entities.Taxi;
import data_structures.network.Node;
import data_structures.network.Location;
import misc.Util;

public class Scheduler {
    // will be used to schedule events based on weights
    // (origin node) taxi --- 5 --- node 1 ---- 2 ---- node 2
    // tick 0: create move event for taxi to node 1
    // tick 5: create move event for taxi to node 2
    // tick 7: taxi arrives at node 2

    static Timeline timeline;
    static Network network;
    static Dispatch dispatch;

    public static void init(Timeline timeline, Network network, Dispatch dispatch) {
        Scheduler.timeline = timeline;
        Scheduler.network = network;
        Scheduler.dispatch = dispatch;
    }

    private static void scheduleMove(Taxi taxi, Node destination) {
        Node[] x = network.findPath( (Node) taxi.getLocation().getCurrentNetLocation(), destination);
        for (int i = 0; i < x.length; i++) {
            try {
                int weight = x[i].getEdge(x[i+1]).getWeight();
                taxi.updateRunningTotal(weight);
                timeline.extendTicks(weight);
                new Move(timeline.getCurrentTick(), taxi, new Location(x[i+1]));
            }
            catch (ArrayIndexOutOfBoundsException e) {
                // do nothing, we're at the end of the path
            }
        }
    }

    public static void scheduleJourney(Taxi taxi, Party party, Node destination) {
        int originalTick = timeline.getCurrentTick().getTickNumber();

        // trip to party
        Node partyLocation = party.getNode();
        Node[] pathToParty = network.findPath((Node) taxi.getLocation().getCurrentNetLocation(), partyLocation);

        new PathDeclare(timeline.getCurrentTick(), taxi, partyLocation, pathToParty);
        scheduleMove(taxi, partyLocation);

        // pick up
        new Pickup(timeline.getCurrentTick(), taxi, party, dispatch);
        timeline.extendTicks(1);

        Node[] pathToDestination = network.findPath((Node) taxi.getLocation().getCurrentNetLocation(), destination);
        new PathDeclare(timeline.getCurrentTick(), taxi, destination, pathToDestination);

        // trip to destination
        scheduleMove(taxi, destination);

        // drop off
        new Dropoff(timeline.getCurrentTick(), taxi, party, dispatch);
        taxi.rate(Util.randInt(0, 5));
        taxi.pay();
        timeline.extendTicks(1);
        timeline.setCurrentTick(originalTick);

    }

}
