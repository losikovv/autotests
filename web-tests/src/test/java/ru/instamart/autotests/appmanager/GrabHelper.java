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

    /** Взять целочисленную стоимость товара в карточке */
    public int itemPriceRounded() {
        return round(itemPrice());
    }

    /** Взять строку со стоимостью товара в карточке */
    public String itemPrice() {
        String price;
        if(kraken.detect().isItemOnSale()){
            price = kraken.grab().text(Elements.Site.ItemCard.salePrice());
            printMessage("> скидочная цена товара: " + price);
        } else {
            price = kraken.grab().text(Elements.Site.ItemCard.price());
            printMessage("> цена товара: " + price);
        }
        return price;
    }

    /** Взять целочисленную сумму корзины */
    public int cartTotalRounded() {
       return round(cartTotal());
    }

    /** Взять строку с суммой корзины */
    public String cartTotal() {
        kraken.shopping().openCart();
        String cartTotal = kraken.detect().isElementDisplayed(Elements.Site.Cart.total()) ? text(Elements.Site.Cart.total()) : null;
        if (cartTotal == null) {
            printMessage("> в корзине пусто");
        } else {
            printMessage("> сумма корзины " + cartTotal);
        }
        return cartTotal;
    }

    /** Взять текущий номер заказа на странице заказа */
    public String currentOrderNumber() {
        return kraken.grab().text(Elements.Site.OrderDetailsPage.orderNumber());
    }


    // TODO переделать
    /** Взять округленное значение цены из указанного элемента */
    public int roundedSum(Elements element) {
        return round(text(element));
    }

    /** Взять сумму минимального заказа из алерта в корзине */
    public int minOrderSum() {
        kraken.shopping().openCart();
        if (kraken.detect().isElementDisplayed(Elements.Site.Cart.alertText())) {
            String text = text(Elements.Site.Cart.alertText());
            return Integer.parseInt(((text).substring((text.length() - 8), (text.length() - 3))).replaceAll(
                    "\\s", ""));
        } else return 0;
    }

    /** Взять текст котомудрости */
    public String catWisdom() {
        String wisdom = null;
        for (int i = 1; i <= 39; i++) {
            String text = kraken.grab().text(Elements.Page404.quote(i));
            if (!text.equals("")) { wisdom = text; }

        }
        kraken.perform().printMessage("\nКотомудрость: " + wisdom);
        return wisdom;
    }
}
