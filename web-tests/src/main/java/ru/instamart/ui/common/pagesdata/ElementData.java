package ru.instamart.ui.common.pagesdata;

import org.openqa.selenium.By;

public class ElementData {
    private By locator;
    private String description;

    public ElementData(By locator, String description){
        this.locator = locator;
        this.description = description;
    }

    public By getLocator() {
        return locator;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ElementData{" +
                "locator=" + locator +
                ", description='" + description + '\'' +
                '}';
    }
}
