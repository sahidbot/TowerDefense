package game;

import common.Settings;
import common.core.ImageSprite;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Represents the button and holds the button sprite.
 */
public class Button extends ImageSprite {
    public ButtonType buttonType;
    private boolean isEnabled;


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

    /**
     * Method to draw button on side bar
     *
     * @param gc The {@link javafx.scene.canvas.GraphicsContext} to use. All graphics will be placed here
     */
    @Override
    public void draw(GraphicsContext gc) {
        if(isEnabled())
        gc.drawImage(this.getImage(), getPosition().getX(), getPosition().getY());
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void update() {

    }

    /**
     * @return the value of enabled
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * @param enabled is the new value
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
