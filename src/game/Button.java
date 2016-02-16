package game;

import common.Settings;
import common.core.ImageSprite;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Dele
 * Represents the button and holds the button sprite.
 */
public class Button extends ImageSprite {
    public ButtonType buttonType;


    /**
     * Default constructor
     *
     * @param buttonType Represents the type of button
     * @param position   Represents the position of the Sprite
     */
    public Button(ButtonType buttonType, Vector2 position) {
        this(imageFrom(buttonType), position);
        this.buttonType = buttonType;
    }


    /**
     * Private constructor that calls super (with an image)
     *
     * @param buttonImage Image to be passed to base class
     * @param position   Position of the sprite on the window;
     * @see Vector2
     */
    private Button(Image buttonImage, Vector2 position) {
        super(buttonImage, position);
    }

    /***
     * Method that translates from ButtonType to Image
     *
     * @param buttonType Button type to be converted
     * @see ButtonType
     */
    private static Image imageFrom(ButtonType buttonType) {
        Image buttonImage;
        switch (buttonType) {
            case SELL:
                buttonImage = Settings.SELLBUTTON_TILE_IMAGE;
                break;
            case UPGRADE:
                buttonImage = Settings.UPGRADEBUTTON_TILE_IMAGE;
                break;

            default:
                throw new NotImplementedException();
        }
        return buttonImage;
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), getPosition().getX(), getPosition().getY());
    }

    @Override
    public void update() {

    }

}
