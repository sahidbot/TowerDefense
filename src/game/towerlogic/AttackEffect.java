package game.towerlogic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Represents any effect
 */
public enum AttackEffect {
    SPLASH,
    FREEZE,
    BURN;

    /**
     * Returns a readable string from the value
     * @return readable string
     */
    public String toString(){
        switch (this){
            case BURN:
                return "Burn";
            case FREEZE:
                return "Freezing";
            case SPLASH:
                return "Splash";
            default:
                throw new NotImplementedException();
        }
    }
}
