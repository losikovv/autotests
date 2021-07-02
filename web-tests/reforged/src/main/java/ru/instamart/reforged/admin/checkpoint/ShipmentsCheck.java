package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;

public interface ShipmentsCheck {

    @Step("Проверяем что количество найденных товаров={actualCount} равно ожидаемому={expectedCount}")
    default void checkFoundOrderOrShipmentCount(final String actualCount, final int expectedCount) {
        Assert.assertEquals(StringUtil.extractNumberFromString(actualCount), expectedCount, "Найдено больше или меньше заказов");
    }

    @Step("Проверяем что найденный заказ\\отправка={0} соответствует ожидаемому={1}")
    default void checkOrderOrShipmentNumber(final String actualOrderNumber, final String expectedOrderNumber) {
        Assert.assertEquals(actualOrderNumber, expectedOrderNumber, "Найден неверный заказ");
    }
}
