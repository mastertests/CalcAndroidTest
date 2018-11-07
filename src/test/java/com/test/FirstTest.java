package com.test;

import com.test.apps.Apps;
import com.test.base.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class FirstTest extends BaseTest {

    private AppiumDriver driver;

    @Test
    public void installAppFromPlayStore() throws Exception {

        Apps.playMarketApp().setDriver(super.driver);
        Apps.playMarketApp().getSearchResult(testAppName);
        Apps.playMarketApp().installApp(testAppName, testAppPackage);
    }

    @AfterMethod
    public void setNewDriver() throws MalformedURLException {

        super.driver.quit();

        this.driver = new AndroidDriver(new URL(hubUrl), installedAppCaps());
    }

    @Test(priority = 1)
    public void calculatorTest() {

        Apps.calculatorApp().setDriver(driver);
        Apps.calculatorApp().setFormula();

        Assert.assertEquals(Apps.calculatorApp().getResult(), 420);
    }
}
