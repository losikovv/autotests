package ru.instamart.reforged.business.frame.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StoreSelectorCheck extends Check, StoreSelectorElement {

    @Step("Проверяем, что окно выбора магазина открыто")
    default void checkStoreSelectorFrameIsOpen() {
        waitAction().shouldBeVisible(storeSelector);
    }

    @Step("Проверяем, что в окне выбора магазинов доступен хотя бы один магазин")
    default void checkStoreSelectorDrawerIsNotEmpty() {
        waitAction().shouldBeVisible(firstStoreCard);
    }

    @Step("Проверяем, что в окне выбора магазинов нет магазинов для выбора")
    default void checkStoreSelectorDrawerIsEmpty() {
        waitAction().shouldNotBeVisible(firstStoreCard);
    }

    @Step("Проверяем, что окно выбора магазина закрыто")
    default void checkStoreSelectorFrameIsNotOpen() {
        waitAction().shouldNotBeVisible(storeSelector);
    }
}
