package game.towerlogic;

import common.core.Vector2;
import game.Critter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Strategy that selects the lowest Farthest target
 */
public class AttackFarthestStrategy extends AttackStrategyBase {
    public AttackFarthestStrategy(Tower leTower) {
        super(leTower);
    }

    /**
     * Implementaton of the {@link IAttackStrategy} interface. This class does damage to the farthest enemy
     *
     * @param possibleTargets All the possible targets that can be damaged
     * @return affected targets
     */
    @Override
    public List<Critter> doDamage(List<Critter> possibleTargets) {
        Collections.sort(possibleTargets,
                (o1, o2) ->
                        (int)((Vector2.Distance(getTower().getPosition(), o2.getPosition())
                                - Vector2.Distance(getTower().getPosition(), o1.getPosition()))*1000)
        );
        Critter target =  possibleTargets.get(0);
        target.setHealthPoints(target.getHealthPoints() - (float)getTower().getDamage());
        List<Critter> ret = new ArrayList<>();
        ret.add(target);
        return ret;
    }
    /**
     * Method to get identifier type
     * @return an Enum of Farthest
     */
    @Override
    public AttackStrategyEnum getTypeIdentifier() {
        return AttackStrategyEnum.FARTHEST;
    }
}
