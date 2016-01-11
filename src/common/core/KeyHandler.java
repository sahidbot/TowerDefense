package common.core;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Sahidul Islam.
 */
public class KeyHandler {
    private Map<KeyCode, Boolean> pressedKyes;

    public KeyHandler(Scene scene) {
        pressedKyes = new Hashtable<KeyCode, Boolean>();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                pressedKyes.put(event.getCode(), true);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (pressedKyes.containsKey(event)) {
                    pressedKyes.remove(event);
                }
            }
        });
    }

    public boolean isKeyPressed(KeyCode key) {
        return pressedKyes.containsKey(key);
    }

    public List<KeyCode> getPressedKeys() {
        List<KeyCode> keys = new ArrayList<KeyCode>();
        for (Map.Entry<KeyCode, Boolean> entry : pressedKyes.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }
}
