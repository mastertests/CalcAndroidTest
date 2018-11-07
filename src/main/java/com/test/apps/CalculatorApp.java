package com.test.apps;

import com.test.base.BaseApp;
import com.test.locators.AndroidUIAutomator;
import com.test.locators.Locator;
import com.test.util.Constants;

public class CalculatorApp extends BaseApp {

    private final Locator digit = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/digit_%s\")");
    private final Locator plus = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/op_add\")");
    private final Locator equally = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/eq\")");
    private final Locator result = new AndroidUIAutomator("new UiSelector().resourceId(\"com.google.android.calculator:id/result\")");

    public void calculateSum(int firstNumber, int secondNumber) {

        wait(Constants.ELEMENT_EXTRASMALL_TIMEOUT_SECONDS);

        setSum(firstNumber, secondNumber);

        pressEquallyButton();
    }

    public int getResult() {

        wait(Constants.ELEMENT_EXTRASMALL_TIMEOUT_SECONDS);

        return Integer.valueOf(getElement(result).getText());
    }

    private void setNumber(int number) {

        char[] numberArray = Integer.toString(number).toCharArray();

        for (char num : numberArray) {
            click("Click on digit " + num, digit, num);
        }
    }

    private void setSum(int firstNumber, int secondNumber) {
        setNumber(firstNumber);

        pressPlusButton();

        setNumber(secondNumber);
    }

    private void pressPlusButton() {

        click("Click on plus", plus);
    }

    private void pressEquallyButton() {

        click("Click on equally", equally);
    }
}
