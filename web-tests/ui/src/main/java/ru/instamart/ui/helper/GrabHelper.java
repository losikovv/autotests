package ru.instamart.ui.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.kraken.setting.Config;
import ru.instamart.ui.data.ElementData;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.ui.Elements;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Shop;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Slf4j
public final class GrabHelper extends HelperBase {

    private final AppManager kraken;

    /** Взять sid из текущего URL */
    public int sidFromUrl() {
        final String url = currentURL();
        final int index = url.indexOf("?sid=");

        if (index == -1) return 1;

        final char[] chars = url.toCharArray();
        final StringBuilder sidString = new StringBuilder();
        int i = index + 5;

        while (i < chars.length && Character.isDigit(chars[i])) {
            sidString.append(chars[i]);
            i++;
        }
        if (sidString.length() == 0) return 1;
        return parseInt(sidString.toString());
    }

    /** Взять текущий URL */
    public String currentURL() {
        return AppManager.getWebDriver().getCurrentUrl();
    }

    /** Взять количество элементов */
    public int listSize(ElementData element) {
        return listSize(element.getLocator());
    }

    /** Взять количество элементов по локатору */
    public int listSize(By locator) {
        return AppManager.getWebDriver().findElements(locator).size();
    }

    /** Взять текст элемента */
    public String text(ElementData element) {
        return text(element.getLocator());
    }

    /** Взять текст элемента по локатору */
    public String text(By locator) {
        try {
            return kraken.await().getText(locator);
            //return AppManager.getWebDriver().findElement(locator).getText();
        } catch (TimeoutException e) {
            return "empty";
        }
    }

    /** Взять текст элемента по локатору */
    public String text(By locator, int i) {
        try {
            return AppManager.getWebDriver().findElement(locator).getText();
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception ex){
            return AppManager.getWebDriver().findElements(locator).get(i).getText();
        }
    }

    /** Взять текст из заполненного поля по элементу */
    public String value(ElementData element) {
        return value(element.getLocator());
    }

    /** Взять текст из заполненного поля по локатору */
    public String value(By locator) {
        try {
            return AppManager.getWebDriver().findElement(locator).getAttribute("value");
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
        return kraken.grab().text(Elements.ItemCard.name().getLocator(),0);
    }

    /** Взять целочисленную стоимость товара в карточке */
    public int itemPriceRounded() {
        return roundPrice(itemPrice());
    }

    /** Взять строку со стоимостью товара в карточке */
    public String itemPrice() {
        log.info("Получение цены из карточки товара");
        String itemPrice;
        itemPrice = AppManager.getWebDriver().findElement(Elements.ItemCard.priceFromAttribute().getLocator())
                .getAttribute("content");
        return itemPrice;
    }

    /** Взять кол-во добавленного в корзину товара из каунтера в карточке */
    public int itemQuantity() {
        String quantity = kraken.grab().text(Elements.ItemCard.quantity());
        if (quantity.equals("")) return 0;
        else return parseInt(quantity);
    }
    /** Взять кол-во добавленного в корзину товара из каунтера в карточке */
    public String itemQuantityByText() {
        String quantity = kraken.grab().text(Elements.ItemCard.quantityByText());
        if (quantity.equals("Товара много")) return quantity;
        else return "количество товара ограничено, может не хватить для выполнения теста";
    }

    /** Добавить первую единицу товара с карточки товара*/
    public void addItemCard(){
        try {
            kraken.await().
                    fluently(ExpectedConditions.elementToBeClickable(Elements.ItemCard.buyButton().getLocator()),
                            "Кнопка купить не доступна для нажатия");
            kraken.perform().click(Elements.ItemCard.buyButton());
            kraken.await().
                    fluently(ExpectedConditions.invisibilityOfElementLocated(Elements.ItemCard.buyButton().getLocator()),
                            "кнопка купить не нажимается",10);
        } catch (NoSuchElementException ex){
            throw new ElementClickInterceptedException("невозможно нажать на кнопку купить");
        }
    }

    /** Добавить несколько единиц товара с карточки товара к существующему товару*/
    public void addItemCard(int count){
        try{
            for (int i=1;i<count;i++){
                kraken.perform().click(Elements.ItemCard.plusButton());
            }
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.ItemCard.quantityByCount(count).getLocator()),
                    "каунтер товаров не содержит нужное количество товаров: "+count, Config.BASIC_TIMEOUT);
        }catch (NoSuchElementException ex){
            throw new ElementClickInterceptedException("невозможно нажать на кнопку купить");
        }
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
            log.warn("> в корзине пусто");
        } else {
            log.info("> сумма корзины {}", cartTotal);
        }
        return cartTotal;
    }

    /** Взять номер доставки на странице заказа */
    public String shipmentNumber() {
        String number = kraken.grab().text(Elements.UserProfile.OrderDetailsPage.OrderSummary.shipmentNumber());
        log.info("Номер доставки: {}", number);
        return number;
    }

    /** Взять способ оплаты на странице заказа */
    public String shipmentPayment() {
        String payment = kraken.grab().text(Elements.UserProfile.OrderDetailsPage.OrderSummary.paymentMethod());
        log.info("Способ оплаты: {}", payment);
        return payment;
    }

    /** Взять способ замен на странице заказа */
    public String shipmentReplacementPolicy() {
        if (!kraken.detect().isElementDisplayed(
                Elements.UserProfile.OrderDetailsPage.OrderSummary.shipmentReplacementPolicy())) {
            kraken.perform().click(Elements.UserProfile.OrderDetailsPage.OrderSummary.trigger());
        }
        ThreadUtil.simplyAwait(1); // Ожидание разворота доп.деталей заказа
        String policy = kraken.grab().text(Elements.UserProfile.OrderDetailsPage.OrderSummary.shipmentReplacementPolicy());
        log.info("Способ оплаты: {}", policy);
        return policy;
    }

    /** Округлить цену до целого числа, отбросив копейки, пробелы и знак рубля */
    private int round(String price) {
        if (price == null) {
            return 0;
        } else {
            return Integer.parseInt(((price).substring(0, (price.length() - 5))).replaceAll("\\s", ""));
        }
    }

    //TODO объединить метод roundPrice c методом round(проверка строки -> выбор приведения)
    /**Округление до целого числа()*/
    private int roundPrice(String price) {
        if (price == null) {
            return 0;
        } else {
            return Integer.parseInt(((price).substring(0, (price.length() - 3))));
        }
    }

    /** Взять сумму минимального заказа из алерта в корзине */
    public int minOrderSum() {
        Shop.Cart.open();
        if (kraken.detect().isElementDisplayed(Elements.Cart.alertText())) {
            String text = text(Elements.Cart.alertText());
            int minOrderSum = parseInt(((text).substring((text.length() - 9), (text.length() - 3))).replaceAll(
                    "\\s", ""));
            log.info("Сумма минимального заказа в алерте корзины: {}р", minOrderSum);
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
            log.info(">>>>>>>>>>>> {}", text);
            if (!text.equals("")) {
                wisdom = text;
            }
        }
        log.info("\nКотомудрость: {}", wisdom);
        return wisdom;
    }
}
