package common.core;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Sahidul Islam
 */
public abstract class Sprite {
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

    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getCenterX() {
        return position.getX() + width * 0.5;
    }

    public double getCenterY() {
        return position.getY() + height * 0.5;
    }

    public boolean collidesWith(Sprite otherSprite) {
        // not per-pixel-collision
        return (otherSprite.position.getX() + otherSprite.getWidth() >= position.getX() &&
                otherSprite.position.getY() + otherSprite.getHeight() >= position.getY() &&
                otherSprite.position.getX() <= position.getX() + width &&
                otherSprite.position.getY() <= position.getY() + height);
    }

    public void kill() {
        health = 0;
    }


    public abstract void draw(GraphicsContext gc);

    public abstract void update();
}
