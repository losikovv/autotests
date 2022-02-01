package ru.instamart.reforged.stf.frame.store_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StoreModalCheck extends Check, StoreModalElement {

    @Step("Проверяем, что окно выбора магазина открыто")
    default void checkStoreModalIsOpen() {
        waitAction().shouldBeVisible(storeModalWindow);
    }
}
