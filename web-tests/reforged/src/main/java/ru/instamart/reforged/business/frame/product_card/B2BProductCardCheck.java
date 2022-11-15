package ru.instamart.reforged.business.frame.product_card;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BProductCardCheck extends Check, B2BProductCardElement {

    @Step("Проверяем, что карточка продукта открыта")
    default void checkProductCardVisible() {
        waitAction().shouldBeVisible(itemName);
    }

    @Step("Проверяем, что карточка продукта закрыта")
    default void checkProductCardIsNotVisible() {
        Assert.assertTrue(itemName.is().invisible());
    }

    @Step("Проверяем, что кнопка 'уменьшить количество продуктов в корзине' не отображается")
    default void checkDecreaseCountButtonNotVisible() {
        Assert.assertTrue(decrease.is().invisible());
    }
}
