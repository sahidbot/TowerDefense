package common;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sahidul Islam.
 */
public class SoundManager {
    ExecutorService soundPool = Executors.newFixedThreadPool(2);
    Map<String, AudioClip> soundEffectsMap = new HashMap<String, AudioClip>();

    public SoundManager(int numberOfThreads) {
        soundPool = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void loadSoundEffects(String id, URL url) {
        AudioClip sound = new AudioClip(url.toExternalForm());
        soundEffectsMap.put(id, sound);
    }

    public void playSound(final String id) {
        Runnable soundPlay = new Runnable() {
            @Override
            public void run() {
                soundEffectsMap.get(id).play();
            }
        };
        soundPool.execute(soundPlay);
    }

    public void shutdown() {
        soundPool.shutdown();
    }
}
