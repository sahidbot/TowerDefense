package game.towerlogic;

import game.Critter;

/**
 * Interface that represents the strategy to attack critters
 */
public interface IAttackStrategy {
    /**
     * Method that does damage to some critters depending on the implementation.
     * @param possibleTargets All the possible targets that can be damaged
     * @return The critters that have were damaged
     */
    Critter[] DoDamage(Critter[] possibleTargets);
}
