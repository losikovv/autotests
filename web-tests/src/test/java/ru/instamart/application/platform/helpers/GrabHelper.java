package ru.instamart.application.platform.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.application.models.ElementData;
import ru.instamart.application.AppManager;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.models.EnvironmentData;

import static java.lang.Integer.parseInt;

public class GrabHelper extends HelperBase{

    public GrabHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
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
        return text(Elements.Header.currentShipAddress());
    }

    /** Взять строку с названием товара в карточке */
    public String itemName() {
        return kraken.grab().text(Elements.ItemCard.name());
    }

    /** Взять целочисленную стоимость товара в карточке */
    public int itemPriceRounded() {
        return round(itemPrice());
    }

    /** Взять строку со стоимостью товара в карточке */
    public String itemPrice() {
        return kraken.grab().text(Elements.ItemCard.price());
    }

    /** Взять кол-во добавленного в корзину товара из каунтера в карточке */
    public int itemQuantity() {
        String quantity = kraken.grab().text(Elements.ItemCard.quantity());
        if(quantity.equals("")) return 0;
        else return parseInt(quantity);
    }

    /** Взять целочисленную сумму корзины */
    public int cartTotalRounded() {
       return round(cartTotal());
    }

    /** Взять строку с суммой корзины */
    public String cartTotal() {
        Shop.Cart.open();
        String cartTotal = kraken.detect().isElementDisplayed(Elements.Cart.total()) ? text(Elements.Cart.total()) : null;
        if (cartTotal == null) {
            message("> в корзине пусто");
        } else {
            message("> сумма корзины " + cartTotal);
        }
        return cartTotal;
    }

    /** Взять номер доставки на странице заказа */
    public String shipmentNumber() {
        String number = kraken.grab().text(Elements.UserProfile.OrderDetailsPage.OrderSummary.shipmentNumber());
        verboseMessage("Номер доставки: " + number);
        return number;
    }

    /** Взять способ оплаты на странице заказа */
    public String shipmentPayment() {
        String payment = kraken.grab().text(Elements.UserProfile.OrderDetailsPage.OrderSummary.paymentMethod());
        verboseMessage("Способ оплаты: " + payment);
        return payment;
    }

    /** Взять способ замен на странице заказа */
    public String shipmentReplacementPolicy() {
        if(!kraken.detect().isElementDisplayed(
                Elements.UserProfile.OrderDetailsPage.OrderSummary.shipmentReplacementPolicy())){
            kraken.perform().click(Elements.UserProfile.OrderDetailsPage.OrderSummary.trigger());
        }
        kraken.await().simply(1); // Ожидание разворота доп.деталей заказа
        String policy = kraken.grab().text(Elements.UserProfile.OrderDetailsPage.OrderSummary.shipmentReplacementPolicy());
        verboseMessage("Способ оплаты: " + policy);
        return policy;
    }


    // TODO переделать
    /** Взять округленное значение цены из указанного элемента */
    public int roundedSum(ElementData element) {
        return round(text(element));
    }

    /** Округлить цену до целого числа, отбросив копейки, пробелы и знак рубля */
    private int round(String price) {
        if (price == null) {
            return 0;
        } else {
            return Integer.parseInt(((price).substring(0, (price.length() - 5))).replaceAll("\\s", ""));
        }
    }

    /** Взять сумму минимального заказа из алерта в корзине */
    public int minOrderSum() {
        Shop.Cart.open();
        if (kraken.detect().isElementDisplayed(Elements.Cart.alertText())) {
            String text = text(Elements.Cart.alertText());
            int minOrderSum = parseInt(((text).substring((text.length() - 9), (text.length() - 3))).replaceAll(
                    "\\s", ""));
            debugMessage("Сумма минимального заказа в алерте корзины: " + minOrderSum + "р");
            return minOrderSum;
        } else return 0;
    }

    /** Взять 10-значный номер телефона по локатору элемента */
    public String strippedPhoneNumber(ElementData element) {
        if (kraken.detect().isElementDisplayed(element)) {
            return strip(value(element));
        } else return null;
    }

    /** Выбрать 10-значный номер телефона из строки, отбросив скобки и +7 */
    private String strip(String phoneNumber) {
        String phone = phoneNumber.replaceAll("\\D", "");
        return phone.substring(phone.length()-10);
    }

    /** Взять текст котомудрости */
    public String catWisdom() {
        String wisdom = null;
        for (int i = 1; i <= 39; i++) {
            String text = kraken.grab().text(Elements.Page404.quote(i));
            debugMessage(">>>>>>>>>>>> " + text);
            if (!text.equals("")) {
                wisdom = text;
            }
        }
        verboseMessage("\nКотомудрость: " + wisdom);
        return wisdom;
    }
}
