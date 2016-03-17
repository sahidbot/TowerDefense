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
                    delta = ((double) now - (double) lastTime) / 1000000000.0; // second
                    lastTime = now;

                    update(delta);
                    clear();
                    draw();
                }
            };
        }

        timer.start();
    }

    /**
     * Method for updating game and map
     *
     * @param delta represents the time difference
     */
    protected abstract void update(double delta);

    /**
     * Method for drawing objects
     */
    protected abstract void draw();

    /**
     * Method for clearing objects
     */
    protected abstract void clear();
}
