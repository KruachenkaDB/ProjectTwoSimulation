package config;

import java.awt.*;

public class GameSettings {
    public static final int MAP_WIDTH = 40;
    public static final int MAP_HEIGHT = 40;

    public static final int WIDTH_FRAME = 600;
    public static final int HEIGHT_FRAME = 600;

    public static final int INITIAL_PREDATOR_COUNT = 5;
    public static final int INITIAL_HERBIVORE_COUNT = 35;
    public static final int INITIAL_GRASS_COUNT = 70;
    public static final int INITIAL_ROCK_COUNT = 30;
    public static final int INITIAL_TREE_COUNT = 100;

    public static final int MAX_HUNGER = 100;
    public static final int HUNGER_DECREASE = 10;
    public static final int HUNGER_DAMAGE = 20;

    public static final int PREDATOR_HEALTH = 100;
    public static final int PREDATOR_ATTACK = 100;
    public static final int PREDATOR_SPEED = 2;

    public static final int HERBIVORE_HEALTH = 100;
    public static final int HERBIVORE_SPEED = 1;

    public static final int SIMULATION_DELAY = 500;

    public static final String PREDATOR_SPRITE = "\uD83E\uDD89";
    public static final String HERBIVORE_SPRITE = "\uD83D\uDC01";
    public static final String GRASS_SPRITE = "\uD83C\uDF3D";
    public static final String ROCK_SPRITE = "\uD83D\uDDFB";
    public static final String TREE_SPRITE = "\uD83C\uDF32";

    public static final Color PREDATOR_COLOR = Color.decode("#8B4513");
    public static final Color HERBIVORE_COLOR = Color.decode("#4169E1");
    public static final Color GRASS_COLOR = Color.decode("#FFD700");
    public static final Color ROCK_COLOR = Color.decode("#000000");
    public static final Color TREE_COLOR = Color.decode("#008000");
    public static final Color DEFAULT_COLOR = Color.decode("#FFFAFA");
}
