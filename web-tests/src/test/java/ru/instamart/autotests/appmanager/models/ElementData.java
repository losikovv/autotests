package ru.instamart.autotests.appmanager.models;

import org.openqa.selenium.By;

public class ElementData {
    private By locator;
    private String text; //todo убрать
    private String description;

    // TODO убрать конструктор, все элементы перевести на нижний конструктор
    public ElementData(By locator){
        this.locator = locator;
    }

    // TODO убрать конструктор, все элементы перевести на нижний конструктор
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
    } //todo убрать

    public String getDescription() {
        return description;
    }
}
