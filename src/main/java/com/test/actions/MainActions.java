package com.test.actions;

import static com.test.base.BaseTest.driver;

public class MainActions {

//    public void clearSession() {
//
//        driver.manage().deleteAllCookies();
//    }

    public void closeApp() {

        if (driver != null)
            driver.quit();
    }
}
