package time;
import entities.Taxi;
import data_structures.network.Location;
public class Move extends Event{

    Taxi target; // parties are static and do not move UNLESS they are in a taxi
    Location destination;

    public Move(Tick tick, Taxi target, Location destination) {
        super(tick);
        this.target = target;
        this.destination = destination;
    }

    @Override
    public void execute() {
        target.MoveVehicleOneStep(destination);
    }




}
