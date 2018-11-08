package com.test.base;

import com.test.actions.Actions;
import com.test.util.reporter.Reporter;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.URL;

public class BaseTest {

    public static AndroidDriver driver;
    public static String udid;

    private String hubUrl, deviceName, platformName, platformVersion, orientation, noReset;
    private String appPackage, appActivity, pathToApk;

    @BeforeSuite
    @Parameters({"hubUrl", "deviceName", "platformName", "platformVersion", "udid",
            "orientation", "noReset", "appPackage", "appActivity", "pathToApk"})
    public void setParameters(String hubUrl, String deviceName, String platformName, String platformVersion, String udid,
                              String orientation, String noReset, String appPackage, String appActivity, String pathToApk) {

        this.hubUrl = hubUrl;
        this.deviceName = deviceName;
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.orientation = orientation;
        this.noReset = noReset;
        this.appPackage = appPackage;
        this.appActivity = appActivity;
        this.pathToApk = pathToApk;
        BaseTest.udid = udid;
    }

    @BeforeTest
    public void installAppApk() throws Exception {

        if (Actions.adbActions().isApkInstalled(appPackage))
            Actions.adbActions().uninstallApp(appPackage);

        Actions.adbActions().installApp(pathToApk);
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

    @AfterTest(alwaysRun = true)
    public void tearDown() {

        Reporter.log("Closing application");

        Actions.mainActions().closeApp();
    }
}
