package common.core;

/**
 * The state of the mouse event.
 */
public class MouseState {
    private MouseEventType eventType = MouseEventType.NONE;
    private Vector2 position = Vector2.getZero();
    private Sprite selectedSprite = null;

    /**
     * Gets the type of the mouse event.
     *
     * @return Returns the type of the mouse event
     */
    public MouseEventType getEventType() {
        return this.eventType;
    }

    /**
     * Sets the mouse event type.
     *
     * @param eventType Type of the mouse event.
     */
    public void setEventType(MouseEventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the position of the mouse event.
     *
     * @return Returns the position of the mouse event
     */
    public Vector2 getPosition() {
        return this.position;
    }

    /**
     * Updates the current position with new value.
     *
     * @param position Vector of the new position.
     */
    public void setPosition(Vector2 position) {
        this.position.setFromVector(position);
    }

    /**
     * Updates the current position with new value.
     *
     * @param x X value of the vector.
     * @param y Y value of the vector.
     */
    public void setPosition(double x, double y) {
        this.position.setXY(x, y);
    }

    /**
     * Clear the current state.
     */
    public void clearPosition() {
        this.position.setXY(0, 0);
        this.eventType = MouseEventType.NONE;
    }

    public Sprite getSelectedSprite() {
        return selectedSprite;
    }

    public <T extends Sprite> T getSelectedSprite(Class<T> type) {
        if (this.selectedSprite != null && type.isInstance(this.selectedSprite))
            return type.cast(this.selectedSprite);

        return null;
    }

    public void clearSelectedSprite() {
        if (this.selectedSprite != null) {
            this.selectedSprite = null;
        }
    }

    public void setSelectedSprite(Sprite selectedSprite) {
        this.selectedSprite = selectedSprite;
    }
}
