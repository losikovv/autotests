package ru.instamart.reforged.admin.shipment;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentsCheck extends Check, ShipmentsElement {
    
    @Step("Проверяем что отображается заголовок")
    default void checkPageTitle() {
        waitAction().shouldBeVisible(title);
    }

    @Step("Проверяем что отображается orderDateFrom")
    default void checkOrderDateFrom() {
        waitAction().shouldBeVisible(orderDateFrom);
    }

    @Step("Проверяем что отображается orderDateTo")
    default void checkOrderDateTo() {
        waitAction().shouldBeVisible(orderDateTo);
    }

    @Step("Проверяем что отображается customerName")
    default void checkCustomerName() {
        waitAction().shouldBeVisible(customerName);
    }

    @Step("Проверяем что отображается customerSurname")
    default void checkCustomerSurName() {
        waitAction().shouldBeVisible(customerSurname);
    }

    @Step("Проверяем что количество найденных товаров={actualCount} равно ожидаемому={expectedCount}")
    default void checkFoundOrderOrShipmentCount(final String actualCount, final int expectedCount) {
        assertEquals(StringUtil.extractNumberFromString(actualCount), expectedCount, "Найдено больше или меньше заказов");
    }

    @Step("Проверяем что найденный заказ\\отправка={0} соответствует ожидаемому={1}")
    default void checkOrderOrShipmentNumber(final String actualOrderNumber, final String expectedOrderNumber) {
        assertEquals(actualOrderNumber, expectedOrderNumber, "Найден неверный заказ");
    }
}
