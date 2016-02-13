package common.core;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Sahidul Islam.
 */
public class MouseHandler {
    private MouseState mouseState = new MouseState();
    private Scene scene;

    public MouseHandler(Scene scene) {
        this.scene = scene;

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    mouseState.setLeftClickPosition(event.getX(), event.getY());
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    mouseState.setRightClickPosition(event.getX(), event.getY());
                }
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseState.setMousePosition(event.getX(), event.getY());
            }
        });
    }

    public MouseState getMouseState() {
        return this.mouseState;
    }

    public void clearMouseState() {
        //this.mouseState.setMousePosition(0, 0);
        this.mouseState.setLeftClickPosition(0, 0);
        this.mouseState.setRightClickPosition(0, 0);
    }
}

/*
 * Mouse cursor in JavaFX
 * https://blog.idrsolutions.com/2014/05/tutorial-change-default-cursor-javafx/
 */