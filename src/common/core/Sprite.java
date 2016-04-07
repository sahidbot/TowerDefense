package common.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.UUID;

/**
 *This is the base sprites class, it stores width, height, position, health, damage and move state of the sprite.
 * @version $revision $
 */
public abstract class Sprite implements Cloneable{
    private UUID uniqueId = UUID.randomUUID();

    protected double width;
    protected double height;

    public Vector2 position;
    public Vector2 velocity;

    public double health;
    public double damage;
/**
* Constructor function to initialize Sprite
* @param width Value of width
* @param height Value of Height
* @param position Position represented as Vector2
*/
    public Sprite(double width, double height, Vector2 position) {
        this(width, height, position, null, 0, 0);
    }
    /**
    *Overridden constructor mainly for the purpose of creating a Tower
    * @param width Value of width
    * @param height Value of Height
    * @param position Position represented as Vector2
    * @param health Health points of tower
    * @param damage Damage dealth by tower
    */
    public Sprite(double width, double height, Vector2 position, double health, double damage) {
        this(width, height, position, null, health, damage);
    }
    /**
    *Overridden constructor mainly for the purpose of creating a Tower
    * @param width Value of width
    * @param height Value of Height
    * @param position Position represented as Vector2
    * @param velocity Velocity of tower canon represented as Vector2
    * @param health Health points of tower
    * @param damage Damage dealth by tower
    */
    public Sprite(double width, double height, Vector2 position, Vector2 velocity, double health, double damage) {
        this.position = position;
        this.velocity = velocity;

        this.health = health;
        this.damage = damage;

        this.width = width;
        this.height = height;
    }

    /**
     * This method check if a sprite is alive by comparing sprite health
     * @return: the compared health
     */
    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }
    /**
     * Get the value of width.
     * @return Returns the width of sprite.
     */
    public double getWidth() {
        return width;
    }
    /**
     * Get the value of height.
     * @return Returns the height of sprite.
     */
    public double getHeight() {
        return height;
    }
    /**
     * Get the position of sprite of height.
     * @return Returns the position as Vector2 coordinates.
     */
    public Vector2 getPosition() {
        return position;
    }
    /**
     * Set the position of sprite from vector
     * @param position The position at which to be set.
     */
    public void setPosition(Vector2 position) {
        this.position.setFromVector(position);
    }
    /**
     * Get the X centre
     * @return Returns X part of position
     */
    public double getCenterX() {
        return position.getX() + width * 0.5;
    }
    /**
     * Get the Y centre
     * @return Returns Y part of position
     */
    public double getCenterY() {
        return position.getY() + height * 0.5;
    }
    /**
     * Get the unique Id
     * @return Returns the unique Id of sprite
     */
    public String getUniqueId() {
        return this.uniqueId.toString();
    }

    /**
     * Set the unique id for the sprite
     *
     * @param id Unique id
     */
    public void setUniqueId(UUID id) {
        this.uniqueId = id;
    }

    /**
     * Generates unique Id for a sprite
     */
    public void generateNewUniqueId() {
        this.uniqueId = UUID.randomUUID();
    }
    /**
     * Method to check if sprite is colliding with any other sprite
     *
     * @param otherSprite The sprite against which newly placed sprite is verified
     * @return Returns True if colliding otherwise False
     */
    public boolean collidesWith(Sprite otherSprite) {
        // not per-pixel-collision
        return (otherSprite.position.getX() + otherSprite.getWidth() >= this.position.getX() &&
                otherSprite.position.getY() + otherSprite.getHeight() >= this.position.getY() &&
                otherSprite.position.getX() <= this.position.getX() + this.width &&
                otherSprite.position.getY() <= this.position.getY() + this.height);
    }
    /**
     * Method to check if sprite is colliding with any other points
     *
     * @param position The position at which the sprite is verified for collision
     * @return Returns True if colliding otherwise False
     */
    public boolean collidesWith(Vector2 position) {
        return (position.getX() >= this.position.getX() &&
                position.getY() >= this.position.getY() &&
                position.getX() <= this.position.getX() + this.width &&
                position.getY() <= this.position.getY() + this.height);
    }

    /**
     * Method to check if sprite is colliding with any other rectangle
     *
     * @param position The position at which the sprite is verified for collision
     * @param size The size of the rectangle
     * @return Returns True if colliding otherwise False
     */
    public boolean collidesWith(Vector2 position, Vector2 size) {
        return (position.getX() + size.getX() >= this.position.getX() &&
                position.getY() + size.getY() >= this.position.getY() &&
                position.getX() <= this.position.getX() + this.width &&
                position.getY() <= this.position.getY() + this.height);
    }
    /**
     * Method to check if sprite is colliding with any other sprite
     *
     * @param rect The rectangle to verify with
     * @return Returns True if colliding otherwise False
     */
    public boolean collidesWith(Rect rect) {
        // not per-pixel-collision
        return (rect.getPosition().getX() + rect.getWidth() >= this.position.getX() &&
                rect.getPosition().getY() + rect.getHeight() >= this.position.getY() &&
                rect.getPosition().getX() <= this.position.getX() + this.width &&
                rect.getPosition().getY() <= this.position.getY() + this.height);
    }
    /**
     * This method kills a sprite by changing its health to 0
     */
    public void kill() {

        health = 0;
    }

    /**
     * Inherited method for drawing a Sprite in graphic context
     *
     * @param gc represents the graphic context
     */
    public abstract void draw(GraphicsContext gc);

    /**
     * Inherited method for updating
     */
    public abstract void update();

    /**
     * This method checks if the id two Sprite object are the same
     *
     * @return returns the evaluated boolean result
     */
    public boolean equals(Sprite obj) {
        return this.getUniqueId().equals(obj.getUniqueId());
    }

    /*
    public <T extends Sprite> T clone(Class<T> type) throws CloneNotSupportedException {
        T clonedSprite = type.cast(super.clone());
        clonedSprite.generateNewUniqueId();
        return clonedSprite;
    }
    */
}
