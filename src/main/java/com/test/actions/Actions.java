package com.test.actions;

public class Actions {

    private static MainActions mainActions;
    private static PlayMarketActions playMarketActions;

    public static MainActions mainActions() {

        if (mainActions == null) {
            mainActions = new MainActions();
        }

        return mainActions;
    }

    public static PlayMarketActions playMarketActions() {

        if (playMarketActions == null) {
            playMarketActions = new PlayMarketActions();
        }

        return playMarketActions;
    }
}

