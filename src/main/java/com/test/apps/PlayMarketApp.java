package com.test.apps;

import com.test.actions.Actions;
import com.test.base.BaseApp;
import com.test.locators.AndroidUIAutomator;
import com.test.locators.ClassName;
import com.test.locators.Locator;
import com.test.locators.XPath;
import com.test.util.reporter.Reporter;

public class PlayMarketApp extends BaseApp {

    private final Locator searchBox = new AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/search_box_idle_text\")");
    private final Locator searchField = new ClassName("android.widget.EditText");
    private final Locator searchSuggestText = new AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/suggest_text\").text(\"%s\")");

    private final Locator appTitle = new XPath("//android.widget.TextView[@content-desc=\"App: %s\"]");
    private final Locator installButton = new XPath("//android.view.ViewGroup[@resource-id=\"com.android.vending:id/button_container\"]/android.widget.Button[@text=\"INSTALL\"]");
    private final Locator uninstallButton = new XPath("//android.view.ViewGroup[@resource-id=\"com.android.vending:id/button_container\"]/android.widget.Button[@text=\"UNINSTALL\"]");

    public void getSearchResult(String appName) {

        Reporter.log("Getting search result");

        waitForElementVisibility(searchBox);
        click("Click on search box", searchBox);

        type("Set text " + appName + " to search field", appName, searchField);

        waitForElementVisibility(searchSuggestText, appName.toLowerCase());
        click("Click on suggest text", searchSuggestText, appName.toLowerCase());
    }

    public void selectApp(String appName) {

        Reporter.log("Select an application " + appName);

        waitForElementVisibility(appTitle, appName);
        click("Click on application title", appTitle, appName);
    }

    public void installApp(String appPackage) throws Exception {

        Reporter.log("Installing application");

        if (isElementPresent(installButton)) {
            clickAndWaitElementVisibility("Click on install button", installButton, uninstallButton);
        } else {
            reinstallApp(appPackage);
        }
    }

    private void reinstallApp(String appPackage) throws Exception {

        Reporter.log("Uninstalling application");

        Actions.playMarketActions().uninstallApp(appPackage);

        waitForElementVisibility(installButton);
        installApp(appPackage);
    }
}
