package game.towerlogic;

/**
 * Base class for Attack Strategies.
 */
public abstract class AttackStrategyBase implements IAttackStrategy {
    private final Tower leTower;

    /**
     * Default constructor that gets the tower for reference
     * @param leTower Reference for calculations when required
     */
    public AttackStrategyBase(Tower leTower){
        this.leTower = leTower;
    }

    /**
     * Default getter for the tower
     * @return The {@link Tower}
     */
    public Tower getTower() {
        return leTower;
    }
}
