import javax.swing.*;

public class Main {
    public static void main(String[] args){
        Map map = new Map();

        Simulation simulation = new Simulation(map);
        simulation.initActions();

        Timer timer = new Timer(500, e -> {
            simulation.startSimulation();
        });
        timer.start();
    }
}
