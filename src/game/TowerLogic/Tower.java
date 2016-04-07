package game.towerlogic;

import common.Settings;
import common.Tile;
import common.core.Rect;
import common.core.Vector2;
import game.Critter;
import game.CritterManager;
import game.NoMoneySprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the tower and holds the tower sprite.
 * @version $revision $
 */
public class Tower extends Tile {
    private NoMoneySprite noMoneySprite;

    private TowerType towerType;
    private int level = 1;
    private double rateOfFire;
    private double rateOfFireMultiplier;
    private int baseCost;
    private int baseCostMultiplier;
    private int refundRate;
    private int refundRateMultiplier;
    private int range;
    private int rangeMultiplier;
    private double damage;
    private double damageMultiplier;
    private boolean isActive;
    private boolean canBuy;
    private AttackStrategyFactory strategyFactory;

    private IAttackStrategy attackStrategy;
    private AttackEffect attackEffect;
    private static final Logger LOGGER = Logger.getLogger(Tower.class);

    /**
     * Default constructor
     *
     * @param towerType Represents the type of tower
     * @param position  Represents the position of the Sprite
     */
    public Tower(TowerType towerType, Vector2 position) {
        this(imageFrom(towerType), position);
        this.towerType = towerType;
        isActive = false;
        setInitialValues();
        noMoneySprite = new NoMoneySprite(position);
        strategyFactory = new AttackStrategyFactory();
        setAttackStrategyEnum(AttackStrategyEnum.LOWESTHP);
    }

    /**
     * Sets the initial values for tower types
     */
    private void setInitialValues() {
        LOGGER.info("Setting Towers initial values");
        switch (towerType) {
            case FROST:
                setBaseCost(200);
                setBaseCostMultiplier(100);
                setDamage(10);
                setDamageMultiplier(7);
                setRange(2);
                setRangeMultiplier(0);
                setRateOfFire(1);
                setRateOfFireMultiplier(0);
                setRefundRate(100);
                setRefundRateMultiplier(20);
                attackEffect = AttackEffect.FREEZE;
                break;
            case SIEGE:
                setBaseCost(500);
                setBaseCostMultiplier(200);
                setDamage(35);
                setDamageMultiplier(10);
                setRange(3);
                setRangeMultiplier(0);
                setRateOfFire(1);
                setRateOfFireMultiplier(0);
                setRefundRate(200);
                setRefundRateMultiplier(40);
                attackEffect = AttackEffect.SPLASH;
                break;
            case ARROW:
                setBaseCost(200);
                setBaseCostMultiplier(50);
                setDamage(25);
                setDamageMultiplier(5);
                setRange(4);
                setRangeMultiplier(0);
                setRateOfFire(0.3);
                setRateOfFireMultiplier(0);
                setRefundRate(100);
                setRefundRateMultiplier(30);
                attackEffect = AttackEffect.BURN;
                break;
        }
    }

    /**
     * Private constructor that calls super (with an image)
     *
     * @param towerImage Image to be passed to base class
     * @param position   Position of the sprite on the window;
     * @see Vector2
     */
    private Tower(Image towerImage, Vector2 position) {
        super(towerImage, position);
    }

    /**
     * Method that translates from TowerType to Image
     *
     * @param towerType Tower type to be converted
     * @see TowerType
     */
    private static Image imageFrom(TowerType towerType) {
        Image towerImage;
        switch (towerType) {
            case ARROW:
                towerImage = Settings.TOWERARROW_TILE_IMAGE;
                break;
            case FROST:
                towerImage = Settings.TOWERFROST_TILE_IMAGE;
                break;
            case SIEGE:
                towerImage = Settings.TOWERSIEGE_TILE_IMAGE;
                break;
            default:
                throw new NotImplementedException();
        }
        return towerImage;
    }

