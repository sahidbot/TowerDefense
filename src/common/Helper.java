package common;

import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Sahidul Islam.
 */
public class Helper {
    public static boolean checkValidBoundaries(int x, int y, int dx, int dy) {
        if (x < 0 || x > dx || y < 0 || y > dy) {
            return false;
        }
        return true;
    }

    public static void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }
}
