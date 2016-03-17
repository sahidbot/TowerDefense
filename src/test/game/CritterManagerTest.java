package test.game;

import common.SpriteType;
import common.Tile;
import common.core.Vector2;
import game.Critter;
import game.CritterManager;
import game.CritterType;
import game.pathlogic.PathFinder;
import game.towerlogic.Tower;
import game.towerlogic.TowerType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

import java.util.ArrayList;

/**
 * Created by saddamtahir on 2016-03-17.
 */
public class CritterManagerTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    CritterManager leCritterManager;
    private Tile[][] leTiles = new Tile[2][3];
    private PathFinder lePath;
    private ArrayList<Critter> leCritters;
    private Tower leTower;

    @Before
    public void BeforeTestingSetup()
    {
        leTiles[0][0] = new Tile(SpriteType.ENTRY_POINT,32,32,new Vector2(0,0));
        leTiles[0][1] = new Tile(SpriteType.PATH,32,32,new Vector2(1,0));
        leTiles[0][2] = new Tile(SpriteType.PATH,32,32,new Vector2(2,0));
        leTiles[0][0] = new Tile(SpriteType.PATH, 32,32,new Vector2(3,0));
        leTiles[0][0] = new Tile(SpriteType.PATH, 32,32,new Vector2(3,0));
        leTiles[0][2] = new Tile(SpriteType.EXIT_POINT, 32, 32, new Vector2(4,0));
        lePath = new PathFinder(leTiles,3,2);
        leCritterManager = new CritterManager(lePath);
        leCritters.add(new Critter(new Vector2(0,0), CritterType.GROUND));
        leCritters.add(new Critter(new Vector2(1,0), CritterType.GROUND));
        leCritterManager.SetCritterList(leCritters);
        leTower = new Tower(TowerType.ARROW, new Vector2(1,2));
    }

    @Test
    public void getShootableCrittersTest()
    {
        ArrayList<Critter> returnList = leCritterManager.getShootableCritters(leTower);
        Assert.assertEquals(returnList.size(),2);
    }
}
