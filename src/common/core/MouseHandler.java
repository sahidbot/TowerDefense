package common.core;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Observable;

/**
 * Serves as the model for the mouse state which changes from time to time
 * Its hold the mouseState to be notified for changes
 * @version $revision $
 */
public class MouseHandler extends Observable {
    private MouseState mouseState;

    /**
     * constructor to initialize mouse state
     * implements mouse state as observable
     * notify observers about change in mouse state
     * @param scene of type scene
     */
    public MouseHandler(Scene scene) {
        this.mouseState = new MouseState();

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    mouseState.setPosition(event.getX(), event.getY());
                    mouseState.setEventType(MouseEventType.LEFT_CLICK);
                    setChanged();
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    mouseState.setPosition(event.getX(), event.getY());
                    mouseState.setEventType(MouseEventType.RIGHT_CLICK);
                    setChanged();
                }

                notifyObservers(mouseState);
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseState.setPosition(event.getX(), event.getY());
                mouseState.setEventType(MouseEventType.MOVE);
                setChanged();
                notifyObservers(mouseState);
            }
        });
    }

    /**
     * gets the mouseState
     * @return current mouseState
     */
    public MouseState getMouseState() {
        return this.mouseState;
    }

    /**
     * clears the mouseState position
     */
    public void clearMouseState() {
        this.mouseState.clearPosition();
    }
}

/*
 * Mouse cursor in JavaFX
 * https://blog.idrsolutions.com/2014/05/tutorial-change-default-cursor-javafx/
 */