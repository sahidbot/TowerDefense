package common.core;

import javafx.animation.AnimationTimer;

/**
 * This class controls the game by monitoring the game timer
 * holds a timer of type AnimationTimer, running state, lastTime and delta
 * @version $revision $
 */
public abstract class GameLoop {
    private AnimationTimer timer;
    private boolean running = false;
    private long lastTime = 0;
    private double delta = 0;

    /**
     * This method starts the game and changes the running state to true
     */
    public synchronized void start() {
        if (running) return;

        running = true;

        if (timer == null) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    delta = (now - lastTime) * 1000000 * 1000; // second
                    lastTime = now;

                    update(delta);
                    clear();
                    draw();
                }
            };
        }

        timer.start();
    }

    protected abstract void update(double delta);
    protected abstract void draw();
    protected abstract void clear();
}
