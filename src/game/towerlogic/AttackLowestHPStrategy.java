package game.towerlogic;

import game.Critter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Strategy that selects teh lowest HP target
 */
public class AttackLowestHPStrategy extends AttackStrategyBase {
    public AttackLowestHPStrategy(Tower leTower) {
        super(leTower);
    }

    /**
     * Implementaton of the {@link IAttackStrategy} interface. This class does damage to the lowest hp enemy
     *
     * @param possibleTargets All the possible targets that can be damaged
     * @return affected targets
     */
    @Override
    public List<Critter> doDamage(List<Critter> possibleTargets) {
        Collections.sort(possibleTargets,
                (o1, o2) ->
                        (int)(o1.getHealthPoints() - o2.getHealthPoints())
        );
        Critter target =  possibleTargets.get(0);
        target.setHealthPoints(target.getHealthPoints() - getDamage());
        List<Critter> ret = new ArrayList<>();
        ret.add(target);
        return ret;
    }

    @Override
    public AttackStrategyEnum getTypeIdentifier() {
        return AttackStrategyEnum.LOWESTHP;
    }
}
