package test.game.pathlogic;

import common.SpriteType;
import common.Tile;
import common.core.Vector2;
import game.pathlogic.Graph;
import game.pathlogic.PathFinder;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import test.JavaFXThreadingRule;

/**
 * Created by saddamtahir on 2016-03-17.
 */
public class PathFinderTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    public Tile[][] leTiles = new Tile[2][2];
    public PathFinder lePath;

    @Before
    public void BeforeTestingSetup()
    {
        leTiles[0][0] = new Tile(SpriteType.PATH,32,32,new Vector2(0,0));
        leTiles[0][1] = new Tile(SpriteType.SCENERY,32,32,new Vector2(32,0));
        leTiles[1][0] = new Tile(SpriteType.PATH, 32,32,new Vector2(0,32));
        leTiles[1][1] = new Tile(SpriteType.SCENERY, 32, 32, new Vector2(32,32));
        lePath = new PathFinder(leTiles,5,5);
    }

    @Test
    public void addEdgeTest_False()
    {
        Graph<Tile> graph = new Graph<Tile>();
        Assert.assertFalse(lePath.addEdge(graph,leTiles[1][1],10,10));
    }

    /*@Test
    public void addEdgeTest_True()
    {
        Graph<Tile> graph = new Graph<Tile>();
        Assert.assertFalse(lePath.addEdge(graph,leTiles[1][1],2,2));
    }*/
}
