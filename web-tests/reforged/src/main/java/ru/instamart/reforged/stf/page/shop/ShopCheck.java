package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

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

    @Step("Проверяем, что нотификация после добавления товара в корзину показана")
    default void checkCartNotificationIsVisible() {
        waitAction().shouldBeVisible(cartNotification);
    }

    @Step("Проверяем, что отображается карточка товара")
    default void checkFirstProductCardIsVisible() {
        waitAction().shouldBeVisible(firstProductCard);
    }

    @Step("Проверяем, что отображается карточка товара")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }

    @Step("Проверяем, что отображется сниппет каталога")
    default void checkSnippet() {
        waitAction().shouldBeVisible(firstProductCardProd);
    }
}
