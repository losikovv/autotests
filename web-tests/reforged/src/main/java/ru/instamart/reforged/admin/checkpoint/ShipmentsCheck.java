package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.admin.element.ShipmentsElement;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

@RequiredArgsConstructor
public class ShipmentsCheck implements Check {

    protected final ShipmentsElement element;

    @Step("Проверяем что отображается заголовок")
    public void checkPageTitle() {
        waitAction().shouldBeVisible(element.title()).isDisplayed();
    }

    @Step("Проверяем что отображается orderDateFrom")
    public void checkOrderDateFrom() {
        waitAction().shouldBeVisible(element.orderDateFrom()).isDisplayed();
    }

    @Step("Проверяем что отображается orderDateTo")
    public void checkOrderDateTo() {
        waitAction().shouldBeVisible(element.orderDateTo()).isDisplayed();
    }

    @Step("Проверяем что отображается customerName")
    public void checkCustomerName() {
        waitAction().shouldBeVisible(element.customerName()).isDisplayed();
    }

    @Step("Проверяем что отображается customerSurname")
    public void checkCustomerSurName() {
        waitAction().shouldBeVisible(element.customerSurname()).isDisplayed();
    }

    @Step("Проверяем что количество найденных товаров={actualCount} равно ожидаемому={expectedCount}")
    public void checkFoundOrderOrShipmentCount(final String actualCount, final int expectedCount) {
        assertEquals(StringUtil.extractNumberFromString(actualCount), expectedCount, "Найдено больше или меньше заказов");
    }

    @Step("Проверяем что найденный заказ\\отправка={0} соответствует ожидаемому={1}")
    public void checkOrderOrShipmentNumber(final String actualOrderNumber, final String expectedOrderNumber) {
        assertEquals(actualOrderNumber, expectedOrderNumber, "Найден неверный заказ");
    }
}
