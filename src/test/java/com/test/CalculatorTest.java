package com.test;

import com.test.apps.Apps;
import com.test.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CalculatorTest extends BaseTest {

    @Test(priority = 1)
    @Parameters({"firstTestNumber", "secondTestNumber", "expectedTestResult"})
    public void testCalculatorSum(int firstTestNumber, int secondTestNumber, int expectedTestResult) {

        Apps.calculatorApp().calculateSum(firstTestNumber, secondTestNumber);

        Assert.assertEquals(Apps.calculatorApp().getCalculateResult(), expectedTestResult);
    }
}
