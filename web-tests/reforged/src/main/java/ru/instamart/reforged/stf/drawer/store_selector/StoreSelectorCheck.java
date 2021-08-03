package ru.instamart.reforged.stf.drawer.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StoreSelectorCheck extends Check, StoreSelectorElement {


    @Step("Проверяем, что окно выбора магазина отображается")
    default void checkStoreSelectorFrameIsPresent() {
        waitAction().shouldBeVisible(storeSelector);
    }

    @Step("Проверяем, что в окне выбора магазинов доступен хотя бы один магазин")
    default void checkStoreDrawerIsNotEmpty() {
        waitAction().shouldBeVisible(storeCard);
    }

    @Step("Проверяем, что окно выбора магазина не отображается")
    default void checkStoreSelectorFrameIsNotPresent() {
        waitAction().shouldNotBeVisible(storeSelector);
    }
}
