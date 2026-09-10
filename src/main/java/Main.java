import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Display;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    //нужна ли поимка ошиюки
    public static void main(String[] args) throws IOException {
        Map map = new Map();
        map.initActions();

        Terminal terminal = TerminalBuilder.builder().build();

        Simulation simulation = new Simulation(map, terminal);
        simulation.render(map);

        List<Herbivore> herbivores = map.getHerbivores();

        while (true) {
            for (Herbivore herbivore : herbivores) {
                LinkedList<Coordinates> path = herbivore.getPathAlgoritmBfs(herbivore, map);

                if (!path.isEmpty()) {
                    simulation.nextTurn(path, herbivore);
                }
            }

            simulation.render(map);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
