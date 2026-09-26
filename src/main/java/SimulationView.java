import config.GameSettings;
import entity.*;
import map.Coordinates;
import map.GameMap;

import javax.swing.*;
import java.awt.*;

public class SimulationView {
    private static final String GAME_NAME = "Simulation";
    private static final String EMPTY_CELL = "=";

    private final JLabel[][] cells = new JLabel[GameSettings.MAP_WIDTH][GameSettings.MAP_HEIGHT];

    private final JButton pauseButton = new JButton("Пауза");
    private final JButton startButton = new JButton("Старт");
    private final JButton stepButton = new JButton("Ход");

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStepButton() {
        return stepButton;
    }

//    Swing-интерфейс потому что консольный вариант с постоянно обновляющимся полем мне показался неудобным
//    окно постоянно мельтешило и за состоянием карты было сложнее следить
    void show(GameMap gameMap) {
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

//        Добавляем компоненты на панель
        for (int x = 0; x < GameSettings.MAP_WIDTH; x++) {
            for (int y = 0; y < GameSettings.MAP_HEIGHT; y++) {
                cells[x][y] = new JLabel(EMPTY_CELL, SwingConstants.CENTER);

                Coordinates coordinates = new Coordinates(y, x);

                if (!gameMap.isSquareEmpty(coordinates)) {
                    Entity entity = gameMap.getEntity(coordinates);

                    cells[x][y].setText(selectUnicodeSpriteForEntity(entity));
                    cells[x][y].setForeground(selectColorForEntity(entity));
                }

                cells[x][y].setOpaque(true);

                panel.add(cells[x][y]);
            }
        }

        buttonsPanel.add(startButton);
        buttonsPanel.add(pauseButton);
        buttonsPanel.add(stepButton);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);

//        Разместить окно по центру экрана
        frame.setLocationRelativeTo(null);

//        показать окно
        frame.setVisible(true);
    }

    public void refreshBoard(GameMap gameMap) {
        for (int x = 0; x < GameSettings.MAP_WIDTH; x++) {
            for (int y = 0; y < GameSettings.MAP_HEIGHT; y++) {
                Coordinates coordinates = new Coordinates(y, x);

                if (gameMap.isSquareEmpty(coordinates)) {
                    cells[x][y].setText(EMPTY_CELL);
                    cells[x][y].setForeground(Color.LIGHT_GRAY);
                } else {
                    Entity entity = gameMap.getEntity(coordinates);
                    cells[x][y].setText(selectUnicodeSpriteForEntity(entity));
                    cells[x][y].setForeground(selectColorForEntity(entity));
                }
            }
        }
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
}
