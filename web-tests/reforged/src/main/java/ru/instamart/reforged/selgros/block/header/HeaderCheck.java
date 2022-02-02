package ru.instamart.reforged.selgros.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HeaderCheck extends HeaderElement, Check {

    @Step("Проверяем что есть header")
    default void checkHeaderVisible() {
        waitAction().shouldBeVisible(header);
    }

    @Step("Проверяем что есть кнопка выбора варианта заказа 'Доставка'")
    default void checkDeliveryButtonVisible() {
        waitAction().shouldBeVisible(delivery);
    }

    @Step("Проверяем что есть кнопка выбора варианта заказа 'Самовывоз'")
    default void checkPickupButtonVisible() {
        waitAction().shouldBeVisible(pickup);
    }

    @Step("Проверяем наличие лого магазина с редиректом на главную")
    default void checkShopLogoButtonVisible() {
        waitAction().shouldBeVisible(logo);
    }

    @Step("Проверяем наличие кнопки выбора адреса")
    default void checkSelectAddressButtonVisible() {
        waitAction().shouldBeVisible(selectAddress);
    }

    @Step("Проверяем наличие кнопки выбора адреса с надписью 'Выберите адрес доставки'")
    default void checkSelectAddressTextButtonVisible() {
        waitAction().shouldBeVisible(selectAddressText);
    }

    @Step("Проверяем наличие времени работы службы поддержки")
    default void checkHotlineWorkHoursVisible() {
        waitAction().shouldBeVisible(hotlineWorkHoursText);
    }

    @Step("Проверяем наличие номера телефона службы поддержки")
    default void checkHotlinePhoneVisible() {
        waitAction().shouldBeVisible(hotlinePhoneNumber);
    }

    @Step("Проверяем наличие ссылки 'Помощь'")
    default void checkHelpVisible() {
        waitAction().shouldBeVisible(help);
    }

    @Step("Проверяем наличие ссылки 'Меню категорий'")
    default void checkCategoryMenuVisible() {
        waitAction().shouldBeVisible(categoryMenu);
    }

    @Step("Проверяем наличие поиска")
    default void checkSearchInputVisible() {
        waitAction().shouldBeVisible(searchInput);
    }

    @Step("Проверяем наличие кнопки поиска")
    default void checkSearchButtonVisible() {
        waitAction().shouldBeVisible(searchButton);
    }

    @Step("Проверяем наличие кнопки 'Корзина'")
    default void checkCartVisible() {
        waitAction().shouldBeVisible(cart);
    }

    @Step("Проверяем наличие кнопки 'Войти'")
    default void checkLoginIsVisible() {
        waitAction().shouldBeVisible(login);
    }

    @Step("Проверяем, что отображается лейбл с инфо о ближайшей доставке и часами")
    default void checkNearestDeliveryLabelVisible() {
        waitAction().shouldBeVisible(nearestDeliveryLabel);
    }
}

