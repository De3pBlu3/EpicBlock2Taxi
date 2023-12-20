package data_structures.network;

@SuppressWarnings("unused")
public class Location {

    private float x;
    private float y;
    private final NetworkComponent currentNetLocation;

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

    @Override
    public String toString() {
        return currentNetLocation.toString();
    }

}
