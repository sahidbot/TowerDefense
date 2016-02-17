package common;

import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class helps to abstract methods the are commonly used through out all classes
 */
public class Helper {
    /**
     * Check the valid boundaries for giver X, Y coordinates.
     *
     * @param x X value of the position.
     * @param y Y value of the position.
     * @param dx X value of the boundary.
     * @param dy Y value of the boundary.
     * @return True for valid and false for invalid.
     */
    public static boolean checkValidBoundaries(int x, int y, int dx, int dy) {
        if (x < 0 || x > dx || y < 0 || y > dy) {
            return false;
        }
        return true;
    }

    /**
     * Draw text on the canvas.
     *
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
