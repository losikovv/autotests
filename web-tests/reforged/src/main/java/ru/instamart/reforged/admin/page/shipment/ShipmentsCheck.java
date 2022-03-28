package ru.instamart.reforged.admin.page.shipment;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.admin.table.ShipmentTable;
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
    default void checkFoundOrderOrShipmentCount(final int actualCount, final int expectedCount) {
        assertEquals(actualCount, expectedCount, "Найдено больше или меньше заказов");
    }

    @Step("Проверяем что найденный заказ={0} соответствует ожидаемому={1}")
    default void checkOrderOrShipmentNumber(final String actualOrderNumber, final String expectedOrderNumber) {
        assertEquals(actualOrderNumber, expectedOrderNumber, "Найден неверный заказ");
    }

    @Step("Проверяем, что был совершен переход на {0} страницу пейджера заказов")
    default void checkCurrentPageNumber(final String expectedPage) {
        assertEquals(currentPage.getText(), expectedPage,
                "Номер страницы не соответствует ожидаемому значению");
    }

    @Step("Проверяем, что открылась последняя страница пейджера с заказами")
    default void checkLastPagePager() {
        waitAction().shouldNotBeVisible(lastPage);
    }

    @Step("Проверяем, что открылась первая страница пейджера с заказами")
    default void checkFirstPagePager() {
        waitAction().shouldNotBeVisible(firstPage);
    }

    @Step("Проверяем, что колонка Дата и Время содержит только отфильтрованные значения: {0}")
    default void checkDateAndTimeShipmentsColumn(final String deliveryDate) {
        tableComponent
                .getDataFromColumn(ShipmentTable.Column.DATE_AND_TIME_FOR_DELIVERY.getLabel())
                        .forEach(text -> {
                                krakenAssert.assertTrue(
                                        text.contains(deliveryDate),
                                        String.format("В колонке присутствует дата %s отличная от примененного фильтра %s", text, deliveryDate)
                                );
                        });
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что колонка Куда содержит только отфильтрованные значения: {0}")
    default void checkPhoneShipmentsColumn(final String phone) {
        tableComponent.getPhones()
                        .forEach(actualPhone -> {
                            krakenAssert.assertTrue(actualPhone.contains(phone),
                                    String.format("В колонке присутствует телефон '%s' отличный от примененного фильтра '%s'", actualPhone, phone));
                        });
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что количество заказов до фильтрации: {0}, отличается от количества заказов после фильтрации: {1}")
    default void checkNumberOfShipmentsAfterFiltration(final String beforeFiltration, final String afterFiltration) {
        Assert.assertNotEquals(beforeFiltration, afterFiltration,
                "после применения фильтра количество заказов не изменилось");
    }
}
