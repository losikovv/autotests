package ru.instamart.reforged.admin.page.retailers.retailer_page;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailerPageCheck extends Check, RetailerPageElements {

    @Step("Проверяем, что кнопка 'Добавить магазин' отображается")
    default void checkAddNewStoreButtonVisible() {
        waitAction().shouldBeVisible(addNewStoreButton);
    }

    @Step("Проверяем, что все магазины ретейлера недоступны")
    default void checkAllStoresDisable() {
        Assert.assertEquals(storesInTable.elementCount(), inactiveStoresInTable.elementCount());
    }

    @Step("Проверяем, что хотя бы один магазин доступен")
    default void checkSomeStoreEnable() {
        Assert.assertTrue(activeStoresInTable.elementCount() > 0);
    }
}
