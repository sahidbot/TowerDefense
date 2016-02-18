package common.core;

import javafx.scene.image.Image;

/**
 * This class creates image sprite and extends the Sprite class
 * The class is also responsible for checking if collision occurs with the image
 * @version $revision $
 */
public abstract class ImageSprite extends Sprite {
    protected Image image;

    /**
     * This constructor is used to create a sprite on the grid
     * @param image: holds the image sprite of type Image
     * @param position: stores the position(type Vector2) of the image on the grid
     */
    public ImageSprite(Image image, Vector2 position) {
        this(image, position, null, 0, 0);
    }

    /**
     *Overridden constructor mainly for the purpose of creating a Tower
     * @param health: holds the health/lives of the Tower
     * @param damage: hold the level of damage of a Tower
     */
    public ImageSprite(Image image, Vector2 position, double health, double damage) {
        this(image, position, null, health, damage);
    }

    public ImageSprite(Image image, Vector2 position, Vector2 velocity, double health, double damage) {
        super(image.getWidth(), image.getHeight(), position, velocity, health, damage);
        this.image = image;
    }

    public boolean collidesWith(ImageSprite otherSprite) {
        // not per-pixel-collision
        return super.collidesWith(otherSprite);
    }

    public Image getImage() {
        return this.image;
    }
}
