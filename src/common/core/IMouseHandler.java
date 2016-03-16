package common.core;

import java.util.Observer;

/**
 * Interface that represents a mouse handler
 */
public interface IMouseHandler {
    /**
     * Current mouse state being left, right, middler or others
     * @return the mouse state value
     */
    MouseState getMouseState();

    /**
     * Flushes or clears the mouse state
     */
    void clearMouseState();

    /**
     * Adds an observer to the mouse
     * @param o Observer to add
     */
    void addObserver(Observer o);
}
