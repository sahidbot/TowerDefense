package game.towerlogic;

import org.apache.log4j.Logger;

/**
 * Base class for Attack Strategies.
 */
public abstract class AttackStrategyBase implements IAttackStrategy {
    private final Tower leTower;
    protected final Logger LOGGER = Logger.getLogger(getClass());

    /**
     * Default constructor that gets the tower for reference
     * @param leTower Reference for calculations when required
     */
    public AttackStrategyBase(Tower leTower){
        LOGGER.info("Instantiating");
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
