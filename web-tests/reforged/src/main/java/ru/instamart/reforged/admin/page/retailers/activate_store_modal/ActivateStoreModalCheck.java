package ru.instamart.reforged.admin.page.retailers.activate_store_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ActivateStoreModalCheck extends Check, ActivateStoreModalElements {

    @Step("Проверяем, что модальное окно активации магазина показано")
    default void checkActivateStoreModalVisible() {
        waitAction().shouldBeVisible(activateStoreModal);
        waitAction().shouldNotBeAnimated(activateStoreModal);
    }

    @Step("Проверяем, что модальное окно активации магазина скрыто")
    default void checkActivateStoreModalNotVisible() {
        waitAction().shouldNotBeVisible(activateStoreModal);
    }
}
