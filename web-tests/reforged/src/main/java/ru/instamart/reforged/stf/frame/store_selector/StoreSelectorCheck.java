package ru.instamart.reforged.stf.frame.store_selector;

import io.qameta.allure.Step;
import org.testng.Assert;
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
        Assert.assertTrue(firstStoreCard.is().invisible());
    }

    @Step("Проверяем, что окно выбора магазина закрыто")
    default void checkStoreSelectorFrameIsNotOpen() {
        Assert.assertTrue(storeSelector.is().invisible());
    }
}