    /**
     * Method that translates from TowerType to Image
     *
     * @param gc The {@link javafx.scene.canvas.GraphicsContext} to use. All graphics will be placed here
     *           {@inheritDoc}
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), getPosition().getX(), getPosition().getY());
        if (!isActive() && !isCanBuy()) {
            noMoneySprite.draw(gc);
        }

        if (isActive()) {
            Rect rect = getRangeRect();

            gc.setStroke(Color.RED);
            gc.strokeRect(rect.getPosition().getX(), rect.getPosition().getY(),
                    rect.getWidth(), rect.getHeight());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {

    }
    /**
     * Gets current level of the tower
     *
     * @return current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets current rate of fire depending on the level
     *
     * @return current rate of fire
     */
    public double getRateOfFire() {
        double rateOfFireResult = rateOfFire + (getLevel() * rateOfFireMultiplier);
        return rateOfFireResult;
    }

    /**
     * Gets current cost of the tower for either buying or upgrading it
     *
     * @return current cost
     */
    public int getCost() {
        int costResult = baseCost + (getLevel() * baseCostMultiplier);
        return costResult;
    }

    /**
     * Gets current refund for the tower if available
     *
     * @return current refund
     */
    public int getRefund() {
        return refundRate + (getLevel() * refundRateMultiplier);
    }

    /**
     * Gets current range of the tower depending on the level
     *
     * @return current range
     */
    public int getRange() {
        int rangeResult = range + (getLevel() * rangeMultiplier);
        return rangeResult;
    }

    /**
     * Gets the rectangle of current range of the tower depending on the level
     *
     * @return current range
     */
    public Rect getRangeRect() {
        double width = getRange() * 2 * getWidth();
        double height = getRange() * 2 * getHeight();

        double x = position.getX() + (width / 2) - width;
        double y = position.getY() + (height / 2) - height;

        Rect rect = new Rect(new Vector2(x, y), width, height);
        return rect;
    }

    /**
     * Gets current damage of the tower depending on the level
     *
     * @return current damage
     */
    public double getDamage() {
        double damageResult = damage + (getLevel() * damageMultiplier);
        return damageResult;
    }

    /**
     * Sets range multiplier
     */
    protected void setRangeMultiplier(int rangeMultiplier) {
        this.rangeMultiplier = rangeMultiplier;
    }

    /**
     * Sets current level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Adds a level to the current level
     *
     * @param levelsToAdd how many levels to add to the current level
     */
    public void AddLevel(int levelsToAdd) {
        this.level++;
    }

    /**
     * Sets damage multiplier
     */
    protected void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    /**
     * Sets rate of fire multiplier
     */
    protected void setRateOfFireMultiplier(double rateOfFireMultiplier) {
        this.rateOfFireMultiplier = rateOfFireMultiplier;
    }

    /**
     * Sets the baste cost multiplier
     */
    protected void setBaseCostMultiplier(int baseCostMultiplier) {
        this.baseCostMultiplier = baseCostMultiplier;
    }

    /**
     * Sets the range multiplier
     */
    protected void setRefundRateMultiplier(int refundRateMultiplier) {
        this.refundRateMultiplier = refundRateMultiplier;
    }

    /**
     * Sets the rate of fire
     */
    protected void setRateOfFire(double rateOfFire) {
        this.rateOfFire = rateOfFire;
    }

    /**
     * Sets the base cost
     */
    protected void setBaseCost(int baseCost) {
        this.baseCost = baseCost;
    }

    /**
     * Sets the base refund rate
     */
    protected void setRefundRate(int refundRate) {
        this.refundRate = refundRate;
    }

    /**
     * Sets range
     */
    protected void setRange(int range) {
        this.range = range;
    }

    /**
     * Sets damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Sets a new value for active
     *
     * @param active value to set
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets the value for IsActive. A tower is active if it can shoot and is placed on the map
     *
     * @return value of active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Gets {@link TowerType}
     *
     * @return returns the value
     * @see TowerType
     */
    public TowerType getTowerType() {
        return towerType;
    }

    /**
     * Whether this tower is available to buy
     *
     * @return result
     */
    public boolean isCanBuy() {
        return canBuy;
    }

