package game.towerlogic;

import game.Critter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Targets the enemy with the most hp
 */
public class AttackHighestHPStrategy extends AttackStrategyBase {
    /**
     * Default constructor that gets the tower for reference
     *
     * @param leTower Reference for calculations when required
     */
    public AttackHighestHPStrategy(Tower leTower) {
        super(leTower);
    }

    /**
     * Implementaton of the {@link IAttackStrategy} interface. This class does damage to the highest hp enemy
     *
     * @param possibleTargets All the possible targets that can be damaged
     * @return affected targets
     */
    @Override
    public List<Critter> doDamage(List<Critter> possibleTargets) {
        Collections.sort(possibleTargets,
                (o1, o2) ->
                        (int)((int)o2.getHealthPoints() - (int)(o1.getHealthPoints()))
        );
        Critter target =  possibleTargets.get(0);
        target.setHealthPoints(target.getHealthPoints() - (float)getTower().getDamage());
        List<Critter> ret = new ArrayList<>();
        ret.add(target);
        return ret;
    }

    /**
     * Method to get identifier type
     * @return an Enum of HighestHP
     */
    @Override
    public AttackStrategyEnum getTypeIdentifier() {
        return AttackStrategyEnum.HIGHESTHP;
    }
}
