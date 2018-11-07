package com.test;

import com.test.apps.Apps;
import com.test.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTest extends BaseTest {

    @Test
    public void installAppFromPlayStore() throws Exception {

        Apps.playMarketApp().getSearchResult(testAppName);
        Apps.playMarketApp().selectApp(testAppName);
        Apps.playMarketApp().installApp(testAppPackage);
    }

    @Test(priority = 1)
    public void testCalculatorSum() {

        Apps.calculatorApp().calculateSum(400, 20);

        Assert.assertEquals(Apps.calculatorApp().getResult(), 420);
    }
}
