package entities;

import network.Location;

import javax.swing.ImageIcon;
import java.awt.Image;

public final class SportsTaxi extends Taxi {

    public SportsTaxi(Location loc) {
        super(1, loc);
    }

    @Override
    public Image getImage() {
        return new ImageIcon("src/main/png/sports_taxi.png").getImage();
    }

}
