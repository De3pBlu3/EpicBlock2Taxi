package entities;

import data_structures.network.Location;

import javax.swing.ImageIcon;
import java.awt.Image;

import static misc.Util.randInt;
import static misc.Util.randomRegistrationString;

/**
 * Taxi class
 */
public sealed class Taxi extends Vehicle permits ElectricTaxi, LimoTaxi, SportsTaxi {

    private double runningTotal = randInt(0, 99) / 100.0;

    public Taxi(int size, String registrationNumber, Location loc) {
        super(size, registrationNumber, loc);
    }

    public Taxi(int size, Location loc) {
        super(size, randomRegistrationString(), loc);
    }

    public void pay(double moola) {
        this.getDriver().pay(moola);
    }

    public void updateRunningTotal(double money) {
        this.runningTotal += money;
    }

    public void pay() {
        this.pay(runningTotal + this.getVehicleCharge());
        this.getDriver().incrementTrips();
        this.runningTotal = randInt(0, 99) / 100.0;
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
