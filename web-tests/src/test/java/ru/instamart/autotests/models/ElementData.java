package ru.instamart.autotests.models;

import org.openqa.selenium.By;

public class ElementData {
    private By locator;
    private String text;
    private String description;


    public ElementData(By locator){
        this.locator = locator;
    }

    public ElementData(String text, By locator){
        this.text = text;
        this.locator = locator;
    }

    public ElementData(By locator, String description){
        this.locator = locator;
        this.description = description;
    }

    public By getLocator() {
        return locator;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }
}
