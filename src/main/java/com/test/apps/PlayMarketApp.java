package com.test.apps;

import com.test.actions.Actions;
import com.test.base.BaseApp;
import com.test.locators.AndroidUIAutomator;
import com.test.locators.ClassName;
import com.test.locators.Locator;
import com.test.locators.XPath;

public class PlayMarketApp extends BaseApp {

    private final Locator searchBox = new AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/search_box_idle_text\")");
    private final Locator searchField = new ClassName("android.widget.EditText");
    private final Locator searchSuggestText = new AndroidUIAutomator("new UiSelector().resourceId(\"com.android.vending:id/suggest_text\").text(\"%s\")");

    private final Locator appTitle = new XPath("//android.widget.TextView[@content-desc=\"App: %s\"]");
    private final Locator installButton = new XPath("//android.view.ViewGroup[@resource-id=\"com.android.vending:id/button_container\"]/android.widget.Button[@text=\"INSTALL\"]");
    private final Locator uninstallButton = new XPath("//android.view.ViewGroup[@resource-id=\"com.android.vending:id/button_container\"]/android.widget.Button[@text=\"UNINSTALL\"]");

    public void getSearchResult(String appName) {

        waitForElementVisibility(searchBox);
        getElement(searchBox).click();

        getElement(searchField).sendKeys(appName);

        waitForElementVisibility(searchSuggestText, appName.toLowerCase());
        getElement(searchSuggestText, appName.toLowerCase()).click();
    }

    public void installApp(String appName, String appPackage) throws Exception {

        waitForElementVisibility(appTitle, appName);
        getElement(appTitle, appName).click();

        if (isElementPresent(installButton)) {
            getElement(installButton).click();
        } else {
            Actions.playMarketActions().uninstallApp(appPackage);
            waitForElementVisibility(installButton);
            getElement(installButton).click();
            waitForElementVisibility(uninstallButton);
        }
    }
}
