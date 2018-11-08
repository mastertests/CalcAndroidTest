package com.test;

import com.test.apps.Apps;
import com.test.base.BaseTest;
import com.test.util.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTest extends BaseTest {

    @Test(priority = 1)
    public void testCalculatorSum() {

        Apps.calculatorApp().calculateSum(Constants.FIRST_NUMBER, Constants.SECOND_NUMBER);

        Assert.assertEquals(Apps.calculatorApp().getCalculateResult(), Constants.EXPECTED_RESULT);
    }
}
