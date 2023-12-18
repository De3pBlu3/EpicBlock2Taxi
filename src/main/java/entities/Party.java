package entities;

import network.Location;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Party class
 */
@SuppressWarnings("unused")
public final class Party extends Entity {

    private final static int IMG_HEIGHT = 30;
    private final static int IMG_WIDTH = 25;

    private String username;
    private int count;
    private Location destination;
    private Taxi assignedTaxi;
    private boolean assigned = false;

    public Party(int count, String username, Location loc, Location destination) {
        super(loc);
        this.username = username;
        this.count = count;
        this.destination = destination;
    }

    public Party(String username, Location loc, Location destination) {
        super(loc);
        this.username = username;
        this.count = 1;
        this.destination = destination;
    }

    public Party(Location loc) {
        super(loc);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        throwErrorIfLessThanOne(count, "count");
        this.count = count;
    }

    public Location getDestination() {
        return this.destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public boolean isAssigned() {
        return this.assigned;
    }

    public void setAssigned(boolean value) {
        this.assigned = value;
    }

    @Override
    public Image getImage() {
        return new ImageIcon("src/main/png/user.png").getImage();
    }

    @Override
    public int getImageHeight() {
        return 21;
    }

    @Override
    public String toString() {
        return "Party['" + this.username + "', " + this.count + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Party party) {
            return (party.getLocation() == this.getLocation());
        }

        return false;
    }
}
