package com.test.apps;

import com.test.base.BaseApp;
import com.test.locators.ID;
import com.test.locators.Locator;

public class CalculatorApp extends BaseApp {

    private final Locator digit = new ID("com.google.android.calculator:id/digit_%s");
    private final Locator plus = new ID("com.google.android.calculator:id/op_add");
    private final Locator equally = new ID("com.google.android.calculator:id/eq");
    private final Locator result = new ID("com.google.android.calculator:id/result");

    private final Locator numberPad = new ID("com.google.android.calculator:id/pad_numeric");

    public void calculateSum(int firstNumber, int secondNumber) {

        waitForElementToBeClickable(numberPad);

        setSum(firstNumber, secondNumber);

        pressEquallyButton();
    }

    public int getCalculateResult() {

        return Integer.valueOf(getElementText("Getting calculate result", result));
    }

    private void setSum(int firstNumber, int secondNumber) {

        setNumber(firstNumber);

        pressPlusButton();

        setNumber(secondNumber);
    }

    private void setNumber(int number) {

        char[] numberArray = Integer.toString(number).toCharArray();

        for (char num : numberArray) {

            click("Press digit " + num, digit, num);
        }
    }

    private void pressPlusButton() {

        click("Press plus", plus);
    }

    private void pressEquallyButton() {

        click("Press equally", equally);
    }
}
