package ru.instamart.reforged.business.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HeaderCheck extends HeaderElement, Check {

    @Step("Проверяем, что отображается введенный адрес")
    default void checkEnteredAddressIsVisible() {
        waitAction().shouldBeVisible(enteredAddress);
    }

    @Step("Проверяем, что кнопка профиля видна")
    default void checkProfileButtonVisible() {
        waitAction().shouldBeVisible(profile);
    }

    @Step("Проверяем, что нотификация после добавления нового товара в корзину показана")
    default void checkCartNotificationIsVisible() {
        waitAction().shouldBeVisible(cartNotification);
    }

    @Step("Проверяем, что нотификация после добавления нового товара в корзину исчезла")
    default void checkCartNotificationIsNotVisible() {
        waitAction().shouldNotBeVisible(cartNotification);
    }

    @Step("Проверяем, что отображается значок с количеством товаров в корзине над кнопкой")
    default void checkCartItemsCountSpoilerIsVisible() {
        waitAction().shouldBeVisible(itemsCountSpoilerOnCartButton);
    }

    @Step("Проверяем, что не отображается значок с количеством товаров в корзине над кнопкой")
    default void checkCartItemsCountSpoilerIsNotVisible() {
        waitAction().shouldNotBeVisible(itemsCountSpoilerOnCartButton);
    }

    @Step("Проверяем, что отображается сообщение об ошибке")
    default void checkErrorAlertDisplayed() {
        waitAction().shouldBeVisible(alert);
    }
}

