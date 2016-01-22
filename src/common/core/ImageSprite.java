package common.core;

import javafx.scene.image.Image;

/**
 * Created by Sahidul Islam
 */
public abstract class ImageSprite extends Sprite {
    protected Image image;

    public ImageSprite(Image image, Vector2 position) {
        this(image, position, null, 0, 0);
    }

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
