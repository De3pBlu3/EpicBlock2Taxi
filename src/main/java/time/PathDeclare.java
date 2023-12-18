package time;


import entities.Taxi;
import network.Node;

public class PathDeclare extends Event {
    private final Taxi taxi;
    private final Node destination;
    private final Node[] path;

    public PathDeclare(Tick tick, Taxi taxi, Node destination, Node[] path) {
        super(tick);
        this.taxi = taxi;
        this.destination = destination;
        this.path = path;

    }

    @Override
    public void execute() {
        taxi.setCurrentPath(path);
    }
}
