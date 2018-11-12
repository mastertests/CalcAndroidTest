package com.test.actions;

public class Actions {

    private static MainActions mainActions;
    private static AdbActions adbActions;
    private static MailActions mailActions;

    public static MainActions mainActions() {

        if (mainActions == null) {
            mainActions = new MainActions();
        }

        return mainActions;
    }

    public static AdbActions adbActions() {

        if (adbActions == null) {
            adbActions = new AdbActions();
        }

        return adbActions;
    }

    public static MailActions mailActions() {

        if (mailActions == null) {
            mailActions = new MailActions();
        }

        return mailActions;
    }
}

