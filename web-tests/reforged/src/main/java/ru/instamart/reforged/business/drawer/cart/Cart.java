package ru.instamart.reforged.business.drawer.cart;

import io.qameta.allure.Step;

/**
 * Корзина. Основные элементы
 */
public final class Cart implements CartCheck {

    @Step("Нажимаем кнопку 'Закрыть'")
    public void closeCart() {
        closeButton.click();
    }
}
