package com.test.base;

import com.test.actions.Actions;
import com.test.util.reporter.Reporter;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.URL;

@SuppressWarnings("WeakerAccess")
public class BaseTest {

    public static AndroidDriver driver;

    protected String hubUrl, deviceName, platformName, platformVersion, udid, orientation, noReset;

    protected String testAppName;
    protected String testAppPackage;
    protected String testAppActivity;

    protected String appPackage;
    protected String appActivity;

    @BeforeSuite
    @Parameters({"hubUrl", "deviceName", "platformName", "platformVersion",
            "udid", "orientation", "noReset", "testAppName",
            "testAppPackage", "testAppActivity", "appPackage", "appActivity"})
    public void setParameters(String hubUrl, String deviceName, String platformName, String platformVersion,
                              String udid, String orientation, String noReset, String testAppName,
                              String testAppPackage, String testAppActivity, String appPackage, String appActivity) {

        this.hubUrl = hubUrl;
        this.deviceName = deviceName;
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.udid = udid;
        this.orientation = orientation;
        this.noReset = noReset;
        this.testAppName = testAppName;
        this.testAppPackage = testAppPackage;
        this.testAppActivity = testAppActivity;
        this.appPackage = appPackage;
        this.appActivity = appActivity;
    }

    @BeforeTest
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = DesiredCapabilities.android();

        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("orientation", orientation);
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("noReset", noReset);

        capabilities.setCapability("browserName", "");
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);

        driver = new AndroidDriver(new URL(hubUrl), capabilities);
    }

    public DesiredCapabilities installedAppCaps() {

        DesiredCapabilities capabilities = DesiredCapabilities.android();

        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("orientation", orientation);
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("noReset", noReset);

        capabilities.setCapability("browserName", "");
        capabilities.setCapability("appPackage", testAppPackage);
        capabilities.setCapability("appActivity", testAppActivity);

        return capabilities;
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {

        Reporter.log("Closing application");

//        Actions.mainActions().clearSession();
        Actions.mainActions().closeApp();
    }
}
