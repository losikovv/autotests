package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;

public class GrabHelper extends HelperBase{

    private ApplicationManager kraken;

    GrabHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
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

    /** Взять текст котомудрости */
    public String catWisdom() {
        String wisdom = null;
        for (int i = 1; i <= 39; i++) {
            //String text = kraken.grab().text(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div/div["+i+"]/div/blockquote"));
            String text = kraken.grab().text(Elements.Page404.quote(i));
            if (!text.equals("")) { wisdom = text; }

        }
        kraken.perform().printMessage("\nКотомудрость: " + wisdom);
        return wisdom;
    }
}
