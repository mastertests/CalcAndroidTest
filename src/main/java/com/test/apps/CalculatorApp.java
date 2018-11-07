package com.test.apps;

import com.test.base.BaseApp;
import com.test.locators.AndroidUIAutomator;
import com.test.locators.Locator;
import com.test.util.Constants;

public class CalculatorApp extends BaseApp {

    private final Locator digit = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/digit_%s\")");
    private final Locator add = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/op_add\")");
    private final Locator equals = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/eq\")");
    private final Locator result = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/result\")");

    public void calculateSum(int firstNumber, int secondNumber) {

        wait(Constants.ELEMENT_EXTRASMALL_TIMEOUT_SECONDS);

        setNumber(firstNumber);

        pressPlus();

        setNumber(secondNumber);

        pressEquals();
    }

    public int getResult() {

        wait(Constants.ELEMENT_EXTRASMALL_TIMEOUT_SECONDS);

        return Integer.valueOf(getElement(result).getText());
    }

    private void setNumber(int number) {

        char[] numberArray = Integer.toString(number).toCharArray();

        for (char aNumberArray : numberArray) {
            getElement(digit, aNumberArray).click();
        }
    }

    private void pressPlus() {

        getElement(add).click();
    }

    private void pressEquals() {

        getElement(equals).click();
    }
}
