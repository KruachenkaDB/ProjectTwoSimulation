import config.GameSettings;
import entity.*;
import map.Coordinates;
import map.Map;

import javax.swing.*;
import java.awt.*;
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

    public Simulation(Map map) {
        this.map = map;
    }

    private String selectUnicodeSpriteForEntity(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Predator":
                return "\uD83E\uDD89";
            case "Herbivore":
                return "\uD83D\uDC01";
            case "Grass":
                return "\uD83C\uDF3D";
            case "Rock":
                return "\uD83D\uDDFB";
            case "Tree":
                return "\uD83C\uDF32";
        }
        return "";
    }

    // переименовать
    void initUI() {
        map.initEntitys();

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

    private void turnActions() {
        turnCounter++;

        List<Herbivore> herbivores = map.getHerbivores();
        for (Herbivore herbivore : herbivores) {
            herbivore.makeMove(map);
        }

        List<Predator> predators = map.getPredators();
        for (Predator predator : predators) {
            predator.makeMove(map);
        }

        List<Creature> creatures = map.getCreatures();
        for (Creature creature : creatures) {
            creature.decreaseHunger();
            if (creature.getHealth() <= 0) {
                map.removeEntity(creature.getCoordinates());
            }
        }

        spawnGrass();

        refreshBoard();
    }

    private void pauseSimulation() {
        timer.stop();
    }

    private void startSimulation() {
        timer.start();
    }

    private Color selectColorForEntity(Entity entity){
        switch (entity.getClass().getSimpleName()) {
            case "Predator":
                return Color.decode("#8B4513");
            case "Herbivore":
                return Color.decode("#4169E1");
            case "Grass":
                return  Color.decode("#FFD700");
            case "Rock":
                return Color.decode("#000000");
            case "Tree":
                return  Color.decode("#008000");
            default:
                return Color.decode("#FFFAFA");
        }
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
        timer = new Timer(GameSettings.SIMULATION_DELAY, e -> turnActions());

        // обработчики действий
        startButton.addActionListener(e -> startSimulation());
        pauseButton.addActionListener(e -> pauseSimulation());
    }

    void spawnGrass() {
        if (turnCounter % GameSettings.GRASS_SPAWN_INTERVAL == 0) {
            Coordinates coordinates = map.getRandomCoordinates();
            map.setEntitys(coordinates, new Grass(coordinates));
        }
    }
}