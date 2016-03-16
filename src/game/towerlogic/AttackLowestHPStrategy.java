package game.towerlogic;

import common.core.Vector2;
import game.Critter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Monster on 3/15/2016.
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
                        (int)(o1.getHp() - o2.getHp())
        );
        Critter target =  possibleTargets.get(0);
        target.setHp(target.getHp() - getDamage());
        List<Critter> ret = new ArrayList<>();
        ret.add(target);
        return ret;
    }

    @Override
    public AttackStrategyEnum getTypeIdentifier() {
        return AttackStrategyEnum.LOWESTHP;
    }
}
