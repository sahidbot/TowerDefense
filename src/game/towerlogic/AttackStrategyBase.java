package game.towerlogic;

/**
 * Base class for Attack Strategies.
 */
public abstract class AttackStrategyBase implements IAttackStrategy {
    private final Tower leTower;
    private float damage;


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

    /**
     * Sets teh value for damage
     * @param damage new value for damage
     */
    @Override
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * Gets teh value for damage
     * @return value of damage
     */
    @Override
    public float getDamage() {
        return damage;
    }

}
