package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;

import static java.lang.Integer.parseInt;
import static ru.instamart.autotests.application.Config.verbose;

public class GrabHelper extends HelperBase{

    GrabHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Взять текущий URL */
    public String currentURL() {
        return driver.getCurrentUrl();
    }

    /** Взять количество элементов */
    public int listSize(ElementData element) {
        return listSize(element.getLocator());
    }

    /** Взять количество элементов по локатору */
    public int listSize(By locator) {
        return driver.findElements(locator).size();
    }

    /** Взять текст элемента */
    public String text(ElementData element) {
        return text(element.getLocator());
    }

    /** Взять текст элемента по локатору */
    public String text(By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /** Взять текст из заполненного поля по элементу */
    public String value(ElementData element) {
        return value(element.getLocator());
    }

    /** Взять текст из заполненного поля по локатору */
    public String value(By locator) {
        try {
            return driver.findElement(locator).getAttribute("value");
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /** Взять текущий адрес доставки */
    public String currentShipAddress() {
        return text(Elements.Site.Header.currentShipAddress());
    }

    /** Взять строку с названием товара в карточке */
    public String itemName() {
        return kraken.grab().text(Elements.Site.ItemCard.name());
    }

    /** Взять целочисленную стоимость товара в карточке */
    public int itemPriceRounded() {
        return round(itemPrice());
    }

    /** Взять строку со стоимостью товара в карточке */
    public String itemPrice() {
        return kraken.grab().text(Elements.Site.ItemCard.price());
    }

    /** Взять кол-во добавленного в корзину товара из каунтера в карточке */
    public int itemQuantity() {
        return parseInt(kraken.grab().text(Elements.Site.ItemCard.quantity()));
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
        String url = kraken.grab().currentURL();
        String number = url.substring(url.length()-10);
        if(verbose) kraken.perform().printMessage("Номер заказа: " + number + "\n");
        return number;
    }

    /** Взять номер доставки на странице заказа */
    public String shipmentNumber() {
        String number = kraken.grab().text(Elements.Site.UserProfile.OrderDetailsPage.shipmentNumber());
        if(verbose) {kraken.perform().printMessage("Номер доставки: " + number);}
        return number;
    }

    /** Взять способ оплаты на странице заказа */
    public String shipmentPayment() {
        String payment = kraken.grab().text(Elements.Site.UserProfile.OrderDetailsPage.shipmentPayment());
        if(verbose) {kraken.perform().printMessage("Способ оплаты: " + payment);}
        return payment;
    }


    // TODO переделать
    /** Взять округленное значение цены из указанного элемента */
    public int roundedSum(ElementData element) {
        return round(text(element));
    }

    /** Взять сумму минимального заказа из алерта в корзине */
    public int minOrderSum() {
        kraken.shopping().openCart();
        if (kraken.detect().isElementDisplayed(Elements.Site.Cart.alertText())) {
            String text = text(Elements.Site.Cart.alertText());
            return parseInt(((text).substring((text.length() - 8), (text.length() - 3))).replaceAll(
                    "\\s", ""));
        } else return 0;
    }

    /** Взять 10-значный номер телефона по локатору элемента */
    public String strippedPhoneNumber(ElementData element) {
        if (kraken.detect().isElementDisplayed(element)) {
            return strip(value(element));
        } else return null;
    }

    /** Взять текст котомудрости */
    public String catWisdom() {
        String wisdom = null;
        for (int i = 1; i <= 39; i++) {
            String text = kraken.grab().text(Elements.Page404.quote(i));
            if (!text.equals("")) {
                wisdom = text;
            }
        }
        kraken.perform().printMessage("\nКотомудрость: " + wisdom);
        return wisdom;
    }
}
