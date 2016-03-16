package game.towerlogic;

import common.core.Vector2;
import game.Critter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Strategy that selects the closest target
 */
public class AttackClosestStrategy extends AttackStrategyBase {


    /**
     * Default Constructor
     *
     * @param leTower Tower that is using the strategy
     */
    public AttackClosestStrategy(Tower leTower) {
        super(leTower);
    }

    /**
     * Implementaton of the {@link IAttackStrategy} interface. This class does damage to the closest enemy
     *
     * @param possibleTargets All the possible targets that can be damaged
     * @return the list of critters that received damage
     */
    @Override
    public List<Critter> doDamage(List<Critter> possibleTargets) {
        Collections.sort(possibleTargets,
                (o1, o2) ->
                        (int)((Vector2.Distance(getTower().getPosition(), o1.getPosition())
                                - Vector2.Distance(getTower().getPosition(), o2.getPosition()))*1000)
                );
        Critter target =  possibleTargets.get(0);
        target.setHealthPoints(target.getHealthPoints() - getDamage());
        List<Critter> ret = new ArrayList<>();
        ret.add(target);
        return ret;
    }

    @Override
    public AttackStrategyEnum getTypeIdentifier() {
        return AttackStrategyEnum.CLOSEST;
    }


}
