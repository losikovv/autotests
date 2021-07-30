package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShopCheck extends Check, ShopElement {

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkMinusButtonAddedAddressIsVisible() {
        waitAction().shouldBeVisible(minusFirstItemFromCartAddedAddress);
    }

    @Step("Проверяем, что нотификация после добавления товара в корзину скрыта")
    default void checkCartNotificationIsNotVisible() {
        waitAction().shouldNotBeVisible(cartNotification);
    }

    @Step("Проверяем, что нотификация после добавления товара в корзину скрыта")
    default void checkCartNotificationIsVisible() {
        waitAction().shouldBeVisible(cartNotification);
    }


}
