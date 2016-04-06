package game;

import common.Settings;
import common.SpriteType;
import common.Tile;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class that represents a critter in the game.
 */
public class Critter extends Tile {
    private float healthPoints = 100;
    private float goldValue = 50;

    private final CritterType critterType;
    private float speed = 1;
    private float damagePerSecond = 0;
    private float damagePerSecondDuration = 0;
    private float frozenDuration = 0;

    private CritterHealthBar healthBar;

    /**
     * Default Constructor
     *
     * @param position Represent the initial position of the sprite
     * @param critterType Type of the critter
     */
    public Critter(Vector2 position, CritterType critterType) {
        super(imageFrom(critterType), position);
        this.critterType = critterType;
        healthBar = new CritterHealthBar(position, healthPoints);
    }
    /**
     * Default Constructor for tests
     *
     * @param width represents the width of the sprite
     * @param height represents the height of the sprite
     * @param position represent the initial position of the sprite
     */
    public Critter(double width, double height, Vector2 position, CritterType critterType) {
        super(SpriteType.CRITTER, width, height, position);
        this.critterType = critterType;
        healthBar = new CritterHealthBar(position, healthPoints);
    }

    /**
     * Get the gold value for the critter.
     *
     * @return Returns the gold value
     */
    public float getGoldValue() {
        return goldValue;
    }
    /**
     * Method that translates from TowerType to Image
     *
     * @param critterType Type of the critter
     * @return Image of the critter
     */
    private static Image imageFrom(CritterType critterType) {
        return Settings.CRITTER_TILE_IMAGE_1;
    }

    /**
     * Method that translates from CritterType to Image
     *
     * @param gc The {@link javafx.scene.canvas.GraphicsContext} to use. All graphics will be placed here
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), getPosition().getX(), getPosition().getY());
        healthBar.draw(gc);
    }

    /**
     * returns the value of the current Health Points
     *
     * @return current healthPoints
     */
    public float getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets the value of the current Health Points
     *
     * @param healthPoints new value for healthPoints
     */
    public void setHealthPoints(float healthPoints) {
        this.healthPoints = healthPoints;
        this.healthBar.updateHealthPoints(healthPoints);
    }

    /**
     * Gets the type of the critter
     *
     * @return the value of the critter type
     */
    public CritterType getCritterType() {
        return critterType;
    }

    /**
     * Returns the value of the critter speed
     *
     * @return the value of the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the value of the speed
     *
     * @param speed the new value for speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Adds health points to the current health points
     * @param toAdd Value to add
     */
    public void addHealthPoints(float toAdd){
        healthPoints += toAdd;
        healthBar.updateHealthPoints(healthPoints);
    }

    /**
     * Gets the damage per second if applied
     * @return the value of damage per second
     */
    public float getDamagePerSecond() {
        return damagePerSecond;
    }

    /**
     * Gets the damage per second
     * @param damagePerSecond the new value
     */
    public void setDamagePerSecond(float damagePerSecond) {
        this.damagePerSecond = damagePerSecond;
    }

    /**
     * Gets teh duration of the damage per second
     * @return the value
     */
    public float getDamagePerSecondDuration() {
        return damagePerSecondDuration;
    }

    /**
     * Sets teh damage per second duration
     * @param damagePerSecondDuration the new value for the damage per second
     */
    public void setDamagePerSecondDuration(float damagePerSecondDuration) {
        this.damagePerSecondDuration = damagePerSecondDuration;
    }

    /**
     * Gets the frozen duration. If the value is 0, there is no freezing applied
     * @return the frozen duration
     */
    public float getFrozenDuration() {
        return frozenDuration;
    }

    /**
     * Sets teh frozen duration. If the value is 0, there is no freezing applied
     * @param frozenDuration the new value
     */
    public void setFrozenDuration(float frozenDuration) {
        this.frozenDuration = frozenDuration;
    }

    /**
     * Check whether the critter can move.
     *
     * @return Returns true if it can move, or false
     */
    public boolean canMove() {
        return frozenDuration <= 0;
    }

    /**
     * Update the critters for every ticks
     * @param delta Delta time value from game loop
     */
    public void update(double delta) {
        // damage per second
        if (damagePerSecondDuration >= 0) {
            setHealthPoints((float) (healthPoints - (delta * damagePerSecond)));
            damagePerSecondDuration -= delta;
        }
        else {
            damagePerSecondDuration = 0;
        }

        // frozen duration
        if (frozenDuration >= 0) {
            frozenDuration -= delta;
        }
        else {
            frozenDuration = 0;
        }
    }
}
