
package time;

import entities.Party;
import network.Network;
import dispatch.Dispatch;
import entities.Taxi;
import network.Node;
import network.Location;

import java.util.Arrays;

public class Scheduler {
    // will be used to schedule events based on weights
    // (origin node) taxi --- 5 --- node 1 ---- 2 ---- node 2
    // tick 0: create move event for taxi to node 1
    // tick 5: create move event for taxi to node 2
    // tick 7: taxi arrives at node 2

    static Timeline timeline;
    static Network network;
    static Dispatch dispatch;

    public Scheduler(Timeline timeline, Network network, Dispatch dispatch) {
        Scheduler.timeline = timeline;
        Scheduler.network = network;
        Scheduler.dispatch = dispatch;

    }

    private static void scheduleMove(Taxi taxi, Node destination) {
        Node[] x = network.findPath( (Node) taxi.getLocation().getCurrentNetLocation(), destination);
        for (int i = 0; i < x.length; i++) {
            try {
                timeline.extendTicks(x[i].getEdge(x[i+1]).getWeight());
                new Move(timeline.getCurrentTick(), taxi, new Location(x[i+1]));
            }
            catch (ArrayIndexOutOfBoundsException e) {
                // do nothing, we're at the end of the path
            }
        }

        taxi.setCurrentPath(x);
    }

    public static void scheduleJourney(Taxi taxi, Party party, Node destination) {
        int originalTick = timeline.getCurrentTick().getTickNumber();

        // trip to party
        Node partyLocation = party.getNode();
        scheduleMove(taxi, partyLocation);

        // pick up
        new Pickup(timeline.getCurrentTick(), taxi, party, dispatch);
        timeline.extendTicks(1);

        // trip to destination
        scheduleMove(taxi, destination);

        // drop off
        new Dropoff(timeline.getCurrentTick(), taxi, party, dispatch);
        timeline.extendTicks(1);
        timeline.setCurrentTick(originalTick);


    }

}
