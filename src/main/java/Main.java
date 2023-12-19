import simulation.Simulation;

public class Main {

    public static void main(String[] args) {
        Simulation simulation = new Simulation(
                15,
                7,
                150
        );

        simulation.start();
    }

}
