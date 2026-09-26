package spawn;

import config.GameSettings;
import entity.Predator;
import map.Coordinates;

public class PredatorSpawner extends EntitySpawner<Predator> {
    @Override
    protected Predator createEntity(Coordinates coordinates) {
        return new Predator(coordinates, GameSettings.PREDATOR_SPEED, GameSettings.PREDATOR_HEALTH, GameSettings.PREDATOR_ATTACK, GameSettings.MAX_HUNGER, GameSettings.HUNGER_DECREASE, GameSettings.HUNGER_DAMAGE);
    }
}