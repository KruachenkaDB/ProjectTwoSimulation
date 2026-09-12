import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;

public class Simulation {
    // тут надо пересмотреь все переменные, разобраться какие приватные, какие файнал
    // и интеджер или просто инт, тоже непонятно

    public static final String ANSI_YELLOW_SQUARE_BACKGROUND = "\u001B[33m";
    public static final String ANSI_RED_SQUARE_BACKGROUND = "\u001B[31m";
    public static final String ANSI_GREEN_SQUARE_BACKGROUND = "\u001B[32m";
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001b[37m";

    public static final String GAME_NAME = "Simulation";
    String emptyCell = "=";

    public static final int WIDTH_FRAME = 1000;
    public static final int HEIGHT_FRAME = 400;

    public static final int PANEL_TABLE_ROWS = 10;
    public static final int PANEL_TABLE_COLS = 80;

    private int turnCounter = 0;

    public static final JLabel[][] cells = new JLabel[10][80];

    //не будут ли хищники пресекаться во время хода?

    //Карту
    private Map map;

    public Simulation(Map map) {
        this.map = map;
    }

    //Счётчик ходов


    //Рендерер поля
    public void render(Map map) {

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

    //запустить бесконечный цикл симуляции и рендеринга
    //съедание происходит когда координаты совпадают (но не заюываем про пищевую цепочку
    // мышь не может съесть сову
    void startSimulation() {
//        while (true) {
        turnActions();
//        }
    }

    void initActions() {
        map.initActions();

//        Создаем и настраиваем каркас (окно) приложения
        JFrame frame = new JFrame(GAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH_FRAME, HEIGHT_FRAME);

//        Панель для размещения компонентов
        JPanel panel = new JPanel();
//        GridLayout размещает компоненты в виде таблицы с равными ячейками
        panel.setLayout(new GridLayout(PANEL_TABLE_ROWS, PANEL_TABLE_COLS));

//        Добавляем компоненты на панель
        for (int gorizontal = 0; gorizontal < 10; gorizontal++) {
            for (int vertikal = 0; vertikal < 80; vertikal++) {
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
//        Разместить окно по центру экрана
        frame.setLocationRelativeTo(null);
//        показать окно
        frame.setVisible(true);
    }

    void turnActions() {
        turnCounter++;
        System.out.println("Ход: " + turnCounter);
        java.util.List<Herbivore> herbivores = map.getHerbivores();
        java.util.List<Predator> predators = map.getPredators();

        for (Herbivore herbivore : herbivores) {
            herbivore.makeMove(map);
        }

        for (Predator predator : predators) {
            predator.makeMove(map);
        }
        refreshBoard(map, cells);
    }

    //    приостановить бесконечный цикл симуляции и рендеринга
    void pauseSimulation() {

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
    //    тут одинаковый код, может что-то вынести в отдельный код
    private void refreshBoard(Map map, JLabel[][] cells) {
        for (int gorizontal = 0; gorizontal < 10; gorizontal++) {
            for (int vertikal = 0; vertikal < 80; vertikal++) {
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
}
