package game;

import common.SpriteType;
import common.Tile;
import common.core.Vector2;

/**
 * Class that represents a critter in the game.
 */
public class Critter extends Tile {

    private float healthPoints;
    private final CritterType critterType;
    private float speed;
    private float damagePerSecond = 0;
    private float damagePerSecondDuration = 0;
    private float frozenDuration = 0;

    /**
     * Default Constructor
     *
     * @param width represents the width of the sprite
     * @param height represents the height of the sprite
     * @param position represent the initial position of the sprite
     */
    public Critter(double width, double height, Vector2 position, CritterType critterType) {
        super(SpriteType.CRITTER, width, height, position);
        this.critterType = critterType;
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
}
