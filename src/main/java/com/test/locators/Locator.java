package com.test.locators;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public abstract class Locator {

    private final Type type;
    private final String value;

    public Locator(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public By get(Object... args) {
        switch (type) {
            case XPATH:
                return MobileBy.xpath(String.format(value, args));
            case CSS:
                return MobileBy.cssSelector(String.format(value, args));
            case ID:
                return MobileBy.id(String.format(value, args));
            case NAME:
                return MobileBy.name(String.format(value, args));
            case CLASSNAME:
                return MobileBy.className(String.format(value, args));
            case ANDROIDUIAUTOMATOR:
                return MobileBy.AndroidUIAutomator(String.format(value, args));
        }
        throw new IllegalLocatorException(String.format("Locator type \"%s\" is unknown!", type));
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }

    public String toString(Object... args) {
        return String.format(value, args);
    }

    public enum Type {
        XPATH, CSS, ID, NAME, CLASSNAME, ANDROIDUIAUTOMATOR
    }
}
