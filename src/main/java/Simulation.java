import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Simulation {
    // тут надо пересмотреь все переменные, разобраться какие приватные, какие файнал
    // и интеджер или просто инт, тоже непонятно
    public static final String GAME_NAME = "Simulation";
    String emptyCell = "=";

    public static final int WIDTH_FRAME = 600;
    public static final int HEIGHT_FRAME = 600;

    private static final int GRASS_SPAWN_INTERVAL = 2;

    private int simulationDelay = 500;

    private int turnCounter = 0;

    public static final JLabel[][] cells = new JLabel[GameSettings.MAP_WIDTH][GameSettings.MAP_HEIGHT];

    private Timer timer;

    JButton pauseButton;
    JButton startButton;


    private Map map;
    Creature creature;

    public Simulation(Map map) {
        this.map = map;
    }

    public String selectUnicodeSpriteForEntity(Entity entity) {
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

    void initActions() {
        map.initActions();

//        Создаем и настраиваем каркас (окно) приложения
        JFrame frame = new JFrame(GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH_FRAME, HEIGHT_FRAME);

//        Панель для размещения компонентов
        JPanel panel = new JPanel();

        //надо ли ее, или хватит только панель
        JPanel buttonsPanel = new JPanel();
//        GridLayout размещает компоненты в виде таблицы с равными ячейками
        panel.setLayout(new GridLayout(GameSettings.MAP_WIDTH, GameSettings.MAP_HEIGHT));

        startButton = new JButton("Старт");
        pauseButton = new JButton("Пауза");

//        Добавляем компоненты на панель
        for (int gorizontal = 0; gorizontal < GameSettings.MAP_WIDTH; gorizontal++) {
            for (int vertikal = 0; vertikal < GameSettings.MAP_HEIGHT; vertikal++) {
                cells[gorizontal][vertikal] = new JLabel(emptyCell, SwingConstants.CENTER);

                cells[gorizontal][vertikal].setText(emptyCell);

                Coordinates coordinates = new Coordinates(gorizontal, vertikal);

                if (!map.isSquareEmpty(coordinates)) {
                    cells[gorizontal][vertikal].setText(selectUnicodeSpriteForEntity(map.getEntity(coordinates)));
                }

                cells[gorizontal][vertikal].setOpaque(true);

                panel.add(cells[gorizontal][vertikal]);
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

    void turnActions() {
        turnCounter++;
//        System.out.println("Ход: " + turnCounter);

        java.util.List<Herbivore> herbivores = map.getHerbivores();
        for (Herbivore herbivore : herbivores) {
            herbivore.makeMove(map);
        }

        java.util.List<Predator> predators = map.getPredators();
        for (Predator predator : predators) {
            predator.makeMove(map);
        }

        java.util.List<Creature> creatures = map.getCreatures();
        for (Creature creature : creatures) {
            creature.decreaseHunger();
            if (creature.getHealth_HP() <= 0) {
                map.removeEntity(creature.getCoordinates());
            }
        }

        spawnGrass();

        refreshBoard(map, cells);
    }

    void pauseSimulation() {
        timer.stop();
    }

    void startSimulation() {
        timer.start();
    }

    Color selectColorForEntity(Entity entity){
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

    private void refreshBoard(Map map, JLabel[][] cells) {
        for (int gorizontal = 0; gorizontal < GameSettings.MAP_WIDTH; gorizontal++) {
            for (int vertikal = 0; vertikal < GameSettings.MAP_HEIGHT; vertikal++) {
                Coordinates coordinates = new Coordinates(gorizontal, vertikal);

                if (map.isSquareEmpty(coordinates)) {
                    cells[gorizontal][vertikal].setText(emptyCell);
                    cells[gorizontal][vertikal].setForeground(Color.LIGHT_GRAY);

                } else {
                    cells[gorizontal][vertikal].setText(
                            selectUnicodeSpriteForEntity(
                                    map.getEntity(coordinates)
                            )
                    );
                    cells[gorizontal][vertikal].setForeground(
                            selectColorForEntity(map.getEntity(coordinates))
                    );
                }
            }
        }
    }

    void initTimer() {
        timer = new Timer(simulationDelay, e -> turnActions());
        startButton.addActionListener(e -> startSimulation());
        pauseButton.addActionListener(e -> pauseSimulation());
    }

    void spawnGrass() {
        if (turnCounter % GRASS_SPAWN_INTERVAL == 0) {
            map.randomCoordinates = map.getRandomCoordinates();
            map.setEntitys(map.randomCoordinates, new Grass(map.randomCoordinates));
        }
        // проверяем количество ходов
        // если пора — создаём Grass
    }
}
