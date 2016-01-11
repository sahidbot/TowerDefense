package common.core;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Sahidul Islam.
 */
public class MouseHandler {
    public Vector2 leftClickPosition = Vector2.zero();
    public Vector2 rightClickPosition = Vector2.zero();
    public Vector2 mousePosition = Vector2.zero();

    public MouseHandler(Scene scene) {
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    leftClickPosition.setX(event.getX());
                    leftClickPosition.setY(event.getY());
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    rightClickPosition.setX(event.getX());
                    rightClickPosition.setY(event.getY());
                }
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePosition.setX(event.getX());
                mousePosition.setY(event.getY());
            }
        });
    }
}
