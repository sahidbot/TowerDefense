package game.towerlogic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Monster on 3/15/2016.
 */
public enum AttackStrategyEnum {
    CLOSEST,
    FARTHEST,
    LOWESTHP;



    private static AttackStrategyEnum[] vals = values();
    public AttackStrategyEnum next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }

    public String ToString() {
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

    public AttackStrategyEnum previous() {
        if(this.ordinal() == 0){
            return vals[vals.length - 1];
        }
        else{
            return vals[(this.ordinal()-1)];
        }
    }
}
