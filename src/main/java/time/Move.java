package time;

import entities.Entity;
import network.Location;
public class Move extends Event{

    Entity target;
    Location destination;

    public Move(Tick tick, Entity target, Location destination) {
        super(tick);
        this.target = target;
        this.destination = destination;
    }

    @Override
    public void execute() {
        target.setLocation(destination);
    }




}
