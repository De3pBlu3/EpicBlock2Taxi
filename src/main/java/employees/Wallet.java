package employees;


@SuppressWarnings("unused")
public final class Wallet {

    private final String name;
    private double balance;

    public Wallet(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public Wallet(String name) {
        this(name, 0);
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addCash(double yummyMoolaUwU) {
        this.balance += yummyMoolaUwU;
    }

}
