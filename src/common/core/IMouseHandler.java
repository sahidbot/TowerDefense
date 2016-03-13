package common.core;

import java.util.Observer;

/**
 * Created by Monster on 3/1/2016.
 */
public interface IMouseHandler {
    MouseState getMouseState();
    void clearMouseState();
    void addObserver(Observer o);
}
