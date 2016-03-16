package common;

import common.core.ImageSprite;
import common.core.MouseState;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


/**
 * Class for drawing tiles. Extends ImageSpriteClass
 * Holds methods to get tile status and position
 * and sprites for tiles
 * @version $revision $
 */
public class Tile extends ImageSprite {
    private SpriteType type;
    private boolean isActive = true;
    private boolean isDraggable = true;

    /**
     * Constructor to calls ImageSprite Constructor to draw tile
     * @see ImageSprite#ImageSprite(Image, Vector2, double, double)
     * @param type Type of sprite based on selection
     * @param width Width of tile to be drawn
     * @param height Height of tile to be drawn
     * @param position (x,y ) coordinates for position at which tile will be drawn
     */
    public Tile(SpriteType type, double width, double height, Vector2 position) {
        super(Settings.BACKGROUND_TILE_IMAGE, position);
        this.type = type;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructor to calls ImageSprite Constructor to draw tile
     * @see ImageSprite#ImageSprite(Image, Vector2)
     * @param image Sprite Image for tile
     * @param position Position at which tile will be drawn
     */
    public Tile(Image image, Vector2 position){
        super(image, position);
    }

    /**
     * Method to draw tiles
     * @param gc The context to draw on
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (type == SpriteType.SCENERY || type == SpriteType.PATH ||
                type == SpriteType.ENTRY_POINT || type == SpriteType.EXIT_POINT) {
            Vector2 imageOffset = getImageOffset();
            gc.drawImage(this.getImage(), imageOffset.getX(), imageOffset.getY(), this.width, this.height,
                    position.getX(), position.getY(), this.width, this.height);
        }
        /*else {
            // background
            gc.setFill(getFillColor());
            gc.fillRect(position.getX(), position.getY(), width, height);

            // stroke
            //gc.setLineWidth(2);
            gc.setStroke(getStrokeColor());
            gc.strokeRect(position.getX(), position.getY(), width, height);

            // text
            gc.setFont(new Font(20));
            gc.setFill(getTextColor());
            gc.fillText(getText(), position.getX() + 10, position.getY() + 22);
        }*/
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void update() {
    }

    public void update(MouseState mouseState) {
    }
    /**
    * Indicates whether a tile is draggable
    * @return true/false
    */
    public boolean isDraggable() {
        return isDraggable;
    }
    /**
    * Sets isDraggable as draggable
    * @param draggable value which is to be set
    */
    public void setDraggable(boolean draggable) {
        isDraggable = draggable;
    }
    /**
    * Indicates whether a tile is active
    * @return true/false
    */
    public boolean isActive() {
        return isActive;
    }
    /**
    * Sets isActive as active
    * @param active value which is to be set
    */
    public void setActive(boolean active) {
        isActive = active;
    }
    /**
    * Method to copy a tile
    * @param position position at which the tile is to be copied
    * @return Newly copied tile
    */
    public Tile copy(Vector2 position) {
        return new Tile(this.type, this.width, this.height, position);
    }
    /**
    * Returns sprite
    * @return Type of sprite
    */
    public SpriteType getType() {
        return type;
    }
    /**
    * Method to get image offsets of different Sprites
    * @return Vector@ postion of Sprite
    */
    public Vector2 getImageOffset() {
        if (type == SpriteType.SCENERY) {
            return new Vector2(0, 64);
        }
        else if (type == SpriteType.PATH) {
            return new Vector2(0, 0);
        }
        else if (type == SpriteType.ENTRY_POINT) {
            if (isActive()) {
                return new Vector2(64, 32);
            }
            else {
                return new Vector2(0, 96);
            }
        }
        else if (type == SpriteType.EXIT_POINT) {
            if (isActive()) {
                return new Vector2(64, 64);
            }
            else {
                return new Vector2(32, 96);
            }
        }

        return Vector2.getZero();
    }
}
