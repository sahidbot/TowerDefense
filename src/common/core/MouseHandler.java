package common.core;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Observable;

/**
 * Created by Sahidul Islam.
 */
public class MouseHandler extends Observable {
    private MouseState mouseState;

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

    public MouseState getMouseState() {
        return this.mouseState;
    }

    public void clearMouseState() {
        this.mouseState.clearPosition();
    }
}

/*
 * Mouse cursor in JavaFX
 * https://blog.idrsolutions.com/2014/05/tutorial-change-default-cursor-javafx/
 */