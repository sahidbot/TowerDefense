package common.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.UUID;

/**
 *This is the base sprites class, it stores width, height, position, health, damage and move state of the sprite.
 * @author Team 7
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

    public boolean canMove = false;

    public Sprite(double width, double height, Vector2 position) {
        this(width, height, position, null, 0, 0);
    }

    public Sprite(double width, double height, Vector2 position, double health, double damage) {
        this(width, height, position, null, health, damage);
    }

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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.setFromVector(position);
    }

    public double getCenterX() {
        return position.getX() + width * 0.5;
    }

    public double getCenterY() {
        return position.getY() + height * 0.5;
    }

    public String getUniqueId() {
        return this.uniqueId.toString();
    }

    public void generateNewUniqueId() {
        this.uniqueId = UUID.randomUUID();
    }

    public boolean collidesWith(Sprite otherSprite) {
        // not per-pixel-collision
        return (otherSprite.position.getX() + otherSprite.getWidth() >= this.position.getX() &&
                otherSprite.position.getY() + otherSprite.getHeight() >= this.position.getY() &&
                otherSprite.position.getX() <= this.position.getX() + this.width &&
                otherSprite.position.getY() <= this.position.getY() + this.height);
    }

    public boolean collidesWith(Vector2 position) {
        return (position.getX() >= this.position.getX() &&
                position.getY() >= this.position.getY() &&
                position.getX() <= this.position.getX() + this.width &&
                position.getY() <= this.position.getY() + this.height);
    }

    /**
     * This method kills a sprite by changing its health to 0
     */
    public void kill() {

        health = 0;
    }

    public abstract void draw(GraphicsContext gc);

    public abstract void update();

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
