package game;

import common.Settings;
import common.SpriteType;
import common.Tile;
import common.core.Rect;
import common.core.Vector2;
import game.pathlogic.PathFinder;
import game.towerlogic.Tower;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manager class for the critters.
 */
public class CritterManager {
    private ArrayList<Critter> critters;
    private LinkedList<Tile> pathList;
    private Vector2 exitPointGridPosition;

    private int rows, columns;
    int crittersPassed = 0;
    int maxCrittersPassed = 10;

    private int rewards = 0;

    /**
     * Default Constructor
     *
     * @param pathFinder Represent the path for critter movement
     */
    public CritterManager(PathFinder pathFinder) {
        pathList = pathFinder.getPaths();
        critters = new ArrayList<>();

        this.rows = pathFinder.rows;
        this.columns = pathFinder.columns;

        exitPointGridPosition =
                GameManager.getInstance().tileManager.getTilePosition(pathList.getLast().getPosition());
    }

    /**
     * Get the critter that are in tower's shooting range.
     *
     * @return Returns the list of Critters
     */
    public ArrayList<Critter> getShootableCritters(Tower tower) {
        ArrayList<Critter> results = new ArrayList<>();

        for (Critter critter : critters) {
            if (critter.collidesWith(tower.getRangeRect())) {
                results.add(critter);
            }
        }

        return results;
    }

    /**
     * Get the critter close to shooting range.
     *
     * @return Returns the list of critters.
     */
    public ArrayList<Critter> getCritterNeighbours(Tower tower, ArrayList<Critter> critters, Critter selCritter) {
        ArrayList<Critter> results = new ArrayList<>();

        // calculate critter's center position
        Vector2 critterPosition = new Vector2();
        critterPosition.setFromVector(selCritter.getPosition());
        critterPosition.setX(critterPosition.getX() + (selCritter.getWidth() / 2));
        critterPosition.setY(critterPosition.getY() + (selCritter.getHeight() / 2));

        // create splash area
        double splashRange = 96;
        Rect splashArea = new Rect(new Vector2(
                critterPosition.getX() - splashRange,
                critterPosition.getY() - splashRange
        ), splashRange * 2, splashRange * 2);

        // check collision
        for (Critter critter : critters) {
            if (critter.collidesWith(splashArea)) {
                results.add(critter);
            }
        }

        return results;
    }

    /**
     * Get the reward for shot critter.
     *
     * @return Returns an integer as the reward
     */
    public int getRewards() {
        int temp = rewards;
        rewards = 0;

        return temp;
    }

    /**
     * Method for starting the wave
     */
    public void startWave() {
        critters.clear();
        crittersPassed = 0;

        int min = 30;
        int max = 50;

        int randomNum = ThreadLocalRandom.current().nextInt((max - min) + 1) + min;

        Tile start = pathList.get(0);
        Tile second = pathList.get(1);

        boolean isVerticalSpawn = start.getPosition().getY() !=
                second.getPosition().getY();

        double dx = 1;
        double dy = 1;

        if (second.getPosition().getX() < start.getPosition().getX())
            dx = -1;
        else if (second.getPosition().getY() < start.getPosition().getY())
            dy = -1;

        Vector2 position = new Vector2();
        position.setFromVector(start.getPosition());

        for (int i = 0; i < randomNum; i++) {
            Critter critter = new Critter(position, CritterType.AIR);
            critters.add(critter);

            if (isVerticalSpawn) {
                position = new Vector2(position.getX(), position.getY() - dy * critter.getHeight());
            }
            else {
                position = new Vector2(position.getX() - dx * critter.getWidth(), position.getY());
            }
        }
    }

    /**
     * Method responsible for updating the Critter
     */
    public void update(double delta) {
        ArrayList<Critter> crittersToBeDeleted = new ArrayList<>();

        for (int i = 0; i < critters.size(); i++) {
            Critter critter = critters.get(i);

            // dead logic
            if (critter.getHealthPoints() <= 0) {
                crittersToBeDeleted.add(critter);
                rewards += critter.getGoldValue();
            }

            // critters update
            critter.update(delta);

            // critter movement
            if (critter.canMove()) {
                moveCritter(critter, delta);
            }

            // check if critter reached to end
            // game end logic
            if (isReachedToExitPoint(critter)) {
                crittersPassed++;
                crittersToBeDeleted.add(critter);

                rewards -= critter.getGoldValue();
                int newGold = GameManager.getInstance().sideBar.getAvailableGold() + rewards;

                if (crittersPassed == maxCrittersPassed ||
                        newGold <= 0) {
                    GameManager.getInstance().endGame();
                }
            }
        }

        // iterate to delete
        for (Critter critter : crittersToBeDeleted) {
            critters.remove(critter);
        }

        // wave stop logic if critters are dead
        if (critters.size() == 0) {
            GameManager.getInstance().endWave();
        }

        // update the currency
        GameManager.getInstance().sideBar.addAvailableGold(getRewards());
    }

    /**
     * Method for drawing Critter to canvas
     */
    public void draw(GraphicsContext gc) {
        for (Critter critter : critters) {
            Vector2 position = critter.position;
            double x = position.getX() > 0 ? Math.ceil(position.getX() / Settings.TILE_WIDTH) : 0;
            double y = position.getY() > 0 ? Math.ceil(position.getY() / Settings.TILE_HEIGHT) : 0;

            if (x <= columns && y < rows)
                critter.draw(gc);
        }
    }

    /**
     * Method for moving Critter.
     *
     * @param  critter represents the Critter to be moved
     * @param delta represents how much Critter is to be moved
     */
    private void moveCritter(Critter critter, double delta) {
        Vector2 pos = GameManager.getInstance().tileManager.getTilePosition(critter.getPosition());
        Tile nextTile = getNextTile(pos);

        if (nextTile == null)
            return;

        Vector2 nextPos = nextTile.getPosition();
        Vector2 curPos = critter.getPosition();

        //double distanceTraveled = 10 * delta;
        double distanceTraveled = critter.getSpeed();
        double newX = curPos.getX();
        double newY = curPos.getY();

        if (nextPos.getX() > curPos.getX())
            newX += distanceTraveled;
        else
            newX -= distanceTraveled;

        if (nextPos.getY() > curPos.getY())
            newY += distanceTraveled;
        else
            newY -= distanceTraveled;

        critter.getPosition().setXY(newX, newY);
    }

    /**
     * Get the next tile .
     *
     * @param pos represent the position of the
     * @return Returns the Tile
     */
    private Tile getNextTile(Vector2 pos) {
        for (int i = 0; i < pathList.size(); i++) {
            Tile tile = pathList.get(i);
            if (tile.getType() != SpriteType.EXIT_POINT) {
                Vector2 tilePos = GameManager.getInstance().tileManager.getTilePosition(tile.getPosition());
                if (pos.equals(tilePos) && i < pathList.size() - 1)
                {
                    return pathList.get(i + 1);
                }
            }
        }

        return pathList.get(0);
    }

    /**
     * Method checks if a Critter has reached the exit point of the path.
     *
     * @param critter is the Critter to be checked
     * @return Returns a boolean value has the result
     */
    private boolean isReachedToExitPoint(Critter critter) {
        Vector2 critterPos =
                GameManager.getInstance().tileManager.getTilePosition(critter.getPosition());

        if (exitPointGridPosition.equals(critterPos)) {
            return true;
        }

        return false;
    }

    // end logic
    // damage per second
    // frozen duration
}
