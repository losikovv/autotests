package ru.instamart.reforged.selgros.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface HeaderCheck extends HeaderElement, Check {

    @Step("Проверяем что есть header")
    default void checkHeaderVisible() {
        krakenAssert.assertTrue(header.is().displayed(), "header не отображается");
    }

    @Step("Проверяем что есть кнопка выбора варианта заказа 'Доставка'")
    default void checkDeliveryButtonVisible() {
        krakenAssert.assertTrue(delivery.is().displayed(), "кнопка выбора варианта заказа 'Доставка' не отображается");
    }

    @Step("Проверяем что есть кнопка выбора варианта заказа 'Самовывоз'")
    default void checkPickupButtonVisible() {
        krakenAssert.assertTrue(pickup.is().displayed(), "кнопка выбора варианта заказа 'Самовывоз' не отображается");
    }

    @Step("Проверяем наличие лого магазина с редиректом на главную")
    default void checkShopLogoButtonVisible() {
        krakenAssert.assertTrue(logo.is().displayed(), "лого магазина с редиректом на главную не отображается");
    }

    @Step("Проверяем наличие кнопки выбора адреса")
    default void checkSelectAddressButtonVisible() {
        krakenAssert.assertTrue(selectAddress.is().displayed(), "кнопка выбора адреса не отображается");
    }

    @Step("Проверяем наличие кнопки выбора адреса с надписью 'Выберите адрес доставки'")
    default void checkSelectAddressTextButtonVisible() {
        krakenAssert.assertTrue(selectAddressText.is().displayed(), "кнопка выбора адреса с надписью 'Выберите адрес доставки' не отображается");
    }

    @Step("Проверяем наличие времени работы службы поддержки")
    default void checkHotlineWorkHoursVisible() {
        krakenAssert.assertTrue(hotlineWorkHoursText.is().displayed(), "время работы службы поддержки не отображается");
    }

    @Step("Проверяем наличие номера телефона службы поддержки")
    default void checkHotlinePhoneVisible() {
        krakenAssert.assertTrue(hotlinePhoneNumber.is().displayed(), "номер телефона службы поддержки не отображается");
    }

    @Step("Проверяем наличие ссылки 'Помощь'")
    default void checkHelpVisible() {
        krakenAssert.assertTrue(help.is().displayed(), "ссылка 'Помощь' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Меню категорий'")
    default void checkCategoryMenuVisible() {
        krakenAssert.assertTrue(categoryMenu.is().displayed(), "ссылка 'Меню категорий' не отображается");
    }

    @Step("Проверяем наличие поиска")
    default void checkSearchInputVisible() {
        krakenAssert.assertTrue(searchInput.is().displayed(), "поиск инпут не отображается");
    }

    @Step("Проверяем наличие кнопки поиска")
    default void checkSearchButtonVisible() {
        krakenAssert.assertTrue(searchButton.is().displayed(), "кнопка поиска не отображается");
    }

    @Step("Проверяем наличие кнопки 'Корзина'")
    default void checkCartVisible() {
        krakenAssert.assertTrue(cart.is().displayed(), "кнопка 'Корзина' не отображается");
    }

    @Step("Проверяем наличие кнопки 'Войти'")
    default void checkLoginIsVisible() {
        krakenAssert.assertTrue(login.is().displayed(), "кнопка 'Войти' не отображается");
    }

    @Step("Проверяем, что отображается лейбл сотрудничества со сбермаркетом")
    default void checkPartnershipLabelVisible() {
        krakenAssert.assertTrue(partnershipLabel.is().displayed(), "лейбл сотрудничества со сбермаркетом не отображается");
    }

    @Step("Проверяем, что отображается лейбл с инфо о ближайшей доставке и часами")
    default void checkNearestDeliveryLabelVisible() {
        krakenAssert.assertTrue(nearestDeliveryLabel.is().displayed(), "лейбл с инфо о ближайшей доставке и часами не отображается");
    }
}

