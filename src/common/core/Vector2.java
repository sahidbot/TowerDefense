package common.core;

/**
 * Represents a displacement in 2-D space.
 */
public class Vector2 {

    private double x;
    private double y;

    /**
     * Initializes a new instance of the Vector structure.
     *
     * @param x The Vector.x-offset of the new Vector.
     * @param y The Vector.y-offset of the new Vector.
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the value of X.
     * @return Returns the X value of the vector.
     */
    public double getX() {
        return x;
    }

    /**
     * Set the value of X.
     * @param x The value to set.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get the value of Y.
     * @return Returns the Y value of the vector.
     */
    public double getY() {
        return y;
    }

    /**
     * Set the value of Y.
     * @param y The value to set.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Set the value of X and Y.
     * @param x The value to set.
     * @param y The value to set.
     */
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the value of X and Y from vector.
     * @param vector The vector to set.
     */
    public void setFromVector(Vector2 vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    /**
     * Creates a zero vector.
     *
     * @return  A vector with zero value of Vector.x and Vector.y.
     */
    public static Vector2 getZero() {
        return new Vector2(0, 0);
    }

    /**
     * Gets the length of this vector.
     *
     * @return Returns the square of the length of this vector.
     */
    public double getLength() {
        return x * x + y * y;
    }

    /**
     * Normalizes this vector.
     */
    public void normalize() {
        double length = this.getLength();
        x /= length;
        y /= length;
    }

    /**
     * Adds two vectors.
     *
     * @param vector   The vector to add.
     */
    public void add(Vector2 vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    /**
     * Adds scalar to the current vector.
     *
     * @param scalar   The scalar to add.
     */
    public void add(double scalar) {
        this.x += scalar;
        this.y += scalar;
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector   The vector to subtract.
     */
    public void subtract(Vector2 vector) {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    /**
     * Subtracts scalar from the current vector.
     *
     * @param scalar   The scalar to subtract.
     */
    public void subtract(double scalar) {
        this.x -= scalar;
        this.y -= scalar;
    }

    /**
     * Calculates dot product of two vectors.
     *
     * @param vector   The vector to multiply.
     */
    public void multiply(Vector2 vector) {
        this.x *= vector.x;
        this.y *= vector.y;
    }

    /**
     * Multiply scalar to the current vector.
     *
     * @param scalar   The scalar to multiply.
     */
    public void multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    /**
     * Divides the current vector by the specified scalar.
     *
     * @param scalar   The scalar to divide.
     */
    public void divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
    }

    /**
     * Negates this vector.
     */
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    /**
     * Indicate whether the vector has zero value or not.
     * @return Returns true if vector is zero otherwise false.
     */
    public boolean isZero() {
        return x == 0 & y == 0;
    }

    /**
     * Compares two vectors for equality.
     *
     * @param vector The vector to compare with this vector.
     * @return true if value has the same Vector.x and Vector.y values as this vector; otherwise, false.
     */
    public boolean equals(Vector2 vector) {
        return (this.x == vector.x && this.y == vector.y);
    }

    /**
     * Returns the string representation of this Vector.
     *
     * @return A string that represents the Vector.x and Vector.y values of this Vector.
     */
    @Override
    public String toString() {
        return String.valueOf(x) + "," + String.valueOf(y);
    }
}
