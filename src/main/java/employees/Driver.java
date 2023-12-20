package employees;

import data_structures.lists.DynamicArray;
import other.Util;

@SuppressWarnings("unused")
public final class Driver {

    private final String firstName;
    private final String lastName;
    private final int age;
    private final Wallet wallet;
    private final DynamicArray<Double> ratings = new DynamicArray<>();
    private double rating = Util.randInt(0, 5);
    private int tripsComplete = 0;


    public Driver(String fullName, int age) {
        String[] split = fullName.split(" ");

        this.firstName = split[0];
        this.lastName = split[1];
        this.age = age;
        this.wallet = new Wallet(this.getName());
    }

    public Driver(String fullName) {
        this(fullName, Util.randInt(18, 70));
    }

    public Driver() {
        this(Util.randomName(), Util.randInt(18, 70));
    }

    public double getBalance() {
        return this.wallet.getBalance();
    }

    public void pay(double moola) {
        this.wallet.addMoola(moola);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public double getRating() {
        return this.rating;
    }

    public int getTripsComplete() {
        return this.tripsComplete;
    }

    public void incrementTrips() {
        this.tripsComplete++;
    }

    /**
     * Updates the drivers average rating.
     *
     * @param rating Rating out of 5.
     */
    public void rate(double rating) {
        if (rating < 0 || rating > 5)
            throw new IllegalArgumentException("Driver rating must be a double ranging from 1 to 5 (inclusive)");

        this.ratings.append(rating);

        double sum = 0;
        for (Double r: this.ratings) {
            sum += r;
        }

        this.rating = sum / this.ratings.length();
    }

    @Override
    public String toString() {
        return "Driver['" + this.getName() + "', " + this.age + ']';
    }

}