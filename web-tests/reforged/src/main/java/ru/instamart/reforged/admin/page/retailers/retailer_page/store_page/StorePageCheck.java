package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StorePageCheck extends Check, StorePageElements {

    @Step("Проверяем, что кнопка 'Возврат к списку магазинов' отображается")
    default void checkBackToStoresListButtonVisible() {
        waitAction().shouldBeVisible(backToStoresList);
    }
}
