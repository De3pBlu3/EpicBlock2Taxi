import simulation.Simulation;

public class Main {

    public static void main(String[] args) {

        Simulation simulation = Simulation.getInstance(
                15,
                7,
                150,
                0.1
        );

        simulation.start();
    }

}
