package entities;

import data_structures.network.Location;

import javax.swing.ImageIcon;
import java.awt.Image;

public final class LimoTaxi extends Taxi {

    public LimoTaxi(Location loc) {
        super(3, loc);
    }

    @Override
    public Image getImage() {
        return new ImageIcon("src/main/png/limo_taxi.png").getImage();
    }

    @Override
    public int getImageWidth() {
        return 55;
    }

    @Override
    protected int getVehicleCharge() {
        return 100;
    }

}
