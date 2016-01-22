package common;

import common.core.ImageSprite;
import common.core.MouseState;
import common.core.Sprite;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Created by Sahidul Islam
 */
public class Tile extends ImageSprite {
    private SpriteType type;

    public Tile(SpriteType type, double width, double height, Vector2 position) {
        super(Settings.BACKGROUND_TILE_IMAGE, position);
        this.type = type;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (type == SpriteType.SCENERY || type == SpriteType.PATH) {
            Vector2 imageOffset = getImageOffset();
            gc.drawImage(this.getImage(), imageOffset.getX(), imageOffset.getY(), this.width, this.height,
                    position.getX(), position.getY(), this.width, this.height);
        }
        else {
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
        }
    }

    @Override
    public void update() {
    }

    public void update(MouseState mouseState) {
    }

    public Tile copy(Vector2 position) {
        return new Tile(this.type, this.width, this.height, position);
    }

    public SpriteType getType() {
        return type;
    }

    public Vector2 getImageOffset() {
        if (type == SpriteType.SCENERY) {
            return new Vector2(0, 64);
        }
        else if (type == SpriteType.PATH) {
            return new Vector2(0, 0);
        }

        return Vector2.getZero();
    }

    private Color getFillColor() {
        Color color;

        switch (type) {
            case SCENERY:
            case PATH:
            case ENTRY_POINT:
            case EXIT_POINT:
                color = Color.web("#1c1a1a");
                break;
            default:
                color = Color.BLACK;
        }

        return color;
    }

    private Color getStrokeColor() {
        Color color;

        switch (type) {
            case SCENERY:
            case PATH:
            case ENTRY_POINT:
            case EXIT_POINT:
                color = Color.WHITE;
                break;
            default:
                color = Color.BLACK;
        }

        return color;
    }

    private Color getTextColor() {
        Color color;

        switch (type) {
            case SCENERY:
            case PATH:
            case ENTRY_POINT:
            case EXIT_POINT:
                color = Color.RED;
                break;
            default:
                color = Color.BLACK;
        }

        return color;
    }

    private String getText() {
        String text;

        switch (type) {
            case SCENERY:
                text = "X";
                break;
            case PATH:
                text = "P";
                break;
            case ENTRY_POINT:
                text = "S";
                break;
            case EXIT_POINT:
                text = "E";
                break;
            default:
                text = "";
        }

        return text;
    }
}
