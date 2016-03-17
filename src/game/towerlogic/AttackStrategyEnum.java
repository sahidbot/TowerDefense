package game.towerlogic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Enum that respresents the AttackStrategy
 */
public enum AttackStrategyEnum {
    CLOSEST,
    FARTHEST,
    LOWESTHP;


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
                return "Closest Enemy";
            case FARTHEST:
                return "Farthest Enemy";
            case LOWESTHP:
                return "Lowest Hp Enemy";
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
