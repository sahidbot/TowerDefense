package game;

import common.*;
import common.core.GameLoop;
import common.core.MouseHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class GameManager extends GameLoop {
    public TileManager tileManager;
    public ScoreManager scoreManager;
    public MouseHandler mouseHandler;

    private GraphicsContext gc;
    private double width;
    private double height;

    public GameManager(GraphicsContext gc, Scene scene) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        mouseHandler = new MouseHandler(scene);

        tileManager = new TileManager();
        tileManager.createScenery(12, 12);

        scoreManager = new ScoreManager(tileManager.getWidth(), 0);
    }

    @Override
    protected void update(double delta) {
        scoreManager.setHealth(scoreManager.getHealth() + 1);
        scoreManager.setMoney(scoreManager.getMoney() + 1);
        scoreManager.updateMousePosition(mouseHandler.getMouseState());
    }

    @Override
    protected void clear() {
        mouseHandler.clearMouseState();
        gc.clearRect(0, 0, width, height);
    }

    @Override
    protected void draw() {
        tileManager.draw(gc);
        scoreManager.draw(gc);
    }
}
