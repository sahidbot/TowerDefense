package test.common;

import common.Helper;
import javafx.scene.control.TextField;
import mainmenu.controllers.CreateMapDialogController;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

/**
 * Created by saddamtahir on 2016-03-17.
 */
public class HelperTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void CheckNumericValueTest_NumericValues() {
        CreateMapDialogController menu = new CreateMapDialogController();
        TextField testTextField = new TextField();
        Assert.assertTrue(menu.checkNumericValue(testTextField, "5", "6"));
    }

    @Test
    public void CheckNumericValueTest_NonNumericValues() {
        CreateMapDialogController menu = new CreateMapDialogController();
        TextField testTextField = new TextField();
        Assert.assertFalse(menu.checkNumericValue(testTextField, "1", "r"));
    }

    @Test
    public void CheckValidBoundariesTest_ValidBoundaries() {
        Assert.assertTrue(Helper.checkValidBoundaries(5, 2, 20, 25));
    }

    @Test
    public void CheckValidBoundaries_InvalidBoundaries() {
        Assert.assertFalse(Helper.checkValidBoundaries(10, 2, 5, 25));
    }

}
