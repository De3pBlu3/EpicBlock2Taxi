package not_implemented_yet;
import network.NetworkComponent;

// Added this to get rid of those annoying
// "cannot resolve symbol..." errors
public class Location {
    private float x;
    private float y;
    private NetworkComponent currentNetLocation;


    public Location(NetworkComponent currentNetLocation) {
        this.currentNetLocation = currentNetLocation;
//        this.x = currentNetLocation.getX(); // TODO implement getX() and getY() in NetworkComponent
//        this.y = currentNetLocation.getY();
    }

    public NetworkComponent getCurrentNetLocation() {
        return this.currentNetLocation;
    }

    public float[] getCoordinates() {
        return new float[] {this.x, this.y};
    }

}
