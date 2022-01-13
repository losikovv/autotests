package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailersPageCheck extends Check, RetailersPageElements {

    @Step("Проверяем, что кнопка 'Добавить ретейлера' отображается")
    default void checkAddNewRetailerButtonVisible() {
        waitAction().shouldBeVisible(addNewRetailerButton);
    }

    @Step("Сравнение количества ретейлеров с количеством надписей о доступности/недоступности")
    default void retailerAccessibilityCompare(final int quantity) {
        Assert.assertEquals(retailersAccessibilityInTable.elementCount(), quantity);
    }

    @Step("Сравнение количества ретейлеров с количеством дат создания ретейлеров")
    default void retailerCreateDateCompare(final int quantity) {
        Assert.assertEquals(retailersCreateDateInTable.elementCount(), quantity);
    }

}