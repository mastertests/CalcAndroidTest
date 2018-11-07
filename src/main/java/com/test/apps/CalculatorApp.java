package com.test.apps;

import com.test.util.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class CalculatorApp {

    private AppiumDriver driver;
    private WebDriverWait wait;

    private final By add = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/op_add\")");
    private final By equals = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/eq\")");
    private final By result = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/result\")");

    private final String digit = "new UiSelector().resourceId(\"com.google.android.calculator:id/digit_%s\")";

    public void setDriver(AppiumDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Constants.ELEMENT_SMALL_TIMEOUT_MILLISECONDS);
    }

    public void setFormula() {

        driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_SMALL_TIMEOUT_MILLISECONDS, TimeUnit.SECONDS);

        driver.findElement(MobileBy.AndroidUIAutomator(String.format(digit, 4)))
                .click();

        driver.findElement(MobileBy.AndroidUIAutomator(String.format(digit, 0)))
                .click();

        driver.findElement(MobileBy.AndroidUIAutomator(String.format(digit, 0)))
                .click();

        driver.findElement(add)
                .click();

        driver.findElement(MobileBy.AndroidUIAutomator(String.format(digit, 2)))
                .click();

        driver.findElement(MobileBy.AndroidUIAutomator(String.format(digit, 0)))
                .click();

        driver.findElement(equals)
                .click();
    }

    public int getResult() {

        return Integer.valueOf(
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(result)))
                        .getText());
    }
}
