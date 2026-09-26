package spawn;

import config.GameSettings;
import entity.Herbivore;
import map.Coordinates;

public class HerbivoreSpawner extends EntitySpawner<Herbivore> {
    @Override
    protected Herbivore createEntity(Coordinates coordinates) {
        return new Herbivore(coordinates, GameSettings.HERBIVORE_SPEED, GameSettings.HERBIVORE_HEALTH, GameSettings.MAX_HUNGER, GameSettings.HUNGER_DECREASE, GameSettings.HUNGER_DAMAGE);
    }
}
