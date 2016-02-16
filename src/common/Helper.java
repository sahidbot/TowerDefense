package common;

import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class helps to abstract methods the are commonly used through out all classes
 * Created by Sahidul Islam.
 */
public class Helper {
    /**
     * @param positon is the x and y cordinate of a sprite
     * @param boundary is the upperbound
     * @return false
     */
    public static boolean CheckBound(Vector2 positon, Vector2 boundary) {
        return false;
    }

    /**
     * @param gc is the graphic context
     * @param text is a text
     * @param position is the text position on canvas
     * @param color is the text color
     */
    public static void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }
}
