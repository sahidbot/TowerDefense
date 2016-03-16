package game.towerlogic;

import game.Critter;

import java.util.List;

/**
 * Interface that represents the strategy to attack critters
 */
public interface IAttackStrategy {
    /**
     * Method that does damage to some critters depending on the implementation.
     *
     * @param possibleTargets All the possible targets that can be damaged
     * @return The critters that have were damaged
     */
    List<Critter> doDamage(List<Critter> possibleTargets);

    /**
     * Set base damage to apply
     *
     * @param damage new value for damage
     */
    void setDamage(float damage);

    /**
     * Gets current value of damage
     *
     * @return the value of damage
     */
    float getDamage();

    /**
     * The {@link AttackStrategyEnum} that is assigned to this strategy
     * @return the value
     */
    AttackStrategyEnum getTypeIdentifier();
}
