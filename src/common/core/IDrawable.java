package common.core;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface for drawable
 */
public interface IDrawable {
    /**
     * draws itself using the {@link GraphicsContext}
     * @param gc What will be used to draw
     */
    void draw(GraphicsContext gc);
}
