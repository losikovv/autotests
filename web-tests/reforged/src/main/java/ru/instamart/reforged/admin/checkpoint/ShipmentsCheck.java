package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.util.StringUtil;

public interface ShipmentsCheck {

    @Step("Проверяем что количество найденных товаров={actualCount} равно ожидаемому={expectedCount}")
    default void checkFoundShipmentCount(final String actualCount, final int expectedCount) {
        Assert.assertEquals(StringUtil.extractNumberFromString(actualCount), expectedCount, "Найдено больше или меньше заказов");
    }

    @Step("Проверяем что найденный заказ={0} соответствует ожидаемому={1}")
    default void checkShipmentNumber(final String actualShipmentNumber, final String expectedShipmentNumber) {
        Assert.assertEquals(actualShipmentNumber, expectedShipmentNumber, "Найден неверный заказ");
    }
}
