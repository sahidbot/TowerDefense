package game;

import common.SpriteType;
import common.Tile;
import common.core.Vector2;

/**
 * Class that represents a critter in the game.
 */
public class Critter extends Tile {

    private float hp;
    private final CritterType critterType;
    private float speed;

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
     * @return current hp
     */
    public float getHp() {
        return hp;
    }

    /**
     * Sets the value of the current Health Points
     *
     * @param hp new value for hp
     */
    public void setHp(float hp) {
        this.hp = hp;
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
}
