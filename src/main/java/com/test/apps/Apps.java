package com.test.apps;

public class Apps {

    private static CalculatorApp calculatorApp;

    public static CalculatorApp calculatorApp() {

        if (calculatorApp == null)
            calculatorApp = new CalculatorApp();

        return calculatorApp;
    }
}
