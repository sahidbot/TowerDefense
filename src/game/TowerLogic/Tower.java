package game.towerlogic;

import common.Settings;
import common.Tile;
import common.core.Vector2;
import game.NoMoneySprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private double range;
    private double rangeMultiplier;
    private double damage;
    private double damageMultiplier;
    private boolean isActive;
    private boolean canBuy;

    private IAttackStrategy attackStrategy;

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
    }

    /**
     * Sets the initial values for tower types
     */
    private void setInitialValues() {
        switch (towerType) {
            case FROST:
                setBaseCost(200);
                setBaseCostMultiplier(100);
                setDamage(10);
                setDamageMultiplier(2);
                setRange(5);
                setRangeMultiplier(0);
                setRateOfFire(10);
                setRateOfFireMultiplier(1);
                setRefundRate(100);
                setRefundRateMultiplier(20);
                break;
            case SIEGE:
                setBaseCost(500);
                setBaseCostMultiplier(200);
                setDamage(20);
                setDamageMultiplier(4);
                setRange(10);
                setRangeMultiplier(0);
                setRateOfFire(1);
                setRateOfFireMultiplier(0);
                setRefundRate(200);
                setRefundRateMultiplier(40);
                break;
            case ARROW:
                setBaseCost(200);
                setBaseCostMultiplier(50);
                setDamage(1);
                setDamageMultiplier(0.2);
                setRange(6);
                setRangeMultiplier(0.5);
                setRateOfFire(50);
                setRateOfFireMultiplier(2.5);
                setRefundRate(100);
                setRefundRateMultiplier(30);
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
        return rateOfFire + (getLevel() * rateOfFireMultiplier);
    }

    /**
     * Gets current cost of the tower for either buying or upgrading it
     *
     * @return current cost
     */
    public int getCost() {
        return baseCost + (getLevel() * baseCostMultiplier);
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
    public double getRange() {
        return range + (getLevel() * rangeMultiplier);
    }

    /**
     * Gets current damage of the tower depending on the level
     *
     * @return current damage
     */
    public double getDamage() {
        return damage + (getLevel() * damageMultiplier);
    }

    /**
     * Sets range multiplier
     */
    protected void setRangeMultiplier(double rangeMultiplier) {
        this.rangeMultiplier = rangeMultiplier;
    }

    /**
     * Sets current level
     */
    protected void setLevel(int level) {
        this.level = level;
    }

    /**
     * Adds a level to the current level
     *
     * @param levelsToAdd how many levels to add to the current level
     */
    protected void AddLevel(int levelsToAdd) {
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
    protected void setRange(double range) {
        this.range = range;
    }

    /**
     * Sets damage
     */
    protected void setDamage(double damage) {
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
        this.canBuy = canBuy;
    }



}
