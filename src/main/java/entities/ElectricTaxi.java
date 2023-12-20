package entities;

import data_structures.network.Location;

import javax.swing.ImageIcon;
import java.awt.Image;

public final class ElectricTaxi extends Taxi {

    public ElectricTaxi( Location loc) {
        super(2, loc);
    }

    @Override
    public Image getImage() {
        return new ImageIcon("src/main/png/electric_taxi.png").getImage();
    }

    @Override
    public int getImageWidth() {
        return 38;
    }

    @Override
    protected int getVehicleCharge() {
        return 5;
    }
}
