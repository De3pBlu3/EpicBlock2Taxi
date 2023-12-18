package entities;

import network.Location;

import javax.swing.ImageIcon;
import java.awt.Image;

import static other.Util.randomRegistrationString;

/**
 * Taxi class
 */
public sealed class Taxi extends Vehicle permits ElectricTaxi, LimoTaxi, SportsTaxi {

    public Taxi(int size, String registrationNumber, Location loc) {
        super(size, registrationNumber, loc);
    }

    public Taxi(int size, Location loc) {
        super(size, randomRegistrationString(), loc);
    }

    @Override
    public Image getImage() {
        return new ImageIcon("src/main/png/taxi.png").getImage();
    }

    @Override
    public int getImageWidth() {
        return 35;
    }

    @Override
    public int getImageHeight() {
        return 18;
    }

}
