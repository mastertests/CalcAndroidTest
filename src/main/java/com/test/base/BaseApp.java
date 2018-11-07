package com.test.base;

import com.test.locators.CSS;
import com.test.locators.ID;
import com.test.locators.IXpath;
import com.test.locators.Locator;
import com.test.util.Constants;
import com.test.util.Random;
import com.test.util.reporter.Reporter;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseApp {
    /*
     * General actions
     */
    protected String getPageTitle(String message) {
        Reporter.log(message);
        return BaseTest.driver.getTitle();
    }

    protected void switchToFrame(String message, Locator frameName, Object... args) {
        Reporter.log(message);
        WebElement frame = getElement(frameName, args);
        BaseTest.driver.switchTo().frame(frame);
    }

    /*
     * Work with elements
     */
    protected WebElement getElement(Locator locator, Object... args) {
        By by = locator.get(args);
        return BaseTest.driver.findElement(by);
    }

    protected List getElements(Locator locator, Object... args) {
        By by = locator.get(args);
        return BaseTest.driver.findElements(by);
    }

    protected String getElementAttributeValue(String message, String attributeName, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement element = getElement(locator, args);
        return element.getAttribute(attributeName);
    }

    protected String getElementText(String message, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement element = getElement(locator, args);
        return element.getText();
    }

    protected void setElementAttributeByXpathWithJS(String message, String attributeName, String attributeValue, IXpath locator, Object... args) {
        Reporter.log(message);
        String locatorXpath = locator.getXpath(args);
        String script = String.format("document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.setAttribute('%s', '%s');",
                locatorXpath, attributeName, attributeValue);
        executeJS(script, locatorXpath);
    }

    /*
     * Drop down lists
     */
    protected void selectDropDownListOption(String message, String selectValue, Locator locator, Object... args) {
        Reporter.log(message);
        Select dropDownList = new Select(getElement(locator, args));
        dropDownList.selectByValue(selectValue);
    }

    protected void selectDropDownListOptionByIndex(String message, int itemIndex, Locator locator, Object... args) {
        Reporter.log(message);
        if (itemIndex > 0) {
            Select dropDownList = new Select(getElement(locator, args));
            dropDownList.selectByIndex(itemIndex - 1);
        }
    }

    protected void selectDropDownListOptionByText(String message, String selectItemText, Locator locator, Object... args) {
        Reporter.log(message);

        Select dropDownList = new Select(getElement(locator, args));
        WebElement select = getElement(locator, args);
        WebElement option = select.findElement(By.xpath(".//option[contains(text(),'" + selectItemText + "')]"));
        String optionValue = option.getAttribute("value");
        // if element has wrong value we can try select item only by text
        try {
            dropDownList.selectByValue(optionValue);
        } catch (NoSuchElementException e) {
            dropDownList.selectByVisibleText(selectItemText);
        }
    }

    protected int selectDropDownListRandomOption(String message, Locator locator, Object... args) {
        Reporter.log(message);
        Select dropDownList = new Select(getElement(locator, args));
        int size = dropDownList.getOptions().size();
        int selectedIndex = Random.genInt(0, size - 1);
        dropDownList.selectByIndex(selectedIndex);
        return selectedIndex;
    }

    /**
     * Select option from drop down
     *
     * @param message    Reporter log message
     * @param firstIndex Index of the first of drop down items from which select
     * @param lastIndex  Index of the last of drop down items from which select. If 'lastIndex' equals to -1
     *                   select from items starting from 'firstIndex' to the last item in the drop down
     * @param locator    locator
     * @param args       locator arguments
     * @return index of selected drop down item
     */
    protected int selectDropDownListRandomOption(String message, int firstIndex, int lastIndex, Locator locator, Object... args) {
        Reporter.log(message);
        Select dropDownList = new Select(getElement(locator, args));
        int size = dropDownList.getOptions().size();
        int selectedIndex = (lastIndex > 0)
                ? Random.genInt(firstIndex - 1, lastIndex - 1)
                : Random.genInt(firstIndex - 1, size - 1);
        dropDownList.selectByIndex(selectedIndex);
        return selectedIndex;
    }

    protected int selectDropDownListRandomOptionByIdWithJS(String message, ID id) {
        Reporter.log(message);
        String js = String.format("var x = document.getElementById(\"%s\").options.length; \n" +
                "var randomOption = Math.round(Math.random() * (x - 2)) + 1; \n" +
                "document.getElementById(\"%s\").options[randomOption].selected = true; \n" +
                "return randomOption;", id.toString(), id.toString());
        return Integer.parseInt(((JavascriptExecutor) BaseTest.driver).executeScript(js).toString());
    }

    protected String getDropDownListSelectedValueText(String message, Locator locator, Object... args) {
        Reporter.log(message);
        return new Select(getElement(locator, args)).getFirstSelectedOption().getText();
    }

    protected List<String> getDropDownListItemsValueTexts(String message, Locator locator, Object... args) {
        Reporter.log(message);
        List<WebElement> options = new Select(getElement(locator, args)).getOptions();
        List<String> items = new ArrayList<>();
        for (WebElement option : options)
            items.add(option.getText());
        return items;
    }

    protected int getDropDownListItemsCount(String message, Locator locator, Object... args) {
        Reporter.log(message);
        return new Select(getElement(locator, args)).getOptions().size();
    }

    /*
     * Input fields and textareas
     */
    protected void type(String message, String value, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement inputElement = getElement(locator, args);
        inputElement.clear();
        inputElement.sendKeys(value);
    }

    protected void typeWithoutWipe(String message, String value, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement inputElement = getElement(locator, args);
        inputElement.sendKeys(value);
    }

    protected void typeWithJS(String message, String value, IXpath locator, Object... args) {
        Reporter.log(message);
        String locatorXpath = locator.getXpath(args);
        value = value.replaceAll("\\\\", "\\\\\\\\");
        executeJS("document.evaluate(\"" + locatorXpath + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value=\"" + value + "\";", locatorXpath);
    }

    protected String getTextFromInput(String message, Locator locator, Object... args) {
        return getElementAttributeValue(message, "value", locator, args);
    }

    protected void typeWithWipe(String message, String value, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement inputElement = getElement(locator, args);
        String oldValue = inputElement.getAttribute("value");
        // simulate user interaction during clearing the input
        for (int i = 1; i <= oldValue.length(); i++)
            inputElement.sendKeys(Keys.BACK_SPACE);
        // type new value
        inputElement.sendKeys(value);
    }

    /*
     * Checkboxes
     */
    protected boolean isCheckboxChecked(Locator locator, Object... args) {
        return getElement(locator, args).isSelected();
    }

    protected void setCheckboxState(String message, boolean checked, Locator locator, Object... args) {
        if (checked ^ isCheckboxChecked(locator, args)) {
            click(message, locator, args);
        }
    }

    /*
     * Clicks
     */
    protected void click(String message, Locator locator, Object... args) {
        Reporter.log(message);
        WebElement element = getElement(locator, args);
        element.click();
    }

    protected void clickByXpathWithJS(String message, IXpath locator, Object... args) {
        Reporter.log(message);
        String locatorXpath = locator.getXpath(args);
        executeJS("document.evaluate(\"" + locatorXpath + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).iterateNext().click();", locatorXpath);
    }

    protected void clickAndWaitElementVisibility(String message, Locator locator, Locator locatorWaitFor, Object... elemArgs) {
        click(message, locator, elemArgs);
        waitForElementVisibility(locatorWaitFor);

    }

    protected void clickAndWaitElementInvisibility(String message, Locator locator, Locator locatorWaitFor, Object... elemArgs) {
        click(message, locator, elemArgs);
        waitForElementInvisibility(locatorWaitFor);
    }

    /*
     * Count elements
     */
    protected int getElementsCount(Locator locator, Object... args) {
        return getElementsCountWithWait(0, locator, args);
    }

    protected int getElementsCountWithWait(int waitInSeconds, Locator locator, Object... args) {
        BaseTest.driver.manage().timeouts().implicitlyWait(waitInSeconds, TimeUnit.SECONDS);
        int size = getElements(locator, args).size();
        BaseTest.driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        return size;
    }

    protected boolean isElementPresent(Locator locator, Object... args) {
        return (getElementsCount(locator, args) > 0);
    }

    protected boolean isElementPresentWithWait(int waitInSeconds, Locator locator, Object... args) {
        return (getElementsCountWithWait(waitInSeconds, locator, args) > 0);
    }

    protected boolean isElementVisible(Locator locator, Object... args) {
        return isElementVisibleWithWait(0, locator, args);
    }

    protected boolean isElementVisibleWithWait(int waitInSeconds, Locator locator, Object... args) {
        By by = locator.get(args);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, waitInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable th) {
            return false;
        }
        return true;
    }

    protected String getElementCssValue(String message, String attributeName, Locator locator, Object... args) {
        Reporter.log(message);
        return getElement(locator, args).getCssValue(attributeName);
    }


    /*
     * Element waits
     */
    protected void switchToNewTab() {
        String winHandleBefore = BaseTest.driver.getWindowHandle();

        for (String winHandle : BaseTest.driver.getWindowHandles()) {
            BaseTest.driver.switchTo().window(winHandle);
        }
    }

    public void switchToWindow(String handler) {
        BaseTest.driver.switchTo().window(handler);
    }

    protected void waitForElementToBeClickable(Locator locator, Object... args) {
        By by = locator.get(args);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Constants.ELEMENT_TIMEOUT_SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(by));

        //wait until element will be at the same place (for moving elements: for Chrome and IE)
        Point currLocation, newLocation;
        long startTime = System.currentTimeMillis();
        long delta;

        newLocation = new Point(-1, -1);
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
            }   //the element may move veeery slowly. It's better to wait some time
            currLocation = newLocation;
            newLocation = getElement(locator, args).getLocation();
            delta = System.currentTimeMillis() - startTime;
        } while ((currLocation.getX() - newLocation.getX() != 0 && currLocation.getY() - newLocation.getY() != 0)
                && (delta <= Constants.ELEMENT_TIMEOUT_SECONDS * 1000));

        if (delta > Constants.ELEMENT_TIMEOUT_SECONDS * 1000) {
            throw new InvalidElementStateException("Element did not stand at the same place for " + Constants.ELEMENT_TIMEOUT_SECONDS + " seconds");
        }
        if (System.getProperty("browser", "firefox").equalsIgnoreCase("firefox")) {
            wait(Constants.ELEMENT_MICRO_TIMEOUT_SECONDS);
        }
    }

    protected void waitForElementPresent(Locator locator, Object... args) {
        By by = locator.get(args);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Constants.ELEMENT_TIMEOUT_SECONDS);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void waitForElementNotPresent(Locator locator, Object... args) {
        By by = locator.get(args);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Constants.ELEMENT_TIMEOUT_SECONDS);
        wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by)));
    }

    protected void waitForElementPresent(int timeout, Locator locator, Object... args) {
        By by = locator.get(args);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void waitForElementVisibility(Locator locator, Object... args) {
        By by = locator.get(args);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Constants.ELEMENT_TIMEOUT_SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void waitForElementInvisibility(Locator locator, Object... args) {
        waitForElementInvisibilityWithWait(0, locator, args);
    }

    protected void waitForElementInvisibilityWithWait(int waitInSecondsBefore, Locator locator, Object... args) {
        By by = locator.get(args);
        BaseTest.driver.manage().timeouts().implicitlyWait(waitInSecondsBefore, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, Constants.ELEMENT_TIMEOUT_SECONDS);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        BaseTest.driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    protected void waitForElementInvisibilityWithWait(int waitInSecondsBefore, int waitInSeconds, Locator locator, Object... args) {
        By by = locator.get(args);
        BaseTest.driver.manage().timeouts().implicitlyWait(waitInSecondsBefore, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, waitInSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        BaseTest.driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public void wait(int waitInSeconds) {
        try {
            Thread.sleep(waitInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void waitForElementContainsText(String text, Locator locator, Object... args) {
        long millis = System.currentTimeMillis();

        while (System.currentTimeMillis() - millis < Constants.ELEMENT_TIMEOUT_SECONDS * 1000) {

            try {
                WebElement webElement = getElement(locator, args);
                if (webElement.getText().contains(text)) break;
                Thread.sleep(1000);
            } catch (InterruptedException | StaleElementReferenceException e) {
                e.printStackTrace();
            }
        }
    }

    protected void mouseOutByXpathUsingJS(String message, IXpath locator, Object... args) {
        Reporter.log(message);
        String locatorXpath = locator.getXpath(args);
        String elem = "document.evaluate(\"" + locatorXpath + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue";
        String javaScript = "if(document.createEvent) {" +
                "    var evObj = document.createEvent('MouseEvents');" +
                "    evObj.initEvent('mouseout', true, false);" +
                "    " + elem + ".dispatchEvent(evObj);" +
                "} else if(document.createEventObject) {" +
                "    " + elem + ".fireEvent('onmouseout');" +
                "}";
        executeJS(javaScript, locatorXpath);
    }

    /**
     * This method add element xpath to exception message (for debug)
     * If we can not add xpath to exception message -
     * returns unchanged exception. (It's impossible, because we get field from Throwable:) )
     *
     * @param script
     * @param xpath
     */
    protected void executeJS(String script, String xpath) {
        try {
            ((JavascriptExecutor) BaseTest.driver).executeScript(script);
        } catch (WebDriverException wd) {
            Field f = null;
            try {
                f = Throwable.class.getDeclaredField("detailMessage");
            } catch (NoSuchFieldException e) {
                throw wd;
            }
            f.setAccessible(true);
            try {
                String error = "\nXPath: " + xpath + "\n" + f.get(wd);
                f.set(wd, error);
            } catch (IllegalAccessException ia) {
            }
            throw wd;
        }
    }

    protected String executeJSWithReturn(String script, WebElement element) {
        return (String) ((JavascriptExecutor) BaseTest.driver).executeScript(script, element);
    }

    public String getElementTextUsingJSByCss(String message, CSS locator, Object... args) {
        Reporter.log(message);
        JavascriptExecutor js = (JavascriptExecutor) BaseTest.driver;
        return String.valueOf(js.executeScript("return document.querySelector('" + locator.toString(args) + "').innerHTML"));
    }

    public void clickUsingJSByCss(String message, CSS locator, Object... args) {
        Reporter.log(message);
        JavascriptExecutor js = (JavascriptExecutor) BaseTest.driver;
        js.executeScript("document.querySelector('" + locator.toString(args) + "').clickSingInButton()");
    }
}
