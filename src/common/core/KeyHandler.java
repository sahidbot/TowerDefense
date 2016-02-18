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
 * The class serves as an event handler to respond to presedkeys
 * it stores pressed keys using a map
 * @author Team 7
 * @version $revision $
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

    /**
     * Checks if a key is pressed
     * @param key is the pressed key
     * @return the key
     */
    public boolean isKeyPressed(KeyCode key) {
        return pressedKyes.containsKey(key);
    }

    /**
     * Gets the pressed keys
     * @return the pressed keys
     */
    public List<KeyCode> getPressedKeys() {
        List<KeyCode> keys = new ArrayList<KeyCode>();
        for (Map.Entry<KeyCode, Boolean> entry : pressedKyes.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }
}