    /**
     * Set whether the tower is available for buy. Mainly used to show an image in the screen
     *
     * @param canBuy new value
     */
    public void setCanBuy(boolean canBuy) {
        if (this.canBuy != canBuy) {
            LOGGER.debug("Changed the state of tower buying option to: " + canBuy);
        }

        this.canBuy = canBuy;
    }

    /**
     * Gets the {@link IAttackStrategy}
     * @return The value of the strategy
     */
    public IAttackStrategy getAttackStrategy() {
        return attackStrategy;
    }

    /**
     * Sets the {@link IAttackStrategy}
     * @param attackStrategy the setting value
     */
    public void setAttackStrategy(IAttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    /**
     * Sets the {@link IAttackStrategy} using the {@link AttackStrategyEnum} as reference.
     * @param strategy the strategy to use
     */
    public void setAttackStrategyEnum(AttackStrategyEnum strategy) {
        attackStrategy = strategyFactory.getAttackStrategy(strategy, this);
        LOGGER.info(getUniqueId() + ": The tower: " + this.getTowerType().toString() + " is now using:  " +
                strategy.toString()+ " strategy");
    }

    /**
     * Gets the current {@link AttackStrategyEnum}
     * @return the value
     */
    public AttackStrategyEnum getAttackStrategyEnum(){
        return getAttackStrategy().getTypeIdentifier();
    }

    private double currentRateOfFireCount;

    /**
     * Returns true when it is time to fire and false otherwise.
     * If it is time to fire, it resets itself and will return false next time.
     * @param delta The delta time when calling the method
     * @return Whether or not it is time to fire
     */
    public Boolean isTimeToFire(double delta){
        currentRateOfFireCount -= delta;

        Boolean ret = currentRateOfFireCount <= 0;
        if (ret)
        {
            clearRateOfFire();
        }
        return ret;
    }

    /**
     * Clears the flag for isTimeToFire method
     */
    public void clearRateOfFire(){
        currentRateOfFireCount = getRateOfFire();
    }

    /**
     * Gets the attack effect
     * @return Value of attack effect
     */
    public AttackEffect getAttackEffect() {
        return attackEffect;
    }

    /**
     * Method that selects and applies damage to critters that are to be damaged
     * @param critterManager The critter manager to check critters for
     * @return list of affected critters
     */
    public List<Critter> doDamage(CritterManager critterManager){
        ArrayList<Critter> possibleTargets = critterManager.getShootableCritters(this);
        return doDamage(critterManager, possibleTargets);
    }

    /**
     * Method that selects and applies damage to critters that are to be damaged
     * @param critterManager The critter manager to check critters for
     * @param possibleTargets The possible targets if it is available
     * @return list of affected critters
     */
    public List<Critter> doDamage(CritterManager critterManager, ArrayList<Critter> possibleTargets) {
        List<Critter> ret = new ArrayList<>();
        List<Critter> targets = attackStrategy.doDamage(possibleTargets);
        ret.addAll(targets);
        if(getAttackEffect() == AttackEffect.SPLASH){
            List<Critter> splashEffectTargets = critterManager.getCritterNeighbours(this, possibleTargets, targets.get(0));
            System.out.println(splashEffectTargets.size());

            ret.addAll(splashEffectTargets);
            for (Critter leCritter :
                    splashEffectTargets) {
                leCritter.setHealthPoints(leCritter.getHealthPoints() - (float)(getDamage() * 0.5));
                ret.add(leCritter);
            }
        }
        else {
            for (Critter target :
                    targets) {
                if (getAttackEffect() == AttackEffect.BURN) {
                    target.setDamagePerSecond((float) (getDamage() / (getRateOfFire() * 2)));
                    target.setDamagePerSecondDuration((float) getRateOfFire());
                }
                else if(getAttackEffect() == AttackEffect.FREEZE){
                    target.setFrozenDuration((float) (getRateOfFire()/2));
                }
            }
        }
        return ret;
    }
}
