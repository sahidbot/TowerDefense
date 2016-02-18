package game;

import common.Settings;
import common.core.ImageSprite;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class related to Sprite for
 * no or less money to buy towers
 * @version $revision $
 */
public class NoMoneySprite extends ImageSprite {
    /**
     * Default constructor
     *
     * @param position position of the sprite
     */
    public NoMoneySprite(Vector2 position) {
        super(Settings.NOMONEY_TILE_IMAGE, position);
    }

    /**
     * Drawing the sprite
     *
     * @param gc {@link GraphicsContext} to draw on
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this.getImage(), position.getX(), position.getY());
    }

    @Override
    public void update() {

    }
}
