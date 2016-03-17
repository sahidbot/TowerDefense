package test.common.core;

import common.core.Vector2;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

import static org.junit.Assert.assertEquals;

/**
 * Created by Monster on 3/14/2016.
 */
public class Vector2Test {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();


    @Test
    public void MagnitudeTest(){
        //Vector 3,4 always has magnitude 5 (math)
        Vector2 threeFourFiveTriangle = new Vector2(3,4);
        double result = Vector2.Magnitude(threeFourFiveTriangle);

        assertEquals(5, result, 0.001);
    }

    @Test
    public void DistanceTest(){
        //the distance is the same as vector 3,4, Distance is 5
        Vector2 oneOne = new Vector2(1,1);
        Vector2 fourFive = new Vector2(4,5);
        double result = Vector2.Distance(oneOne, fourFive);

        assertEquals(5, result, 0.001);

        //Opperation is commutative
        double result2 = Vector2.Distance(fourFive, oneOne);
        assertEquals(result, result2, 0.001);

    }
}
