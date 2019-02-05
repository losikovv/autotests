package ru.instamart.autotests.models;

import org.openqa.selenium.By;

public class ElementData {
    private String text;
    private By locator;

    public ElementData(String text, By locator){
        this.text = text;
        this.locator = locator;
    }

    public String getText() {
        return text;
    }

    public By getLocator() {
        return locator;
    }
}
