package com.test;

import com.test.apps.Apps;
import com.test.base.BaseTest;
import com.test.util.Constants;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CalculatorTest extends BaseTest {

    @Test
    public void installAppFromPlayStore() throws Exception {

        Apps.playMarketApp().getSearchResult(testAppName);
        Apps.playMarketApp().installApp(testAppName, testAppPackage);
    }

    @AfterMethod
    public void setInstalledAppDriver() throws MalformedURLException {

        driver.quit();

        driver = new AndroidDriver(new URL(hubUrl), installedAppCaps());
    }

    @Test(priority = 1)
    public void testCalculatorSum() {

        Apps.calculatorApp().calculateSum(400, 20);

        Assert.assertEquals(Apps.calculatorApp().getResult(), 420);
    }
}
