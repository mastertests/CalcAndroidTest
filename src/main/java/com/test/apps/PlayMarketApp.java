package com.test.apps;

import com.test.util.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayMarketApp {

    private AppiumDriver driver;
    private WebDriverWait wait;

    private final By searchBox = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/search_box_idle_text\")");
    private final By searchField = MobileBy.className("android.widget.EditText");
    private final By installButton = MobileBy.xpath("//android.view.ViewGroup[@resource-id=\"com.android.vending:id/button_container\"]/android.widget.Button[@text=\"INSTALL\"]");

    private final String searchSuggestText = "new UiSelector().resourceId(\"com.android.vending:id/suggest_text\").text(\"%s\")";
    private final String appTitle = "//android.widget.TextView[@content-desc=\"App: %s\"]";

    public void setDriver(AppiumDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Constants.ELEMENT_SMALL_TIMEOUT_MILLISECONDS);
    }

    public void getSearchResult(String appName) {

        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(searchBox)))
                .click();

        driver.findElement(searchField)
                .sendKeys(appName);

        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(MobileBy.AndroidUIAutomator(String.format(searchSuggestText, appName.toLowerCase())))))
                .click();
    }

    public void installApp(String appName, String appPackage) throws Exception {

        if (isElementPresent(MobileBy.xpath(String.format(appTitle, appName)))) {

            wait.until(ExpectedConditions.visibilityOf(
                    driver.findElement(MobileBy.xpath(String.format(appTitle, appName)))))
                    .click();

            if (isElementPresent(installButton))

                wait.until(ExpectedConditions.visibilityOf(
                        driver.findElement(installButton)))
                        .click();
            else

                uninstallApp(appPackage);
        }
    }

    private void uninstallApp(String appPackage) throws IOException, InterruptedException {

        final Process process = Runtime.getRuntime().exec("adb uninstall " + appPackage);

        new Thread(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            try {
                while ((line = input.readLine()) != null)
                    System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        process.waitFor();
    }

    private boolean isElementPresent(By by) {

        return (driver.findElements(by).size() > 0);
    }
}
