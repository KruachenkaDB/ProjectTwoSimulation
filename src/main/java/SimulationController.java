import action.*;
import action.Action;
import config.GameSettings;
import map.GameMap;

import javax.swing.*;
import java.util.List;

public class SimulationController {
    private Timer timer;

    private final SimulationView view;
    private final GameMap gameMap;

    private final List<Action> initActions = List.of(new InitEntitiesAction());
    private final List<Action> turnActions = List.of(new MoveCreaturesAction(), new DecreaseHungerAction(), new SpawnGrassTurnAction());

    public SimulationController(GameMap gameMap) {
        this.view = new SimulationView();
        this.gameMap = gameMap;
    }

    public void start() {
        executeInitActions();

        view.show(gameMap);

        initTimer();

        view.getStartButton().addActionListener(e -> startSimulation());
        view.getPauseButton().addActionListener(e -> pauseSimulation());
        view.getStepButton().addActionListener(e -> nextTurn());
    }

    private void nextTurn() {
        executeTurnActions();

        view.refreshBoard(gameMap);
    }

    private void pauseSimulation() {
        timer.stop();
    }

    private void startSimulation() {
        timer.start();
    }

    void initTimer() {
        timer = new Timer(GameSettings.SIMULATION_DELAY, e -> nextTurn());
    }

    private void executeInitActions() {
        for (Action action : initActions) {
            action.execute(gameMap);
        }
    }

    private void executeTurnActions() {
        for (Action action : turnActions) {
            action.execute(gameMap);
        }
    }
}