package common.core;

import javafx.animation.AnimationTimer;

public abstract class GameLoop {
    private AnimationTimer timer;
    private boolean running = false;
    private long lastTime = 0;
    private double delta = 0;

    public synchronized void start() {
        if (running) return;

        running = true;

        if (timer == null) {
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    delta = now - lastTime;
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
