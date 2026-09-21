import action.*;
import config.GameSettings;
import entity.*;
import map.Coordinates;
import map.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private static final String GAME_NAME = "Simulation";
    private static final String EMPTY_CELL = "=";

    private int turnCounter = 0;

    private final JLabel[][] cells = new JLabel[GameSettings.MAP_WIDTH][GameSettings.MAP_HEIGHT];

    private Timer timer;

    private JButton pauseButton;
    private JButton startButton;

    private final Map map;

    private final List<InitAction> initActions = new ArrayList<>();
    private final List<TurnAction> turnActions = new ArrayList<>();

    public Simulation(Map map) {
        this.map = map;

        initActions.add(new InitEntitiesAction());

        turnActions.add(new MoveCreaturesAction());
        turnActions.add(new DecreaseHungerAction());
        turnActions.add(new SpawnGrassTurnAction());
    }

    private String selectUnicodeSpriteForEntity(Entity entity) {
        if (entity instanceof Predator) {
            return GameSettings.PREDATOR_SPRITE;
        }
        if (entity instanceof Herbivore) {
            return GameSettings.HERBIVORE_SPRITE;
        }
        if (entity instanceof Grass) {
            return GameSettings.GRASS_SPRITE;
        }
        if (entity instanceof Rock) {
            return GameSettings.ROCK_SPRITE;
        }
        if (entity instanceof Tree) {
            return GameSettings.TREE_SPRITE;
        }

        return "";
    }

//    Swing-интерфейс потому что консольный вариант с постоянно обновляющимся полем мне показался неудобным
//    окно постоянно мельтешило и за состоянием карты было сложнее следить
    void initUI() {
        executeInitActions();

//        Swing-интерфейс
//        Создаем и настраиваем каркас (окно) приложения
        JFrame frame = new JFrame(GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameSettings.WIDTH_FRAME, GameSettings.HEIGHT_FRAME);

//        Панель для размещения компонентов
        JPanel panel = new JPanel();

        JPanel buttonsPanel = new JPanel();
//        GridLayout размещает компоненты в виде таблицы с равными ячейками
        panel.setLayout(new GridLayout(GameSettings.MAP_WIDTH, GameSettings.MAP_HEIGHT));

        startButton = new JButton("Старт");
        pauseButton = new JButton("Пауза");

//        Добавляем компоненты на панель
        for (int horizontal = 0; horizontal < GameSettings.MAP_WIDTH; horizontal++) {
            for (int vertikal = 0; vertikal < GameSettings.MAP_HEIGHT; vertikal++) {
                cells[horizontal][vertikal] = new JLabel(EMPTY_CELL, SwingConstants.CENTER);

                Coordinates coordinates = new Coordinates(vertikal, horizontal);

                if (!map.isSquareEmpty(coordinates)) {
                    cells[horizontal][vertikal].setText(selectUnicodeSpriteForEntity(map.getEntity(coordinates)));
                }

                cells[horizontal][vertikal].setOpaque(true);

                panel.add(cells[horizontal][vertikal]);
            }
        }

//        Добавляем панель в окно
        frame.getContentPane().add(panel);

        buttonsPanel.add(startButton);
        buttonsPanel.add(pauseButton);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

//        Разместить окно по центру экрана
        frame.setLocationRelativeTo(null);

//        показать окно
        frame.setVisible(true);
    }

    private void nextTurn() {
        turnCounter++;

        executeTurnActions();

        refreshBoard();
    }

    private void pauseSimulation() {
        timer.stop();
    }

    private void startSimulation() {
        timer.start();
    }

    //    окрашивание сущностей в Swing
    private Color selectColorForEntity(Entity entity) {
        if (entity instanceof Predator) {
            return GameSettings.PREDATOR_COLOR;
        }
        if (entity instanceof Herbivore) {
            return GameSettings.HERBIVORE_COLOR;
        }
        if (entity instanceof Grass) {
            return GameSettings.GRASS_COLOR;
        }
        if (entity instanceof Rock) {
            return GameSettings.ROCK_COLOR;
        }
        if (entity instanceof Tree) {
            return GameSettings.TREE_COLOR;
        }

        return GameSettings.DEFAULT_COLOR;
    }

    private void refreshBoard() {
        for (int horizontal = 0; horizontal < GameSettings.MAP_WIDTH; horizontal++) {
            for (int vertikal = 0; vertikal < GameSettings.MAP_HEIGHT; vertikal++) {
                Coordinates coordinates = new Coordinates(vertikal, horizontal);

                if (map.isSquareEmpty(coordinates)) {
                    cells[horizontal][vertikal].setText(EMPTY_CELL);
                    cells[horizontal][vertikal].setForeground(Color.LIGHT_GRAY);
                } else {
                    cells[horizontal][vertikal].setText(
                            selectUnicodeSpriteForEntity(
                                    map.getEntity(coordinates)
                            )
                    );
                    cells[horizontal][vertikal].setForeground(
                            selectColorForEntity(map.getEntity(coordinates))
                    );
                }
            }
        }
    }

    void initTimer() {
        timer = new Timer(GameSettings.SIMULATION_DELAY, e -> nextTurn());

//        обработчики действий
        startButton.addActionListener(e -> startSimulation());
        pauseButton.addActionListener(e -> pauseSimulation());
    }

    private void executeInitActions() {
        for (InitAction action : initActions) {
            action.execute(map);
        }
    }

    private void executeTurnActions() {
        for (TurnAction action : turnActions) {
            action.execute(map, turnCounter);
        }
    }
}