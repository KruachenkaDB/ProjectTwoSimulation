package action;

import entity.Herbivore;
import entity.Predator;
import map.Map;

import java.util.List;

public class MoveCreaturesAction implements TurnAction {
    @Override
    public void execute(Map map, int turnCounter) {
        List<Herbivore> herbivores = map.getHerbivores();

        for (Herbivore herbivore : herbivores) {
            herbivore.makeMove(map);
        }

        List<Predator> predators = map.getPredators();

        for (Predator predator : predators) {
            predator.makeMove(map);
        }
    }
}
