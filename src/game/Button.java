package game;

import common.Settings;
import common.core.ImageSprite;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Dele on 2/14/2016.
 * Represents the tower and holds the tower sprite.
 */
public class Button extends ImageSprite {
    public ButtonType buttonType;
    private boolean isActive;


    /**
     * Default constructor
     *
     * @param buttonType Represents the type of tower
     * @param position   Represents the position of the Sprite
     */
    public Button(ButtonType buttonType, Vector2 position) {
        this(imageFrom(buttonType), position);
        this.buttonType = buttonType;
        isActive = false;
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
     * Method that translates from TowerType to Image
     *
     * @param buttonType Tower type to be converted
     * @see TowerType
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
