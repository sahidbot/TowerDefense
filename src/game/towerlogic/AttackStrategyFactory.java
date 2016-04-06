package game.towerlogic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Factory for Attack Strategies.
 */
public class AttackStrategyFactory {

    /**
     * Instantiates an attack strategy depending on the enum
     * @param toGet requesting attack strategy
     * @param toHold the tower that will be holding the strategy.
     * @return attack strategy instance
     */
    public IAttackStrategy getAttackStrategy(AttackStrategyEnum toGet, Tower toHold){
        //Ideally, dynamically mapping instances with enums in a dictionary
        IAttackStrategy ret;
        switch (toGet) {
            case CLOSEST:
                ret = new AttackClosestStrategy(toHold);
                break;
            case FARTHEST:
                ret = new AttackFarthestStrategy(toHold);
                break;
            case LOWESTHP:
                ret = new AttackLowestHPStrategy(toHold);
                break;
            case NEARESTTOENDPOINT:
                ret = new AttackNearestToEndPointStrategy(toHold);
                break;
            case HIGHESTHP:
                ret = new AttackHighestHPStrategy(toHold);
                break;
            default:
                throw new NotImplementedException();

        }
        return ret;
    }
}
