package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class GrabHelper extends HelperBase{

    private ApplicationManager kraken;

    GrabHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Взять текущий URL */
    public String currentURL() {
        return driver.getCurrentUrl();
    }

    /** Взять текст элемента */
    public String text(Elements element) {
        return text(Elements.locator());
    }

    /** Взять текст элемента по локатору */
    public String text(By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /** Взять текущий адрес доставки */
    public String currentShipAddress() {
        return text(Elements.Site.Header.currentShipAddress());
    }

    /** Взять текущую сумму корзины */
    public String currentCartTotal() {
        return kraken.detect().isElementDisplayed(Elements.Site.Cart.total()) ? text(Elements.Site.Cart.total()) : null;
    }

    /** Взять текущий номер заказа на странице заказа */
    public String currentOrderNumber() {
        return kraken.grab().text(Elements.Site.OrderDetailsPage.orderNumber());
    }

    /** Взять округленное значение цены из указанного элемента */
    public int roundedSum(Elements element) {
        return round(text(element));
    }
}
