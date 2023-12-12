
package time;

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

    Timeline timeline;
    Network network;
    Dispatch dispatch;

    public Scheduler(Timeline timeline, Network network, Dispatch dispatch) {
        this.timeline = timeline;
        this.network = network;
        this.dispatch = dispatch;

}
