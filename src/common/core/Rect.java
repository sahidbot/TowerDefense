package common.core;

/**
 * Representation of a rectangle
 */
public class Rect {
    private Vector2 position;
    private double width;
    private double height;

    /**
     * Default constructor
     *
     * @param position Position of the rectangle
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     */
    public Rect(Vector2 position, double width, double height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }
    /**
     * Get the value of width.
     * @return Returns the width of sprite.
     */
    public double getWidth() {
        return width;
    }
    /**
     * Get the value of height.
     * @return Returns the height of sprite.
     */
    public double getHeight() {
        return height;
    }
    /**
     * Get the position of sprite of height.
     * @return Returns the position as Vector2 coordinates.
     */
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "X: " + position.getX() + ", " +
                "Y: " + position.getY() + ", " +
                "W: " + width + ", " +
                "H: " + height;
    }
}
