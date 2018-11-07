package com.test.apps;

public class Apps {

    private static PlayMarketApp playMarketApp;
    private static CalculatorApp calculatorApp;

    public static PlayMarketApp playMarketApp() {

        if (playMarketApp == null)
            playMarketApp = new PlayMarketApp();

        return playMarketApp;
    }

    public static CalculatorApp calculatorApp() {

        if (calculatorApp == null)
            calculatorApp = new CalculatorApp();

        return calculatorApp;
    }
}
