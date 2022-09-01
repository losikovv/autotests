package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StorePageCheck extends Check, StorePageElements {

    @Step("Проверяем, что кнопка 'Возврат к списку магазинов' отображается")
    default void checkBackToStoresListButtonVisible() {
        waitAction().shouldBeVisible(backToStoresList);
    }

    @Step("Проверяем, что адрес {0} на странице магазина соответствует ожидаемому")
    default void checkAddressCorrect(final String addressExp) {
        waitAction().shouldBeVisible(address, addressExp);
    }

    @Step("Проверяем, что название магазина содержит текст: '{expectedStoreName}'")
    default void checkStoreNameContains(final String expectedStoreName) {
        Assert.assertTrue(storeTitle.getText().contains(expectedStoreName),
                String.format("Название магазина '%s' не содержит '%s'", storeTitle.getText(), expectedStoreName));
    }
}
