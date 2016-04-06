package game.towerlogic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Enum that respresents the AttackStrategy
 */
public enum AttackStrategyEnum {
    CLOSEST,
    FARTHEST,
    LOWESTHP,
    NEARESTTOENDPOINT,
    HIGHESTHP;

    private static AttackStrategyEnum[] vals = values();

    /**
     * Iterates to the next enum on the list
     *
     * @return the next enum
     */
    public AttackStrategyEnum next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }

    /**
     * Returns a readable string
     *
     * @return The readable string
     */
    public String toString() {
        switch (this){
            case CLOSEST:
                return "Closest to Tower";
            case FARTHEST:
                return "Farthest From Tower";
            case LOWESTHP:
                return "Lowest Hp ";
            case NEARESTTOENDPOINT:
                return "Closest to Exit";
            case HIGHESTHP:
                return "Highest HP";
            default:
                throw new NotImplementedException();
        }
    }

    /**
     * Iterates to the previous enum
     *
     * @return
     */
    public AttackStrategyEnum previous() {
        if(this.ordinal() == 0){
            return vals[vals.length - 1];
        }
        else{
            return vals[(this.ordinal()-1)];
        }
    }
}
